package org.serratec.adocao_pets.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.serratec.adocao_pets.domain.Animal;
import org.serratec.adocao_pets.domain.Caracteristica;
import org.serratec.adocao_pets.dto.request.AnimalDTORequest;
import org.serratec.adocao_pets.dto.response.AnimalDTOResponse;
import org.serratec.adocao_pets.dto.response.CaracteristicaDTOResponse;
import org.serratec.adocao_pets.enumerated.Especie;
import org.serratec.adocao_pets.enumerated.Sexo;
import org.serratec.adocao_pets.enumerated.StatusAdocao;
import org.serratec.adocao_pets.enumerated.Tamanho;
import org.serratec.adocao_pets.exception.RecursoNaoEncontradoException;
import org.serratec.adocao_pets.repository.AnimalRepository;
import org.serratec.adocao_pets.repository.CaracteristicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private CaracteristicaRepository caracteristicaRepository;

    // Métodos para o GETS
    @Transactional
    public List<AnimalDTOResponse> listarTodos() {
        return animalRepository.findAll().stream()
                .map(AnimalDTOResponse::toAnimalResponse)
                .toList();
    }

    @Transactional
    public AnimalDTOResponse buscar(Long id) {
        return animalRepository.findById(id)
                .map(AnimalDTOResponse::toAnimalResponse)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Animal de ID '" + id + "' não encontrado!"));
    }

    @Transactional
    public List<AnimalDTOResponse> buscarNome(String nome) {
        List<Animal> animais = animalRepository.findByNomeContainingIgnoreCase(nome);
        if (animais.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum animal com nome contendo '" + nome + "' encontrado!");
        }
        return animais.stream().map(AnimalDTOResponse::toAnimalResponse).toList();
    }

    @Transactional
    public List<AnimalDTOResponse> buscarTamanho(String tamanho) {
        try {
            Tamanho tamanhoEnum = Tamanho.valueOf(tamanho.toUpperCase());
            List<Animal> animais = animalRepository.findByTamanho(tamanhoEnum);
            if (animais.isEmpty()) {
                throw new RecursoNaoEncontradoException("Nenhum animal com tamanho '" + tamanho + "' encontrado!");
            }
            return animais.stream().map(AnimalDTOResponse::toAnimalResponse).toList();
        } catch (IllegalArgumentException e) {
            throw new RecursoNaoEncontradoException("Tamanho '" + tamanho + "' é inválido!");
        }
    }

    @Transactional
    public List<AnimalDTOResponse> buscarEspecies(String especie) {
        try {
            Especie especieEnum = Especie.valueOf(especie.toUpperCase());
            List<Animal> animais = animalRepository.findByEspecie(especieEnum);
            if (animais.isEmpty()) {
                throw new RecursoNaoEncontradoException("Nenhum animal da espécie '" + especie + "' encontrado!");
            }
            return animais.stream().map(AnimalDTOResponse::toAnimalResponse).toList();
        } catch (IllegalArgumentException e) {
            throw new RecursoNaoEncontradoException("Espécie '" + especie + "' é inválida!");
        }
    }

    @Transactional
    public List<AnimalDTOResponse> buscarSexo(String sexo) {
        try {
            Sexo sexoEnum = Sexo.valueOf(sexo.toUpperCase());
            List<Animal> animais = animalRepository.findBySexo(sexoEnum);
            if (animais.isEmpty()) {
                throw new RecursoNaoEncontradoException("Nenhum animal do sexo '" + sexo + "' encontrado!");
            }
            return animais.stream().map(AnimalDTOResponse::toAnimalResponse).toList();
        } catch (IllegalArgumentException e) {
            throw new RecursoNaoEncontradoException("Sexo '" + sexo + "' é inválido!");
        }
    }

    @Transactional
    public List<AnimalDTOResponse> buscarAdocao(String adocao) {
        try {
            StatusAdocao statusEnum = StatusAdocao.valueOf(adocao.toUpperCase());
            List<Animal> animais = animalRepository.findByStatusAdocao(statusEnum);
            if (animais.isEmpty()) {
                throw new RecursoNaoEncontradoException(
                        "Nenhum animal com status de adoção '" + adocao + "' encontrado!");
            }
            return animais.stream().map(AnimalDTOResponse::toAnimalResponse).toList();
        } catch (IllegalArgumentException e) {
            throw new RecursoNaoEncontradoException("Status de adoção '" + adocao + "' é inválido!");
        }
    }

    @Transactional
    public List<CaracteristicaDTOResponse> listarCaracteristicas(Long id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Animal de ID '" + id + "' não encontrado!"));

        return animal.getCaracteristicas().stream()
                .map(c -> new CaracteristicaDTOResponse(c.getId(), c.getDescricao()))
                .toList();
    }

    // Métodos para o POST
    @Transactional
    public AnimalDTOResponse salvar(AnimalDTORequest request) {
        Set<Caracteristica> caracteristicasProntas = buscarCaracteristicasPorIds(request.caracteristicas());
        Animal animal = request.toAnimal(caracteristicasProntas);
        return AnimalDTOResponse.toAnimalResponse(animalRepository.save(animal));
    }

    @Transactional
    public List<AnimalDTOResponse> salvarList(List<AnimalDTORequest> requests) {

        Set<Long> idsSet = new HashSet<>();
        for (AnimalDTORequest r : requests) {
            if (r.caracteristicas() != null) {
                idsSet.addAll(r.caracteristicas());
            }
        }
        List<Long> ids = new ArrayList<>(idsSet);

        Map<Long, Caracteristica> mapaCaracteristicas = caracteristicaRepository.findAllById(ids).stream()
                .collect(Collectors.toMap(Caracteristica::getId, c -> c));

        List<Animal> animais = requests.stream()
                .map(request -> {
                    Set<Caracteristica> caracteristicasDoAnimal = new HashSet<>();
                    if (request.caracteristicas() != null) {
                        request.caracteristicas().forEach(id -> {
                            Caracteristica c = mapaCaracteristicas.get(id);
                            if (c == null)
                                throw new RecursoNaoEncontradoException(
                                        "Característica ID '" + id + "' não encontrada.");
                            caracteristicasDoAnimal.add(c);
                        });
                    }
                    return request.toAnimal(caracteristicasDoAnimal);
                })
                .toList();

        return animalRepository.saveAll(animais).stream()
                .map(AnimalDTOResponse::toAnimalResponse)
                .toList();
    }

    // Métodos para o PUT
    @Transactional
    public AnimalDTOResponse atualizar(Long id, AnimalDTORequest request) {
        Animal existe = animalRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Animal de ID '" + id + "' não encontrado!"));

        if (request.nome() != null && !request.nome().isBlank())
            existe.setNome(request.nome());

        if (request.mesesVida() != null && request.mesesVida() < 0)
            existe.setMesesVida(request.mesesVida());

        if (request.descricao() != null && !request.descricao().isBlank())
            existe.setDescricao(request.descricao());

        if (request.especie() != null)
            existe.setEspecie(request.especie());

        if (request.tamanho() != null)
            existe.setTamanho(request.tamanho());

        if (request.sexo() != null)
            existe.setSexo(request.sexo());

        if (request.statusAdocao() != null)
            existe.setStatusAdocao(request.statusAdocao());

        if (request.caracteristicas() != null)
            existe.setCaracteristicas(buscarCaracteristicasPorIds(request.caracteristicas()));

        return AnimalDTOResponse.toAnimalResponse(animalRepository.save(existe));
    }

    @Transactional
    public void excluir(Long id) {
        if (!animalRepository.existsById(id))
            throw new RecursoNaoEncontradoException("Animal com o ID " + id + " não foi encontrado.");

        animalRepository.deleteById(id);
    }

    private Set<Caracteristica> buscarCaracteristicasPorIds(List<Long> ids) {
        if (ids == null || ids.isEmpty())
            return new HashSet<>();

        List<Caracteristica> encontradas = caracteristicaRepository.findAllById(ids);

        if (encontradas.size() != ids.size())
            throw new RecursoNaoEncontradoException("Uma ou mais características informadas não foram encontradas.");

        return new HashSet<>(encontradas);
    }
}