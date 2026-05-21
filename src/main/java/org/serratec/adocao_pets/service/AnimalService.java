package org.serratec.adocao_pets.service;

import java.util.List;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private static CaracteristicaRepository caracteristicaRepository;

    // Métodos para o GETS
    public List<AnimalDTOResponse> listarTodos() {
        List<Animal> animais = animalRepository.findAll();
        return animais.stream().map(AnimalDTOResponse::toAnimalResponse).toList();
    }

    public ResponseEntity<AnimalDTOResponse> buscar(Long id) throws RecursoNaoEncontradoException {
        return animalRepository.findById(id)
                .map(AnimalDTOResponse::toAnimalResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Animal de ID '" + id + "' não encontrado!"));
    }

    public ResponseEntity<List<AnimalDTOResponse>> buscarNome(String nome) throws RecursoNaoEncontradoException {
        List<AnimalDTOResponse> animais = animalRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(AnimalDTOResponse::toAnimalResponse)
                .collect(Collectors.toList());

        if (animais.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum animal com nome contendo '" + nome + "' encontrado!");
        }
        return ResponseEntity.ok(animais);
    }

    public ResponseEntity<List<AnimalDTOResponse>> buscarTamanho(String tamanho) throws RecursoNaoEncontradoException {
        try {
            Tamanho tamanhoEnum = Tamanho.valueOf(tamanho.toUpperCase());
            List<AnimalDTOResponse> animais = animalRepository.findByTamanho(tamanhoEnum)
                    .stream()
                    .map(AnimalDTOResponse::toAnimalResponse)
                    .collect(Collectors.toList());

            if (animais.isEmpty()) {
                throw new RecursoNaoEncontradoException("Nenhum animal com tamanho '" + tamanho + "' encontrado!");
            }
            return ResponseEntity.ok(animais);
        } catch (IllegalArgumentException e) {
            throw new RecursoNaoEncontradoException("Tamanho '" + tamanho + "' é inválido!");
        }
    }

    public ResponseEntity<List<AnimalDTOResponse>> buscarEspecies(String especie) throws RecursoNaoEncontradoException {
        try {
            Especie especieEnum = Especie.valueOf(especie.toUpperCase());
            List<AnimalDTOResponse> animais = animalRepository.findByEspecie(especieEnum)
                    .stream()
                    .map(AnimalDTOResponse::toAnimalResponse)
                    .collect(Collectors.toList());

            if (animais.isEmpty()) {
                throw new RecursoNaoEncontradoException("Nenhum animal da espécie '" + especie + "' encontrado!");
            }
            return ResponseEntity.ok(animais);
        } catch (IllegalArgumentException e) {
            throw new RecursoNaoEncontradoException("Espécie '" + especie + "' é inválida!");
        }
    }

    public ResponseEntity<List<AnimalDTOResponse>> buscarSexo(String sexo) throws RecursoNaoEncontradoException {
        try {
            Sexo sexoEnum = Sexo.valueOf(sexo.toUpperCase());
            List<AnimalDTOResponse> animais = animalRepository.findBySexo(sexoEnum)
                    .stream()
                    .map(AnimalDTOResponse::toAnimalResponse)
                    .collect(Collectors.toList());

            if (animais.isEmpty()) {
                throw new IllegalArgumentException("Nenhum animal do sexo '" + sexo + "' encontrado!");
            }
            return ResponseEntity.ok(animais);
        } catch (IllegalArgumentException e) {
            throw new RecursoNaoEncontradoException("Sexo '" + sexo + "' é inválido!");
        }
    }

    public ResponseEntity<List<AnimalDTOResponse>> buscarAdocao(String adocao) throws RecursoNaoEncontradoException {
        try {
            StatusAdocao statusEnum = StatusAdocao.valueOf(adocao.toUpperCase());
            List<AnimalDTOResponse> animais = animalRepository.findByStatusAdocao(statusEnum)
                    .stream()
                    .map(AnimalDTOResponse::toAnimalResponse)
                    .collect(Collectors.toList());

            if (animais.isEmpty()) {
                throw new RecursoNaoEncontradoException(
                        "Nenhum animal com status de adoção '" + adocao + "' encontrado!");
            }
            return ResponseEntity.ok(animais);
        } catch (RecursoNaoEncontradoException e) {
            throw new RecursoNaoEncontradoException("Status de adoção '" + adocao + "' é inválido!");
        }
    }

    public ResponseEntity<List<CaracteristicaDTOResponse>> listarCaracteristicas(Long id) {
        return animalRepository.findById(id)
                .map(animal -> {
                    List<CaracteristicaDTOResponse> dtos = animal.getCaracteristicas().stream()
                            .map(c -> new CaracteristicaDTOResponse(c.getId(), c.getDescricao())).toList();
                    return ResponseEntity.ok(dtos);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Métodos para o POST
    public AnimalDTOResponse salvar(AnimalDTORequest request) {
        Animal animal = toAnimal(request);
        Animal salvo = animalRepository.save(animal);
        return toAnimalResponse(salvo);
    }

    public List<AnimalDTOResponse> salvarList(List<AnimalDTORequest> request) {
        List<Animal> animais = request.stream().map(AnimalDTORequest::toAnimal).toList();
        List<Animal> salvo = animalRepository.saveAll(animais);

        return salvo.stream().map(AnimalDTOResponse::toAnimalResponse).toList();
    }

    // Métodos para o PUT
    public ResponseEntity<AnimalDTOResponse> atualizar(Long id, AnimalDTORequest request) {
        return animalRepository.findById(id).map(existe -> {

            existe.setNome(request.nome());
            existe.setMesesVida(request.mesesVida());
            existe.setDescricao(request.descricao());
            existe.setEspecie(request.especie());
            existe.setTamanho(request.tamanho());
            existe.setSexo(request.sexo());
            existe.setStatusAdocao(request.statusAdocao());

            Animal salvo = animalRepository.save(existe);
            AnimalDTOResponse response = AnimalDTOResponse.toAnimalResponse(salvo);

            return ResponseEntity.ok(response);

        }).orElse(ResponseEntity.notFound().build());
    }

    // Métodos DELETE
    public void excluir(Long id) {
        animalRepository.findById(id).ifPresentOrElse(
                animalRepository::delete,
                () -> {
                    throw new RecursoNaoEncontradoException("Animal com o ID " + id + " não foi encontrado.");
                });
    }
}
