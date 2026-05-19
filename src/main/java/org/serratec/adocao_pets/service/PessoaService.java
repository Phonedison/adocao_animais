package org.serratec.adocao_pets.service;

import org.serratec.adocao_pets.domain.Pessoa;
import org.serratec.adocao_pets.dto.request.PessoaDTORequest;
import org.serratec.adocao_pets.dto.response.PessoaDTOResponse;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    public static Pessoa toPessoa(PessoaDTORequest request) {

        Pessoa pessoa = new Pessoa();

        pessoa.setNome(request.getNome());
        pessoa.setEmail(request.getEmail());
        pessoa.setCpf(request.getCpf());
        pessoa.setTelefone(request.getTelefone());

        if (request.getEndereco() != null)
            pessoa.setEndereco(EnderecoService.toEndereco(request.getEndereco()));

        return pessoa;
    }

    public static PessoaDTOResponse toPessoaResponse(Pessoa pessoa) {

        PessoaDTOResponse response = new PessoaDTOResponse();
        response.setId(pessoa.getId());
        response.setNome(pessoa.getNome());
        response.setEmail(pessoa.getEmail());
        response.setCpf(pessoa.getCpf());
        response.setTelefone(pessoa.getTelefone());

        if (pessoa.getEndereco() != null)
            response.setEndereco(EnderecoService.toEnderecoResponse(pessoa.getEndereco()));

        return response;
    }

}
