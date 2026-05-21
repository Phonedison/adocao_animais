package org.serratec.adocao_pets.dto.response;

import org.serratec.adocao_pets.domain.Endereco;

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
        "rua",
        "numero",
        "bairro",
        "cidade",
        "estado",
        "cep" })
public class EnderecoDTOResponse {
    private Long id;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
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