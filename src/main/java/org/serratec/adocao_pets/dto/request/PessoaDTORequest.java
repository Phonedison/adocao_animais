package org.serratec.adocao_pets.dto.request;

public record PessoaDTORequest(String nome,
        String email,
        String telefone,
        String cpf,
        EnderecoDTORequest endereco) {
}