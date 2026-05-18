package org.serratec.adocao_pets.dto.service;

import org.serratec.adocao_pets.domain.Endereco;
import org.serratec.adocao_pets.dto.request.EnderecoRequest;
import org.serratec.adocao_pets.dto.response.EnderecoResponse;

public class EnderecoService {

    public static Endereco toEndereco(EnderecoRequest request) {

        Endereco endereco = new Endereco();
        endereco.setRua(request.getRua());
        endereco.setNumero(request.getNumero());
        endereco.setBairro(request.getBairro());
        endereco.setEstado(request.getEstado());
        endereco.setCep(request.getCep());
        return endereco;

        /*
         * POSTMAPPING
         * no RequestBody EnderecoRequest request
         * no controller deve chamar Endereco endereco =
         * EnderecoService.toEndereco(request);
         * 
         */
    }

    public static EnderecoResponse toEnderecoResponse(Endereco endereco) {

        EnderecoResponse response = new EnderecoResponse();
        response.setId(endereco.getId());
        response.setRua(endereco.getRua());
        response.setNumero(endereco.getNumero());
        response.setBairro(endereco.getBairro());
        response.setEstado(endereco.getEstado());
        response.setCep(endereco.getCep());
        return response;

        /*
         * POSTMAPPING
         * Endereco enderecoSalvo = EnderecoRepository.save(endereco)
         * EnderecoService service = EnderecoService.toEnderecoResponse(endereco)
         * return ResponseEntity.status(Http.CREATED).body(enderecoResponse)
         */

    }

}
