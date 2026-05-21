package org.serratec.adocao_pets.dto.request;

import org.serratec.adocao_pets.domain.Caracteristica;

import jakarta.validation.constraints.NotBlank;

public record CaracteristicaDTORequest(@NotBlank(message = "O campo não pode estar em branco") String descricao) {

    public static Caracteristica toCaracteristica(CaracteristicaDTORequest request) {
        Caracteristica caracteristica = new Caracteristica();
        caracteristica.setDescricao(request.descricao());

        return caracteristica;
    }
}