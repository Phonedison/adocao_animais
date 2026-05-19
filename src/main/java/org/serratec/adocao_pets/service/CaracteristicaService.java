package org.serratec.adocao_pets.service;

import org.serratec.adocao_pets.domain.Caracteristica;
import org.serratec.adocao_pets.dto.request.CaracteristicaDTORequest;
import org.serratec.adocao_pets.dto.response.CaracteristicaDTOResponse;
import org.springframework.stereotype.Service;

@Service
public class CaracteristicaService {

    public static Caracteristica toCaracteristica(CaracteristicaDTORequest request) {
        Caracteristica caracteristica = new Caracteristica();

        caracteristica.setDescricao(request.getDescricao());

        return caracteristica;
    }

    /*
     * POSTMAPPING
     * no RequestBody CaracteristicaDTORequest request
     * no controller deve chamar Caracteristica caracteristica =
     * CaracteristicaService.toAnimal(request);
     * 
     */

    public static CaracteristicaDTOResponse toCaracteristicaResponse(Caracteristica caracteristica) {
        CaracteristicaDTOResponse response = new CaracteristicaDTOResponse();

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
