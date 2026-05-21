package org.serratec.adocao_pets.dto.request;

import java.time.LocalDateTime;

import org.serratec.adocao_pets.domain.Animal;
import org.serratec.adocao_pets.domain.InteresseAdocao;
import org.serratec.adocao_pets.domain.InteresseAdocaoPK;
import org.serratec.adocao_pets.domain.Pessoa;
import org.serratec.adocao_pets.enumerated.StatusProcesso;

public record InteresseAdocaoDTORequest(Long id, Long pessoaId,
        Long animalId,
        LocalDateTime dataPedido,
        StatusProcesso statusProcesso,
        String observacoes) {

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