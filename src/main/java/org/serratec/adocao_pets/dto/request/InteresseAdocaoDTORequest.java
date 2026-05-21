package org.serratec.adocao_pets.dto.request;

import java.time.LocalDateTime;

import org.serratec.adocao_pets.domain.Animal;
import org.serratec.adocao_pets.domain.InteresseAdocao;
import org.serratec.adocao_pets.domain.InteresseAdocaoPK;
import org.serratec.adocao_pets.domain.Pessoa;
import org.serratec.adocao_pets.enumerated.StatusProcesso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record InteresseAdocaoDTORequest(Long id, Long pessoaId,
        Long animalId,
        @PastOrPresent(message = "Data de pedido inválido.") LocalDateTime dataPedido,
        @NotNull(message = "O status de adoção deve ser informado como PENDENTE, APROVADO, REJEITADO ou CANCELADO") StatusProcesso statusProcesso,
        @NotBlank(message = "O campo não pode estar em branco") String observacoes) {

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