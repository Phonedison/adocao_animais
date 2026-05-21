package org.serratec.adocao_pets.dto.request;

import org.serratec.adocao_pets.domain.Pessoa;

public record PessoaDTORequest(String nome, String email, String cpf, String telefone, EnderecoDTORequest endereco) {

        public Pessoa toPessoa() {
                Pessoa pessoa = new Pessoa();
                pessoa.setNome(this.nome());
                pessoa.setEmail(this.email());
                pessoa.setCpf(this.cpf());
                pessoa.setTelefone(this.telefone());
                return pessoa;
        }
}