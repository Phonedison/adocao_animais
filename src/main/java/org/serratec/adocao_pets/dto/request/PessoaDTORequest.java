package org.serratec.adocao_pets.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // cria os métodos GETs
@Setter // cria os métodos SETs
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDTORequest {
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private EnderecoDTORequest endereco;
}