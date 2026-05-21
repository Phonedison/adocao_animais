package org.serratec.adocao_pets.dto.response;

import org.serratec.adocao_pets.domain.Caracteristica;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // cria os métodos GETs
@Setter // cria os métodos SETs
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "id", "descricao" })
public class CaracteristicaDTOResponse {
    Long id;
    String descricao;

    public static CaracteristicaDTOResponse toCaracteristicaResponse(Caracteristica caracteristica) {
        CaracteristicaDTOResponse response = new CaracteristicaDTOResponse();

        response.setId(caracteristica.getId());
        response.setDescricao(caracteristica.getDescricao());

        return response;
    }
}