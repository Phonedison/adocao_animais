package org.serratec.adocao_pets.service;

import org.serratec.adocao_pets.domain.Animal;
import org.serratec.adocao_pets.domain.InteresseAdocao;
import org.serratec.adocao_pets.domain.InteresseAdocaoPK;
import org.serratec.adocao_pets.domain.Pessoa;
import org.serratec.adocao_pets.dto.request.InteresseAdocaoDTORequest;
import org.serratec.adocao_pets.dto.response.AnimalDTOResponse;
import org.serratec.adocao_pets.dto.response.InteresseAdocaoDTOResponse;
import org.serratec.adocao_pets.dto.response.PessoaDTOResponse;
import org.springframework.stereotype.Service;

@Service
public class InteresseAdocaoService {

    public static InteresseAdocao toInteresseAdocao(InteresseAdocaoDTORequest request) {

        Pessoa pessoa = new Pessoa();
        pessoa.setId(request.getPessoaId());

        Animal animal = new Animal();
        animal.setId(request.getAnimalId());

        InteresseAdocao interesse = new InteresseAdocao();

        InteresseAdocaoPK pk = new InteresseAdocaoPK(request.getPessoaId(), request.getAnimalId());
        interesse.setId(pk);

        interesse.setPessoa(pessoa);
        interesse.setAnimal(animal);
        interesse.setDataPedido(request.getDataPedido());
        interesse.setStatusProcesso(request.getStatusProcesso());
        interesse.setObservacoes(request.getObservacoes());

        return interesse;
    }

    public static InteresseAdocaoDTOResponse toInteresseAdocaoResponse(InteresseAdocao interesse) {
        InteresseAdocaoDTOResponse response = new InteresseAdocaoDTOResponse();

        response.setId(interesse.getId());

        if (interesse.getPessoa() != null) {
            PessoaDTOResponse pessoaResponse = new PessoaDTOResponse();
            pessoaResponse.setId(interesse.getPessoa().getId());
            response.setPessoa(pessoaResponse);
        }

        if (interesse.getAnimal() != null) {
            AnimalDTOResponse animalResponse = new AnimalDTOResponse();
            animalResponse.setId(interesse.getAnimal().getId());
            response.setAnimal(animalResponse);
        }

        response.setDataPedido(interesse.getDataPedido());
        response.setStatusProcesso(interesse.getStatusProcesso());
        response.setObservacoes(interesse.getObservacoes());

        return response;
    }
}
