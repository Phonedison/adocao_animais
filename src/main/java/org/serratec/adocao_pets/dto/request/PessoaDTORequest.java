package org.serratec.adocao_pets.dto.request;

import org.hibernate.validator.constraints.br.CPF;
import org.serratec.adocao_pets.domain.Pessoa;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Modelo de requisição para cadastro e atualização de dados de uma pessoa (Adotante/Doador)")
public record PessoaDTORequest(
                @Schema(description = "Nome completo da pessoa", example = "Marcia da Silva", requiredMode = Schema.RequiredMode.REQUIRED) @NotBlank(message = "O campo NOME não pode estar em branco") String nome,

                @Schema(description = "Endereço de e-mail principal", example = "marcia.silva@email.com", requiredMode = Schema.RequiredMode.REQUIRED) @Email(message = "Email inválido!") @NotBlank(message = "O campo EMAIL não pode estar em branco") String email,

                @Schema(description = "Cadastro de Pessoa Física (CPF) - Deve ser um número válido", example = "123.456.789-00", requiredMode = Schema.RequiredMode.REQUIRED) @CPF @NotBlank(message = "O campo não pode estar em branco") @Column(unique = true) String cpf,

                @Schema(description = "Número de telefone ou celular com DDD", example = "(24) 99999-1234", pattern = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$", requiredMode = Schema.RequiredMode.REQUIRED) @NotBlank(message = "O campo não pode estar em branco") @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$", message = "O formato do telefone é inválido (Ex: (99) 99999-9999)") String telefone,

                @Schema(description = "Dados de endereço residencial associados à pessoa", requiredMode = Schema.RequiredMode.REQUIRED) @NotNull(message = "Os dados de endereço são obrigatórios") @Valid EnderecoDTORequest endereco) {

        public Pessoa toPessoa() {
                Pessoa pessoa = new Pessoa();
                pessoa.setNome(this.nome());
                pessoa.setEmail(this.email());
                pessoa.setCpf(this.cpf());
                pessoa.setTelefone(this.telefone());
                return pessoa;
        }
}