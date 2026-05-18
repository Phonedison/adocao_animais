package org.serratec.adocao_pets.dto.response;

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
public class AnimalResponse {
    Long id;
    String nome;
    Integer mesesVida;
    String descricao;
    Especie especie;
    Tamanho tamanho;
    Sexo sexo;
    StatusAdocao statusAdocao;
    List<Long> caracteristicas;
}