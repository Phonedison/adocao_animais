package org.serratec.adocao_pets.dto.service;

import org.serratec.adocao_pets.domain.Caracteristica;
import org.serratec.adocao_pets.dto.request.CaracteristicaRequest;
import org.serratec.adocao_pets.dto.response.CaracteristicaResponse;

public class CaracteristicaService {

    public static Caracteristica toCaracteristica(CaracteristicaRequest request) {
        Caracteristica caracteristica = new Caracteristica();

        caracteristica.setDescricao(request.getDescricao());

        return caracteristica;
    }

    /*
     * POSTMAPPING
     * no RequestBody CaracteristicaRequest request
     * no controller deve chamar Caracteristica caracteristica =
     * CaracteristicaService.toAnimal(request);
     * 
     */

    public static CaracteristicaResponse toCaracteristicaResponse(Caracteristica caracteristica) {
        CaracteristicaResponse response = new CaracteristicaResponse();

        response.setId(caracteristica.getId());
        response.setDescricao(caracteristica.getDescricao());

        return response;
    }
    /*
     * POSTMAPPING
     * Caracteristica CaracteristicaSalvo =
     * caracteristicaRepository.save(caracteristica)
     * CaracteristicaService service =
     * CaracteristicaService.toCaracteristicaResponse(caracteristica)
     * return ResponseEntity.status(Http.CREATED).body(caracteristicaResponse)
     */
}
