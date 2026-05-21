package org.serratec.adocao_pets.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pessoa")
@Getter // cria os métodos GETs
@Setter // cria os métodos SETs
@NoArgsConstructor
@EqualsAndHashCode(of = "id")

public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo não pode estar em branco")
    private String nome;

    @Email(message = "Email inválido!")
    @NotBlank(message = "O campo não pode estar em branco")
    private String email;

    @NotBlank(message = "O campo não pode estar em branco")
    @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$", message = "O formato do telefone é inválido (Ex: (99) 99999-9999)")
    private String telefone;

    // @CPF
    @NotBlank(message = "O campo não pode estar em branco")
    @Column(unique = true)
    private String cpf;

    // referencia a classe Endereco
    @NotNull(message = "O endereço não pode ser nulo")
    @ManyToOne // pode ser ligação 1:1 ou N:1
    @JoinColumn(name = "endereco_id", referencedColumnName = "id") // vinculo
    private Endereco endereco;
}
