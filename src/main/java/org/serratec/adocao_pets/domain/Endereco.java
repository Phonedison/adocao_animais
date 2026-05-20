package org.serratec.adocao_pets.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "endereco")
@Getter // cria os métodos GETs
@Setter // cria os métodos SETs
@NoArgsConstructor
@EqualsAndHashCode(of = "id")

public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O RUA não pode estar em branco")
    private String rua;

    @NotBlank(message = "O NUMERO não pode estar em branco")
    private String numero;

    @NotBlank(message = "O BAIRRO não pode estar em branco")
    private String bairro;

    @NotBlank(message = "A CIDADE não pode estar em branco")
    private String cidade;

    @NotBlank(message = "O ESTADO não pode estar em branco")
    private String estado;

    @NotBlank(message = "O CEP não pode estar em branco")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve estar no formato 00000-000")
    private String cep;

}
