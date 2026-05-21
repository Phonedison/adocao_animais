package org.serratec.adocao_pets.domain;

import java.time.LocalDateTime;

import org.serratec.adocao_pets.enumerated.StatusProcesso;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PastOrPresent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "interesse_adocao")
@Getter // cria os métodos GETs
@Setter // cria os métodos SETs
@NoArgsConstructor
@EqualsAndHashCode(of = "id")

public class InteresseAdocao {

    @EmbeddedId
    @Id
    private InteresseAdocaoPK id = new InteresseAdocaoPK();

    @ManyToOne
    @MapsId("pessoaId")
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    @ManyToOne
    @MapsId("animalId")
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @PastOrPresent(message = "Data de pedido inválido.")
    private LocalDateTime dataPedido;

    @Enumerated(EnumType.STRING)
    private StatusProcesso statusProcesso;

    private String observacoes;
}
