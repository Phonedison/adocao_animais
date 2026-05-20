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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    public static Animal toAnimal(AnimalDTORequest request) {
        Animal animal = new Animal();
        animal.setNome(request.getNome());
        animal.setMesesVida(request.getMesesVida());
        animal.setDescricao(request.getDescricao());
        animal.setEspecie(request.getEspecie());
        animal.setTamanho(request.getTamanho());
        animal.setSexo(request.getSexo());
        animal.setStatusAdocao(request.getStatusAdocao());

        if (request.getCaracteristicasIds() != null) {
            Set<Caracteristica> caracteristicas = request.getCaracteristicasIds().stream()
                    .map(id -> {
                        Caracteristica c = new Caracteristica();
                        c.setId(id);
                        return c;
                    })
                    .collect(Collectors.toSet());
            animal.setCaracteristicas(caracteristicas);
        }
        return animal;
    }

    public static AnimalDTOResponse toAnimalResponse(Animal animal) {
        AnimalDTOResponse response = new AnimalDTOResponse();
        response.setId(animal.getId());
        response.setNome(animal.getNome());
        response.setMesesVida(animal.getMesesVida());
        response.setDescricao(animal.getDescricao());
        response.setEspecie(animal.getEspecie());
        response.setTamanho(animal.getTamanho());
        response.setSexo(animal.getSexo());
        response.setStatusAdocao(animal.getStatusAdocao());

        if (animal.getCaracteristicas() != null) {
            List<CaracteristicaDTOResponse> dto = animal.getCaracteristicas().stream()
                    .map(c -> new CaracteristicaDTOResponse(c.getId(), c.getDescricao()))
                    .collect(Collectors.toList());
            response.setCaracteristicasIds(dto);
        }
        return response;
    }

    // Métodos para o GETS
    public List<AnimalDTOResponse> listarTodos() {
        List<Animal> animais = animalRepository.findAll();
        return animais.stream().map(AnimalService::toAnimalResponse).toList();
    }

    public ResponseEntity<AnimalDTOResponse> buscar(Long id) throws RecursoNaoEncontradoException {
        return animalRepository.findById(id)
                .map(AnimalService::toAnimalResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Animal de ID '" + id + "' não encontrado!"));
    }

    public ResponseEntity<List<AnimalDTOResponse>> buscarNome(String nome) throws RecursoNaoEncontradoException {
        List<AnimalDTOResponse> animais = animalRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(AnimalService::toAnimalResponse)
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
                    .map(AnimalService::toAnimalResponse)
                    .collect(Collectors.toList());

            if (animais.isEmpty()) {
                throw new RecursoNaoEncontradoException("Nenhum animal com tamanho '" + tamanho + "' encontrado!");
            }
            return ResponseEntity.ok(animais);
        } catch (RecursoNaoEncontradoException e) {
            throw new RecursoNaoEncontradoException("Tamanho '" + tamanho + "' é inválido!");
        }
    }

    public ResponseEntity<List<AnimalDTOResponse>> buscarEspecies(String especie) throws RecursoNaoEncontradoException {
        try {
            Especie especieEnum = Especie.valueOf(especie.toUpperCase());
            List<AnimalDTOResponse> animais = animalRepository.findByEspecie(especieEnum)
                    .stream()
                    .map(AnimalService::toAnimalResponse)
                    .collect(Collectors.toList());

            if (animais.isEmpty()) {
                throw new RecursoNaoEncontradoException("Nenhum animal da espécie '" + especie + "' encontrado!");
            }
            return ResponseEntity.ok(animais);
        } catch (RecursoNaoEncontradoException e) {
            throw new RecursoNaoEncontradoException("Espécie '" + especie + "' é inválida!");
        }
    }

    public ResponseEntity<List<AnimalDTOResponse>> buscarSexo(String sexo) throws RecursoNaoEncontradoException {
        try {
            Sexo sexoEnum = Sexo.valueOf(sexo.toUpperCase());
            List<AnimalDTOResponse> animais = animalRepository.findBySexo(sexoEnum)
                    .stream()
                    .map(AnimalService::toAnimalResponse)
                    .collect(Collectors.toList());

            if (animais.isEmpty()) {
                throw new RecursoNaoEncontradoException("Nenhum animal do sexo '" + sexo + "' encontrado!");
            }
            return ResponseEntity.ok(animais);
        } catch (RecursoNaoEncontradoException e) {
            throw new RecursoNaoEncontradoException("Sexo '" + sexo + "' é inválido!");
        }
    }

    public ResponseEntity<List<AnimalDTOResponse>> buscarAdocao(String adocao) throws RecursoNaoEncontradoException {
        try {
            StatusAdocao statusEnum = StatusAdocao.valueOf(adocao.toUpperCase());
            List<AnimalDTOResponse> animais = animalRepository.findByStatusAdocao(statusEnum)
                    .stream()
                    .map(AnimalService::toAnimalResponse)
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
        List<Animal> animais = request.stream().map(AnimalService::toAnimal).toList();
        List<Animal> salvo = animalRepository.saveAll(animais);

        return salvo.stream().map(AnimalService::toAnimalResponse).toList();
    }

    // Métodos para o PUT
    public ResponseEntity<AnimalDTOResponse> atualizar(Long id, AnimalDTORequest request) {
        return animalRepository.findById(id).map(existe -> {

            existe.setNome(request.getNome());
            existe.setMesesVida(request.getMesesVida());
            existe.setDescricao(request.getDescricao());
            existe.setEspecie(request.getEspecie());
            existe.setTamanho(request.getTamanho());
            existe.setSexo(request.getSexo());
            existe.setStatusAdocao(request.getStatusAdocao());

            Animal salvo = animalRepository.save(existe);
            AnimalDTOResponse response = toAnimalResponse(salvo);

            return ResponseEntity.ok(response);

        }).orElse(ResponseEntity.notFound().build());
    }

    // Métodos DELETE
    public boolean excluir(Long id) {
        return animalRepository.findById(id).map(animal -> {
            animalRepository.delete(animal);
            return true;
        }).orElse(false);
    }
}
