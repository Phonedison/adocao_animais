package org.serratec.adocao_pets.dto.request;

import org.serratec.adocao_pets.domain.Endereco;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Modelo de requisição para cadastro e atualização de endereços")
public record EnderecoDTORequest(

        @Schema(hidden = true) Long id,
        @Schema(description = "Nome do logradouro / rua", example = "Avenida Barão do Rio Branco", requiredMode = Schema.RequiredMode.REQUIRED) @NotBlank(message = "O RUA não pode estar em branco") String rua,
        @Schema(description = "Número do imóvel ou 'S/N'", example = "1005", requiredMode = Schema.RequiredMode.REQUIRED) @NotBlank(message = "O NUMERO não pode estar em branco") String numero,
        @Schema(description = "Bairro onde se localiza o endereço", example = "Centro", requiredMode = Schema.RequiredMode.REQUIRED) @NotBlank(message = "O BAIRRO não pode estar em branco") String bairro,
        @Schema(description = "Nome do município / cidade", example = "Petrópolis", requiredMode = Schema.RequiredMode.REQUIRED) @NotBlank(message = "A CIDADE não pode estar em branco") String cidade,
        @Schema(description = "Unidade Federativa / Estado", example = "RJ", requiredMode = Schema.RequiredMode.REQUIRED) @NotBlank(message = "O ESTADO não pode estar em branco") String estado,
        @Schema(description = "Código de Endereçamento Postal (CEP)", example = "25620-000", pattern = "\\d{5}-\\d{3}", requiredMode = Schema.RequiredMode.REQUIRED) @NotBlank(message = "O CEP não pode estar em branco") @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve estar no formato 00000-000") String cep) {

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
