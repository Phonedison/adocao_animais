package org.serratec.adocao_pets.dto.request;

import java.util.List;

import org.serratec.adocao_pets.enumerated.Especie;
import org.serratec.adocao_pets.enumerated.Sexo;
import org.serratec.adocao_pets.enumerated.StatusAdocao;
import org.serratec.adocao_pets.enumerated.Tamanho;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // cria os métodos GETs
@Setter // cria os métodos SETs
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDTORequest {

    private String nome;
    private Integer mesesVida;
    private String descricao;
    private Especie especie;
    private Tamanho tamanho;
    private Sexo sexo;
    private StatusAdocao statusAdocao;
    private List<Long> caracteristicasIds;
}