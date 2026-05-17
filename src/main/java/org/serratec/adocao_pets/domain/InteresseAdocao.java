package org.serratec.adocao_pets.domain;

import java.time.LocalDateTime;

import org.serratec.adocao_pets.dto.InteresseAdocaoDTO;
import org.serratec.adocao_pets.enumerated.StatusProcesso;
import org.springframework.beans.BeanUtils;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Past;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @Past(message = "Data de pedido inválido. Preencha novamente!")
    private LocalDateTime dataPedido;

    @Enumerated(EnumType.STRING)
    private StatusProcesso statusProcesso;

    private String observacoes;
}
