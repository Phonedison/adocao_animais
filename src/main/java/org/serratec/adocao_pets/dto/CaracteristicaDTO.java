package org.serratec.adocao_pets.dto;

import org.serratec.adocao_pets.domain.Caracteristica;
import org.springframework.beans.BeanUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaracteristicaDTO {

    private Long id;
    private String nome;
    private String descricao;

    public CaracteristicaDTO(Caracteristica caracteristica) {
        BeanUtils.copyProperties(caracteristica, this);
    }

}
