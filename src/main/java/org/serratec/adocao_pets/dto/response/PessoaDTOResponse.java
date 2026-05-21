package org.serratec.adocao_pets.dto.response;

import org.serratec.adocao_pets.domain.Pessoa;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Modelo de resposta contendo os dados detalhados de uma pessoa cadastrada")
public class PessoaDTOResponse {

    @Schema(description = "Identificador único da pessoa no banco de dados", example = "2")
    private Long id;

    @Schema(description = "Cadastro de Pessoa Física (CPF) com máscara", example = "123.456.789-00")
    private String cpf;

    @Schema(description = "Nome completo cadastrado", example = "Marcia da Silva")
    private String nome;

    @Schema(description = "Número de telefone para contato formatado", example = "(24) 99999-1234")
    private String telefone;

    @Schema(description = "Endereço de e-mail cadastrado", example = "marcia.silva@email.com")
    private String email;

    @Schema(description = "Dados detalhados do endereço residencial da pessoa")
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