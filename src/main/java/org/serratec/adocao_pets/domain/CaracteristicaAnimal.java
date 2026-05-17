package org.serratec.adocao_pets.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private Long id;

    @Column
    @NotBlank(message = "Preencha o campo nome da descrição do animal")
    private String nome; // exemplo: "Castrado", "Vacinado", "Dócil", "Brincalhão", "Precisa de quintal";
}
