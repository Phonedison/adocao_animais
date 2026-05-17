package org.serratec.adocao_pets.domain;

import org.serratec.adocao_pets.enumerated.Especie;
import org.serratec.adocao_pets.enumerated.Sexo;
import org.serratec.adocao_pets.enumerated.StatusAdocao;
import org.serratec.adocao_pets.enumerated.Tamanho;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "animal")
@Getter // cria os métodos GETs
@Setter // cria os métodos SETs
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo não pode estar em branco")
    @Column
    private String nome;

    @NotNull(message = "O campo não pode estar nulo")
    @Positive(message = "Preencha o campo com valores acima de ZERO")
    @Column(name = "meses_vida")
    private Integer mesesVida;

    @Size(max = 100, message = "Campo com permissão de no máximo 100 caracteres")
    @Size(min = 10, message = "Campo com permissão de no mínimo 10 caracteres")
    @Column
    private String descricao; // sobre o pet

    // referencia a ENUM especie
    @OneToOne
    @NotBlank(message = "O campo não pode estar em branco")
    @Column
    private Especie especie;

    // referencia a ENUM tamanho
    @OneToOne
    @NotBlank(message = "O campo não pode estar em branco")
    @Column
    private Tamanho tamanho;
    // referencia a ENUM sexo

    @OneToOne
    @NotBlank(message = "O campo não pode estar em branco")
    @Column
    private Sexo sexo;
    // referencia a ENUM status da adocao
    @OneToOne
    @NotBlank(message = "O campo não pode estar em branco")
    @Column
    private StatusAdocao statusAdocao;
}
