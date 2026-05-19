package org.serratec.adocao_pets.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InteresseAdocaoPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "pessoa_id")
    private Long pessoaId;

    @Column(name = "animal_id")
    private Long animalId;
}
