package org.serratec.adocao_pets.dto.request;

import java.time.LocalDateTime;

import org.serratec.adocao_pets.domain.Animal;
import org.serratec.adocao_pets.domain.InteresseAdocao;
import org.serratec.adocao_pets.domain.InteresseAdocaoPK;
import org.serratec.adocao_pets.domain.Pessoa;
import org.serratec.adocao_pets.enumerated.StatusProcesso;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Schema(description = "Modelo de requisição para registrar o interesse de uma pessoa em adotar um pet")
public record InteresseAdocaoDTORequest(
        @Schema(hidden = true) Long id,
        @Schema(description = "ID da pessoa interessada na adoção", example = "2", requiredMode = Schema.RequiredMode.REQUIRED) Long pessoaId,
        @Schema(description = "ID do animal que a pessoa deseja adotar", example = "5", requiredMode = Schema.RequiredMode.REQUIRED) Long animalId,
        @Schema(description = "Data e hora em que o pedido de adoção foi feito", example = "2026-05-21T15:00:00", requiredMode = Schema.RequiredMode.REQUIRED) @PastOrPresent(message = "Data de pedido inválido.") LocalDateTime dataPedido,
        @Schema(description = "Status atual do processo da intenção de adoção", example = "PENDENTE", allowableValues = {
                "PENDENTE", "APROVADO", "REJEITADO",
                "CANCELADO" }, requiredMode = Schema.RequiredMode.REQUIRED) @NotNull(message = "O status de adoção deve ser informado como PENDENTE, APROVADO, REJEITADO ou CANCELADO") StatusProcesso statusProcesso,
        @Schema(description = "Notas adicionais, justificativas ou observações sobre o pedido de adoção", example = "Possui espaço amplo em casa e já tem outro gatinho.", requiredMode = Schema.RequiredMode.REQUIRED) @NotBlank(message = "O campo não pode estar em branco") String observacoes) {

    public static InteresseAdocao toInteresseAdocao(InteresseAdocaoDTORequest request,
            Pessoa pessoa,
            Animal animal) {

        InteresseAdocao interesse = new InteresseAdocao();

        interesse.setPessoa(pessoa);
        interesse.setAnimal(animal);

        interesse.setId(new InteresseAdocaoPK(pessoa.getId(), animal.getId()));
        interesse.setDataPedido(request.dataPedido());
        interesse.setStatusProcesso(request.statusProcesso());
        interesse.setObservacoes(request.observacoes());

        return interesse;
    }
}