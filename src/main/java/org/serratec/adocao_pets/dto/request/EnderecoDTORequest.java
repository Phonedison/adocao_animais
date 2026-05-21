package org.serratec.adocao_pets.dto.request;

import org.serratec.adocao_pets.domain.Endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EnderecoDTORequest(

        Long id,
        @NotBlank(message = "O RUA não pode estar em branco") String rua,
        @NotBlank(message = "O NUMERO não pode estar em branco") String numero,
        @NotBlank(message = "O BAIRRO não pode estar em branco") String bairro,
        @NotBlank(message = "A CIDADE não pode estar em branco") String cidade,
        @NotBlank(message = "O ESTADO não pode estar em branco") String estado,
        @NotBlank(message = "O CEP não pode estar em branco") @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve estar no formato 00000-000") String cep) {

    public static Endereco toEndereco(EnderecoDTORequest request) {

        Endereco endereco = new Endereco();

        endereco.setRua(request.rua());
        endereco.setNumero(request.numero());
        endereco.setBairro(request.bairro());
        endereco.setCidade(request.cidade());
        endereco.setEstado(request.estado());
        endereco.setCep(request.cep());
        return endereco;
    }

}
