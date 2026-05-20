package org.serratec.adocao_pets.dto.response;

import java.time.LocalDateTime;

import org.serratec.adocao_pets.domain.InteresseAdocao;
import org.serratec.adocao_pets.enumerated.StatusProcesso;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // cria os métodos GETs
@Setter // cria os métodos SETs
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "id",
        "pessoa_id",
        "animal_id",
        "dataPedido",
        "statusProcesso",
        "observacoes" })
public class InteresseAdocaoDTOResponse {
    private Long pessoaId;
    private Long animalId;
    private PessoaDTOResponse pessoa;
    private AnimalDTOResponse animal;
    private LocalDateTime dataPedido;
    private StatusProcesso statusProcesso;
    private String observacoes;

    public InteresseAdocaoDTOResponse(InteresseAdocao interesse) {
        this.pessoaId = interesse.getId().getPessoaId();
        this.animalId = interesse.getId().getAnimalId();
        this.dataPedido = interesse.getDataPedido();
        this.statusProcesso = interesse.getStatusProcesso();
        this.observacoes = interesse.getObservacoes();
    }
}