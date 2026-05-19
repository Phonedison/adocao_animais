package org.serratec.adocao_pets.service;

import org.serratec.adocao_pets.domain.Endereco;
import org.serratec.adocao_pets.dto.request.EnderecoDTORequest;
import org.serratec.adocao_pets.dto.response.EnderecoDTOResponse;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    public static Endereco toEndereco(EnderecoDTORequest request) {

        Endereco endereco = new Endereco();

        endereco.setRua(request.getRua());
        endereco.setNumero(request.getNumero());
        endereco.setBairro(request.getBairro());
        endereco.setEstado(request.getEstado());
        endereco.setCep(request.getCep());
        return endereco;

        /*
         * POSTMAPPING
         * no RequestBody EnderecoDTORequest request
         * no controller deve chamar Endereco endereco =
         * EnderecoService.toEndereco(request);
         * 
         */
    }

    public static EnderecoDTOResponse toEnderecoResponse(Endereco endereco) {

        EnderecoDTOResponse response = new EnderecoDTOResponse();
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
