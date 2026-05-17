package org.serratec.adocao_pets.domain;

import org.serratec.adocao_pets.enumerated.TipoPessoa;

public class Pessoa {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;

    // referencia a classe Endereco
    private Endereco endereco;

    // referencia a ENUM tipoPessoa
    private TipoPessoa tipoPessoa;

}
