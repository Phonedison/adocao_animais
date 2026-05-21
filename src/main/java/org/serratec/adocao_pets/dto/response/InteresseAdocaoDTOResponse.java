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
        "dataPedido",
        "statusProcesso",
        "pessoa",
        "animal",
        "observacoes" })
public class InteresseAdocaoDTOResponse {
    private Long id;
    private PessoaDTOResponse pessoa;
    private AnimalDTOResponse animal;
    private LocalDateTime dataPedido;
    private StatusProcesso statusProcesso;
    private String observacoes;

    public InteresseAdocaoDTOResponse(InteresseAdocao interesse) {
        this.dataPedido = interesse.getDataPedido();
        this.statusProcesso = interesse.getStatusProcesso();
        this.observacoes = interesse.getObservacoes();
    }

    public static InteresseAdocaoDTOResponse toInteresseAdocaoResponse(InteresseAdocao interesse) {
        InteresseAdocaoDTOResponse response = new InteresseAdocaoDTOResponse();

        if (interesse.getPessoa() != null)
            response.setPessoa(PessoaDTOResponse.toPessoaResponse(interesse.getPessoa()));

        if (interesse.getAnimal() != null)
            response.setAnimal(AnimalDTOResponse.toAnimalResponse(interesse.getAnimal()));

        response.setDataPedido(interesse.getDataPedido());
        response.setStatusProcesso(interesse.getStatusProcesso());
        response.setObservacoes(interesse.getObservacoes());

        return response;
    }
}