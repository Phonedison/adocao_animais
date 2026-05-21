package org.serratec.adocao_pets.dto.response;

import org.serratec.adocao_pets.domain.Caracteristica;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // cria os métodos GETs
@Setter // cria os métodos SETs
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "id", "descricao" })
@Schema(description = "Modelo de resposta que representa uma característica do pet")
public class CaracteristicaDTOResponse {

    @Schema(description = "Identificador único da característica no banco de dados", example = "5")
    Long id;

    @Schema(description = "Texto descritivo do traço físico ou comportamental", example = "Dócil")
    String descricao;

    public static CaracteristicaDTOResponse toCaracteristicaResponse(Caracteristica caracteristica) {
        CaracteristicaDTOResponse response = new CaracteristicaDTOResponse();

        response.setId(caracteristica.getId());
        response.setDescricao(caracteristica.getDescricao());

        return response;
    }
}