package org.serratec.adocao_pets.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // cria os métodos GETs
@Setter // cria os métodos SETs
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "id",
        "cpf",
        "nome",
        "telefone",
        "email",
        "endereco" })
public class PessoaDTOResponse {
    private Long id;
    private String Cpf;
    private String nome;
    private String telefone;
    private String email;
    private EnderecoDTOResponse endereco;

}