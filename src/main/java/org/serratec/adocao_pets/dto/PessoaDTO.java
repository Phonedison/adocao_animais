package org.serratec.adocao_pets.dto;

import org.serratec.adocao_pets.domain.Endereco;
import org.serratec.adocao_pets.domain.Pessoa;
import org.serratec.adocao_pets.enumerated.TipoPessoa;
import org.springframework.beans.BeanUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PessoaDTO {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private Endereco endereco;
    private TipoPessoa tipoPessoa;

    public PessoaDTO(Pessoa pessoa) {
        BeanUtils.copyProperties(pessoa, this);
    }

}
