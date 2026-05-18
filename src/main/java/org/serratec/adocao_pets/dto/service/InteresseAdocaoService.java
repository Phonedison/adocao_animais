package org.serratec.adocao_pets.dto.service;

import org.serratec.adocao_pets.domain.Animal;
import org.serratec.adocao_pets.domain.InteresseAdocao;
import org.serratec.adocao_pets.domain.Pessoa;
import org.serratec.adocao_pets.dto.request.InteresseAdocaoRequest;
import org.serratec.adocao_pets.dto.response.AnimalResponse;
import org.serratec.adocao_pets.dto.response.InteresseAdocaoResponse;
import org.serratec.adocao_pets.dto.response.PessoaResponse;

public class InteresseAdocaoService {

    public static InteresseAdocao toInteresseAdocao(InteresseAdocaoRequest request) {

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

    public static InteresseAdocaoResponse toInteresseAdocaoResponse(InteresseAdocao interesse) {

        InteresseAdocaoResponse response = new InteresseAdocaoResponse();
        response.setId(interesse.getId());

        if (interesse.getPessoa() != null) {
            PessoaResponse pessoaResponse = new PessoaResponse();
            pessoaResponse.setId(interesse.getPessoa().getId());
            // ... teste só com id
            response.setPessoa(pessoaResponse);
        }

        if (interesse.getAnimal() != null) {
            AnimalResponse animalResponse = new AnimalResponse();
            animalResponse.setId(interesse.getAnimal().getId());

            response.setAnimal(animalResponse);
        }

        response.setDataPedido(interesse.getDataPedido());
        response.setStatusProcesso(interesse.getStatusProcesso());
        response.setObservacoes(interesse.getObservacoes());

        return response;
    }
}
