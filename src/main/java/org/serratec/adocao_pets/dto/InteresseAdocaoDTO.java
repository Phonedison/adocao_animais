package org.serratec.adocao_pets.dto;

import java.time.LocalDateTime;

import org.serratec.adocao_pets.domain.Animal;
import org.serratec.adocao_pets.domain.InteresseAdocao;
import org.serratec.adocao_pets.domain.Pessoa;
import org.serratec.adocao_pets.enumerated.StatusProcesso;
import org.springframework.beans.BeanUtils;

public class InteresseAdocaoDTO {

    private Long id;

    private Pessoa pessoa;

    private Animal animal;

    private LocalDateTime dataPedido;

    private StatusProcesso statusProcesso;

    private String observacoes;

    public InteresseAdocaoDTO(InteresseAdocao interesse) {
        BeanUtils.copyProperties(interesse, this);
    }
}
