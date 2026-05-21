package org.serratec.adocao_pets.dto.response;

import java.time.LocalDateTime;

import org.serratec.adocao_pets.domain.InteresseAdocao;
import org.serratec.adocao_pets.domain.InteresseAdocaoPK;
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
        "dataPedido",
        "statusProcesso",
        "observacoes",
        "pessoa",
        "animal"
})
public class InteresseAdocaoDTOResponse {
    private InteresseAdocaoPK id;
    private PessoaDTOResponse pessoa;
    private AnimalDTOResponse animal;
    private LocalDateTime dataPedido;
    private StatusProcesso statusProcesso;
    private String observacoes;

    public InteresseAdocaoDTOResponse(InteresseAdocao interesse) {
        this.id = interesse.getId();
        this.dataPedido = interesse.getDataPedido();
        this.statusProcesso = interesse.getStatusProcesso();
        this.observacoes = interesse.getObservacoes();
    }

    public static InteresseAdocaoDTOResponse toInteresseAdocaoResponse(InteresseAdocao interesse) {
        InteresseAdocaoDTOResponse response = new InteresseAdocaoDTOResponse();

        if (interesse.getId() != null)
            response.setId(interesse.getId());

        if (interesse.getPessoa() != null)
            response.setPessoa(PessoaDTOResponse.toPessoaResponseResumo(interesse.getPessoa()));

        if (interesse.getAnimal() != null)
            response.setAnimal(AnimalDTOResponse.toAnimalResponseResumo(interesse.getAnimal()));

        response.setDataPedido(interesse.getDataPedido());
        response.setStatusProcesso(interesse.getStatusProcesso());
        response.setObservacoes(interesse.getObservacoes());

        return response;
    }
}