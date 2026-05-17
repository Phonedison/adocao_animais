package org.serratec.adocao_pets.dto;

import org.serratec.adocao_pets.domain.Endereco;
import org.springframework.beans.BeanUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EnderecoDTO {

    private Long id;
    private String rua;
    private String numero;
    private String bairro;
    private String estado;
    private String cep;

    public EnderecoDTO(Endereco endereco) {
        BeanUtils.copyProperties(endereco, this);
    }
}
