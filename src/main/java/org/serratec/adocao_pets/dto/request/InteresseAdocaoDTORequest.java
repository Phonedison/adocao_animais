package org.serratec.adocao_pets.dto.request;

import java.time.LocalDateTime;

import org.serratec.adocao_pets.enumerated.StatusProcesso;

public record InteresseAdocaoDTORequest(Long pessoaId,
                Long animalId,
                LocalDateTime dataPedido,
                StatusProcesso statusProcesso,
                String observacoes) {
}