package org.serratec.adocao_pets.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "caracteristica_animal")
@Getter // cria os métodos GETs
@Setter // cria os métodos SETs
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class CaracteristicaAnimal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "O NOME não pode estar em branco")
    private String nome; // exemplo: "Castrado", "Vacinado", "Dócil", "Brincalhão", "Precisa de quintal";
}
