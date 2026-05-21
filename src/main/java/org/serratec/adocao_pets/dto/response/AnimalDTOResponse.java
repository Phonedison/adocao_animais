package org.serratec.adocao_pets.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.adocao_pets.domain.Animal;
import org.serratec.adocao_pets.enumerated.Especie;
import org.serratec.adocao_pets.enumerated.Sexo;
import org.serratec.adocao_pets.enumerated.StatusAdocao;
import org.serratec.adocao_pets.enumerated.Tamanho;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // cria os métodos GETs
@Setter // cria os métodos SETs
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "id",
        "nome",
        "mesesVida",
        "especie",
        "tamanho",
        "sexo",
        "descricao",
        "caracteristicas",
        "statusAdocao"
})
public class AnimalDTOResponse {
    Long id;
    String nome;
    Integer mesesVida;
    String descricao;
    Especie especie;
    Tamanho tamanho;
    Sexo sexo;
    StatusAdocao statusAdocao;
    List<CaracteristicaDTOResponse> caracteristicas;

    public AnimalDTOResponse(Animal animal) {
        this.id = animal.getId();
        this.nome = animal.getNome();
        this.mesesVida = animal.getMesesVida();
        this.especie = animal.getEspecie();
        this.tamanho = animal.getTamanho();
        this.sexo = animal.getSexo();
        this.descricao = animal.getDescricao();
        this.statusAdocao = animal.getStatusAdocao();

        if (animal.getCaracteristicas() != null) {
            this.caracteristicas = animal.getCaracteristicas().stream()
                    .map(caracteristica -> {
                        CaracteristicaDTOResponse dto = new CaracteristicaDTOResponse();
                        dto.setId(caracteristica.getId());
                        dto.setDescricao(caracteristica.getDescricao());
                        return dto;
                    })
                    .collect(Collectors.toList());
        }
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
            response.setCaracteristicas(dto);
        }
        return response;
    }

}