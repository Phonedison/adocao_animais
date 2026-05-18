package org.serratec.adocao_pets.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // cria os métodos GETs
@Setter // cria os métodos SETs
@NoArgsConstructor
@AllArgsConstructor
public class PessoaResponse {
    private Long id;
    private String nome;
    private String email;
    private String Cpf;
    private String telefone;
    private EnderecoResponse endereco;

}