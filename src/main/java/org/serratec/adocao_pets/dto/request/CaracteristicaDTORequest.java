package org.serratec.adocao_pets.dto.request;

import org.serratec.adocao_pets.domain.Caracteristica;

public record CaracteristicaDTORequest(String descricao) {

    public static Caracteristica toCaracteristica(CaracteristicaDTORequest request) {
        Caracteristica caracteristica = new Caracteristica();
        caracteristica.setDescricao(request.descricao());

        return caracteristica;
    }
}