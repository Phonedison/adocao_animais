package org.serratec.adocao_pets.dto.response;

import org.serratec.adocao_pets.domain.Pessoa;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL) // oculta parametros informados como null
public class PessoaDTOResponse {
    private Long id;
    private String cpf;
    private String nome;
    private String telefone;
    private String email;

    private EnderecoDTOResponse endereco;

    public static PessoaDTOResponse toPessoaResponse(Pessoa pessoa) {
        PessoaDTOResponse response = new PessoaDTOResponse();
        response.setId(pessoa.getId());
        response.setNome(pessoa.getNome());
        response.setEmail(pessoa.getEmail());
        response.setCpf(pessoa.getCpf());
        response.setTelefone(pessoa.getTelefone());

        if (pessoa.getEndereco() != null) {
            response.setEndereco(EnderecoDTOResponse.toEnderecoResponse(pessoa.getEndereco()));
        }
        return response;
    }

    public static PessoaDTOResponse toPessoaResponseResumo(Pessoa pessoa) {
        PessoaDTOResponse response = new PessoaDTOResponse();

        response.setNome(pessoa.getNome());
        response.setTelefone(pessoa.getTelefone());
        response.setEmail(pessoa.getEmail());
        return response;
    }

}