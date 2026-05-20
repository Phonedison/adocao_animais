package org.serratec.adocao_pets.dto.request;

import java.util.List;

import org.serratec.adocao_pets.enumerated.Especie;
import org.serratec.adocao_pets.enumerated.Sexo;
import org.serratec.adocao_pets.enumerated.StatusAdocao;
import org.serratec.adocao_pets.enumerated.Tamanho;

public record AnimalDTORequest(String nome,
                Integer mesesVida,
                String descricao,
                Especie especie,
                Tamanho tamanho,
                Sexo sexo,
                StatusAdocao statusAdocao,
                List<Long> caracteristicas) {
}