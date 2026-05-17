package org.serratec.adocao_pets.dto;

import org.serratec.adocao_pets.domain.Animal;
import org.serratec.adocao_pets.enumerated.Especie;
import org.serratec.adocao_pets.enumerated.Sexo;
import org.serratec.adocao_pets.enumerated.StatusAdocao;
import org.serratec.adocao_pets.enumerated.Tamanho;
import org.springframework.beans.BeanUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
// DTO -> Representa os dados de transferência
public class AnimalDTO {
    private Long id;

    private String nome;
    private Integer mesesVida;
    private String descricao; // sobre o pet

    // referencia a ENUM especie
    private Especie especie;
    // referencia a ENUM tamanho
    private Tamanho tamanho;
    // referencia a ENUM sexo
    private Sexo sexo;
    // referencia a ENUM status da adocao
    private StatusAdocao statusAdocao;

    public AnimalDTO(Animal animal) {
        BeanUtils.copyProperties(animal, this);
    }
}
