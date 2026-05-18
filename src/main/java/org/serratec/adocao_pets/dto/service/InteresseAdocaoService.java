package org.serratec.adocao_pets.dto.service;

import org.serratec.adocao_pets.domain.Animal;
import org.serratec.adocao_pets.domain.InteresseAdocao;
import org.serratec.adocao_pets.domain.Pessoa;
import org.serratec.adocao_pets.dto.request.InteresseAdocaoRequest;

public class InteresseAdocaoService {

    public static InteresseAdocao tInteresseAdocao(InteresseAdocaoRequest request) {

        Pessoa pessoa = new Pessoa();
        pessoa.setId(request.getPessoaId());

        Animal animal = new Animal();
        animal.setId(request.getAnimalId());

        InteresseAdocao interesse = new InteresseAdocao();
        interesse.setPessoa(pessoa);
        interesse.setAnimal(animal);
        interesse.setDataPedido(request.getDataPedido());
        interesse.setStatusProcesso(request.getStatusProcesso());
        interesse.setObservacoes(request.getObservacoes());

        return interesse;
    }
}
