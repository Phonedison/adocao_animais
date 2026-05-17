package org.serratec.adocao_pets.dto;

import org.serratec.adocao_pets.domain.CaracteristicaAnimal;
import org.springframework.beans.BeanUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CaracteristicaAnimalDTO {

    private Long id;
    private String nome;

    public CaracteristicaAnimalDTO(CaracteristicaAnimal caracteristica) {
        BeanUtils.copyProperties(caracteristica, this);
    }

}
