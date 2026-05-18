package org.serratec.adocao_pets.dto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.adocao_pets.domain.Animal;
import org.serratec.adocao_pets.domain.Caracteristica;
import org.serratec.adocao_pets.dto.request.AnimalRequest;
import org.serratec.adocao_pets.dto.response.AnimalResponse;

public class AnimalService {

    public static Animal toAnimal(AnimalRequest request) {

        Animal animal = new Animal();

        animal.setNome(request.getNome());
        animal.setMesesVida(request.getMesesVida());
        animal.setDescricao(request.getDescricao());
        animal.setEspecie(request.getEspecie());
        animal.setTamanho(request.getTamanho());
        animal.setSexo(request.getSexo());
        animal.setStatusAdocao(request.getStatusAdocao());

        if (request.getCaracteristicasIds() != null) {
            List<Caracteristica> caracteristicas = request.getCaracteristicasIds().stream()
                    .map(id -> {
                        Caracteristica c = new Caracteristica();
                        c.setId(id);
                        return c;
                    }).collect(Collectors.toList());

            animal.setCaracteristicas(caracteristicas);
        }

        return animal;

        /*
         * POSTMAPPING
         * no RequestBody AnimalRequest request
         * no controller deve chamar Animal animal = AnimalService.toAnimal(request);
         * 
         */
    }

    public static AnimalResponse toAnimalResponse(Animal animal) {

        AnimalResponse response = new AnimalResponse();

        response.setId(animal.getId());
        response.setNome(animal.getNome());
        response.setMesesVida(animal.getMesesVida());
        response.setDescricao(animal.getDescricao());
        response.setEspecie(animal.getEspecie());
        response.setTamanho(animal.getTamanho());
        response.setSexo(animal.getSexo());
        response.setStatusAdocao(animal.getStatusAdocao());

        if (animal.getCaracteristicas() != null) {
            List<Long> ids = animal.getCaracteristicas().stream()
                    .map(c -> c.getId()).collect(Collectors.toList());

            response.setCaracteristicasIds(ids);
        }

        return response;

        /*
         * POSTMAPPING
         * Animal animalSalvo = animalRepository.save(animal)
         * AnimalService service = AnimalService.toAnimalResponse(animal)
         * return ResponseEntity.status(Http.CREATED).body(animalResponse)
         */

    }
}
