package org.serratec.adocao_pets.dto.request;

import java.time.LocalDateTime;

import org.serratec.adocao_pets.enumerated.StatusProcesso;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // cria os métodos GETs
@Setter // cria os métodos SETs
@NoArgsConstructor
@AllArgsConstructor
public class InteresseAdocaoRequest {
        private Long pessoaId;
        private Long animalId;
        private LocalDateTime dataPedido;
        private StatusProcesso statusProcesso;
        private String observacoes;
}