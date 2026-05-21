package org.serratec.adocao_pets.dto.request;

import org.hibernate.validator.constraints.br.CPF;
import org.serratec.adocao_pets.domain.Pessoa;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PessoaDTORequest(
                @NotBlank(message = "O campo NOME não pode estar em branco") String nome,

                @Email(message = "Email inválido!") @NotBlank(message = "O campo EMAIL não pode estar em branco") String email,

                @CPF @NotBlank(message = "O campo não pode estar em branco") @Column(unique = true) String cpf,

                @NotBlank(message = "O campo não pode estar em branco") @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$", message = "O formato do telefone é inválido (Ex: (99) 99999-9999)") String telefone,

                EnderecoDTORequest endereco) {

        public Pessoa toPessoa() {
                Pessoa pessoa = new Pessoa();
                pessoa.setNome(this.nome());
                pessoa.setEmail(this.email());
                pessoa.setCpf(this.cpf());
                pessoa.setTelefone(this.telefone());
                return pessoa;
        }
}