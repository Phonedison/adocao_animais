package org.serratec.adocao_pets.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.serratec.adocao_pets.enumerated.Especie;
import org.serratec.adocao_pets.enumerated.Sexo;
import org.serratec.adocao_pets.enumerated.StatusAdocao;
import org.serratec.adocao_pets.enumerated.Tamanho;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter // cria os métodos GETs
@Setter // cria os métodos SETs
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Entity
@Table(name = "animal")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo não pode estar em branco")
    private String nome;

    @NotNull(message = "O campo não pode estar nulo")
    @Positive(message = "Preencha o campo com valores acima de ZERO")
    @Column(name = "meses_vida")
    private Integer mesesVida;

    @NotBlank(message = "O campo não pode estar nulo")
    @Size(max = 100, message = "Campo com permissão de no máximo 100 caracteres")
    @Size(min = 10, message = "Campo com permissão de no mínimo 10 caracteres")
    private String descricao; // sobre o pet

    // referencia a ENUM especie
    @NotNull(message = "A espécie deve ser informada, informe como CACHORRO ou GATO")
    @Enumerated(EnumType.STRING)
    private Especie especie;

    // referencia a ENUM tamanho
    @NotNull(message = "O status de adoção deve ser informado como PEQUENO, MEDIO ou GRANDE")
    @Enumerated(EnumType.STRING)
    private Tamanho tamanho;

    // referencia a ENUM sexo
    @NotNull(message = "O sexo deve ser informado como MACHO ou FEMEA")
    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    // referencia a ENUM status da adocao
    @NotNull(message = "Campo status deve ser informado como PENDENTE, APROVADO, REJEITADO ou CANCELADO")
    @Enumerated(EnumType.STRING)
    private StatusAdocao statusAdocao;

    @ManyToMany
    @JoinTable(name = "animal_caracteristica", joinColumns = @JoinColumn(name = "animal_id"), inverseJoinColumns = @JoinColumn(name = "caracteristica_id"))
    // private List<Caracteristica> caracteristicas;
    private Set<Caracteristica> caracteristicas = new HashSet<>();

    @OneToMany(mappedBy = "animal", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<InteresseAdocao> interesses = new ArrayList<>();

}
