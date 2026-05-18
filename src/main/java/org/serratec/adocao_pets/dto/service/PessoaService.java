package org.serratec.adocao_pets.dto.service;

import org.serratec.adocao_pets.domain.Pessoa;
import org.serratec.adocao_pets.dto.request.PessoaRequest;
import org.serratec.adocao_pets.dto.response.PessoaResponse;

public class PessoaService {

    public static Pessoa toPessoa(PessoaRequest request) {

        Pessoa pessoa = new Pessoa();

        pessoa.setNome(request.getNome());
        pessoa.setEmail(request.getEmail());
        pessoa.setCpf(request.getCpf());
        pessoa.setTelefone(request.getTelefone());

        if (request.getEndereco() != null)
            pessoa.setEndereco(EnderecoService.toEndereco(request.getEndereco()));

        return pessoa;
    }

    public static PessoaResponse toPessoaResponse(Pessoa pessoa) {

        PessoaResponse response = new PessoaResponse();
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
