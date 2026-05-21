package org.serratec.adocao_pets.dto.response;

import java.time.LocalDateTime;

import org.serratec.adocao_pets.domain.InteresseAdocao;
import org.serratec.adocao_pets.domain.InteresseAdocaoPK;
import org.serratec.adocao_pets.enumerated.StatusProcesso;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
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

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Modelo de resposta detalhado contendo a intenção de adoção registrada")
public class InteresseAdocaoDTOResponse {

    @Schema(description = "Chave composta contendo os identificadores vinculados de Pessoa e Animal")
    private InteresseAdocaoPK id;

    @Schema(description = "Dados resumidos da pessoa interessada na adoção")
    private PessoaDTOResponse pessoa;

    @Schema(description = "Dados resumidos do animal escolhido para adoção")
    private AnimalDTOResponse animal;

    @Schema(description = "Data e hora exata em que o pedido de adoção deu entrada no sistema", example = "2026-05-21T15:00:00")
    private LocalDateTime dataPedido;

    @Schema(description = "Status atual da análise do processo de adoção", example = "PENDENTE")
    private StatusProcesso statusProcesso;

    @Schema(description = "Justificativas ou observações anotadas na abertura do processo", example = "Possui espaço amplo em casa e já tem outro gatinho.")
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