package org.serratec.adocao_pets.dto.response;

import org.serratec.adocao_pets.domain.Endereco;

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
        "rua",
        "numero",
        "bairro",
        "cidade",
        "estado",
        "cep" })
@Schema(description = "Modelo de resposta contendo os dados de um endereço cadastrado")
public class EnderecoDTOResponse {

    @Schema(description = "Identificador único do endereço no banco de dados", example = "10")
    private Long id;

    @Schema(description = "Nome do logradouro / rua", example = "Avenida Barão do Rio Branco")
    private String rua;

    @Schema(description = "Número do imóvel residencial ou comercial", example = "1005")
    private String numero;

    @Schema(description = "Bairro", example = "Centro")
    private String bairro;

    @Schema(description = "Nome do município / cidade", example = "Petrópolis")
    private String cidade;

    @Schema(description = "Unidade Federativa / Estado", example = "RJ")
    private String estado;

    @Schema(description = "Código de Endereçamento Postal (CEP) formatado", example = "25620-000")
    private String cep;

    public static EnderecoDTOResponse toEnderecoResponse(Endereco endereco) {

        EnderecoDTOResponse response = new EnderecoDTOResponse();
        response.setId(endereco.getId());
        response.setRua(endereco.getRua());
        response.setNumero(endereco.getNumero());
        response.setBairro(endereco.getBairro());
        response.setCidade(endereco.getCidade());
        response.setEstado(endereco.getEstado());
        response.setCep(endereco.getCep());
        return response;
    }
}