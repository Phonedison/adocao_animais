package org.serratec.adocao_pets.dto.request;

import org.serratec.adocao_pets.domain.Caracteristica;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Modelo de requisição para cadastro e atualização de características dos pets")
public record CaracteristicaDTORequest(
        @Schema(description = "Descrição da característica física ou comportamental do pet", example = "Dócil", requiredMode = Schema.RequiredMode.REQUIRED) @NotBlank(message = "O campo não pode estar em branco") String descricao) {

    public static Caracteristica toCaracteristica(CaracteristicaDTORequest request) {
        Caracteristica caracteristica = new Caracteristica();
        caracteristica.setDescricao(request.descricao());

        return caracteristica;
    }
}