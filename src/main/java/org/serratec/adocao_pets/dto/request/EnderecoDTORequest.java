package org.serratec.adocao_pets.dto.request;

import org.serratec.adocao_pets.domain.Endereco;

public record EnderecoDTORequest(
        Long id,
        String rua,
        String numero,
        String bairro,
        String cidade,
        String estado,
        String cep) {

    public static Endereco toEndereco(EnderecoDTORequest request) {

        Endereco endereco = new Endereco();

        endereco.setRua(request.rua());
        endereco.setNumero(request.numero());
        endereco.setBairro(request.bairro());
        endereco.setCidade(request.cidade());
        endereco.setEstado(request.estado());
        endereco.setCep(request.cep());
        return endereco;
    }

}
