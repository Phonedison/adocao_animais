package org.serratec.adocao_pets.domain;

import org.hibernate.validator.constraints.UniqueElements;
import org.hibernate.validator.constraints.br.CPF;
import org.serratec.adocao_pets.dto.PessoaDTO;
import org.serratec.adocao_pets.enumerated.TipoPessoa;
import org.springframework.beans.BeanUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @Column
    private String nome;

    @Email
    @NotBlank(message = "O campo não pode estar em branco")
    @Column
    private String email;

    @NotBlank(message = "O campo não pode estar em branco")
    @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$", message = "O formato do telefone é inválido (Ex: (99) 99999-9999)")
    @Column
    private String telefone;

    @CPF
    @NotBlank(message = "O campo não pode estar em branco")
    @Column
    @UniqueElements(message = "Campo duplicado!")
    private String cpf;

    // referencia a classe Endereco
    @NotBlank(message = "O campo não pode estar em branco")
    @Column
    private Endereco endereco;

    // referencia a ENUM tipoPessoa
    @NotBlank(message = "O campo não pode estar em branco")
    @Column
    private TipoPessoa tipoPessoa;

    public Pessoa(PessoaDTO pessoa) {
        BeanUtils.copyProperties(pessoa, this);
    }

}
