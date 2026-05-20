package org.serratec.adocao_pets.dto.request;

public record EnderecoDTORequest(
                Long id,
                String rua,
                String numero,
                String bairro,
                String cidade,
                String estado,
                String cep) {
}
