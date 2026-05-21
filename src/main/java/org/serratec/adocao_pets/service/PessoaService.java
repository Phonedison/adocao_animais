package org.serratec.adocao_pets.service;

import java.util.List;

import org.serratec.adocao_pets.domain.Endereco;
import org.serratec.adocao_pets.domain.Pessoa;
import org.serratec.adocao_pets.dto.request.EnderecoDTORequest;
import org.serratec.adocao_pets.dto.request.PessoaDTORequest;
import org.serratec.adocao_pets.dto.response.PessoaDTOResponse;
import org.serratec.adocao_pets.exception.RecursoNaoEncontradoException;
import org.serratec.adocao_pets.repository.EnderecoRepository;
import org.serratec.adocao_pets.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Transactional
    public List<PessoaDTOResponse> listarTodas() {
        return repository.findAll()
                .stream()
                .map(pessoa -> PessoaDTOResponse.toPessoaResponse(
                        pessoa))
                .toList();
    }

    @Transactional
    public PessoaDTOResponse buscarPorId(Long id) {
        return repository.findById(id)
                .map(pessoa -> PessoaDTOResponse.toPessoaResponse(pessoa))
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pessoa com ID '" + id + "' não encontrada!"));
    }

    @Transactional
    public PessoaDTOResponse salvar(PessoaDTORequest request) {
        Pessoa pessoa = request.toPessoa();

        if (request.endereco() != null && request.endereco().id() != null) {
            Long enderecoId = request.endereco().id();

            Endereco endereco = enderecoRepository.findById(enderecoId)
                    .orElseThrow(() -> new RecursoNaoEncontradoException(
                            "Endereço com ID '" + enderecoId
                                    + "' não existe! É necessário cadastrar um novo endereço primeiro."));

            pessoa.setEndereco(endereco);
        }

        return PessoaDTOResponse.toPessoaResponse(repository.save(pessoa));
    }

    @Transactional
    public List<PessoaDTOResponse> salvarList(List<PessoaDTORequest> requests) {
        List<Pessoa> pessoas = requests.stream()
                .map(request -> {
                    Pessoa pessoa = request.toPessoa();
                    if (request.endereco() != null && request.endereco().id() != null) {
                        Long enderecoId = request.endereco().id();

                        Endereco endereco = enderecoRepository.findById(enderecoId)
                                .orElseThrow(() -> new RecursoNaoEncontradoException(
                                        "Endereço com ID '" + enderecoId
                                                + "' não existe! É necessário cadastrar um novo endereço primeiro."));

                        pessoa.setEndereco(endereco);
                    }
                    return pessoa;
                })
                .toList();

        return repository.saveAll(pessoas).stream()
                .map(pessoa -> PessoaDTOResponse.toPessoaResponse(pessoa))
                .toList();
    }

    @Transactional
    public PessoaDTOResponse atualizar(Long id, PessoaDTORequest request) {
        Pessoa pessoaExistente = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pessoa com ID '" + id + "' não encontrada!"));

        pessoaExistente.setNome(request.nome());
        pessoaExistente.setEmail(request.email());
        pessoaExistente.setCpf(request.cpf());
        pessoaExistente.setTelefone(request.telefone());

        if (request.endereco() != null) {
            EnderecoDTORequest encRequest = request.endereco();

            if (encRequest.id() != null) {
                Endereco novoEndereco = enderecoRepository.findById(encRequest.id())
                        .orElseThrow(() -> new RecursoNaoEncontradoException(
                                "Endereço com ID " + encRequest.id() + " não encontrado!"));
                pessoaExistente.setEndereco(novoEndereco);
            } else if (pessoaExistente.getEndereco() != null) {
                atualizarCampos(pessoaExistente.getEndereco(), encRequest);
            }
        }

        return PessoaDTOResponse.toPessoaResponse(repository.save(pessoaExistente));
    }

    private void atualizarCampos(Endereco destino, EnderecoDTORequest origem) {
        if (origem.rua() != null)
            destino.setRua(origem.rua());
        if (origem.numero() != null)
            destino.setNumero(origem.numero());
        if (origem.bairro() != null)
            destino.setBairro(origem.bairro());
        if (origem.cidade() != null)
            destino.setCidade(origem.cidade());
        if (origem.estado() != null)
            destino.setEstado(origem.estado());
        if (origem.cep() != null)
            destino.setCep(origem.cep());
    }

    public void deletar(Long id) {
        repository.findById(id).ifPresentOrElse(
                repository::delete,
                () -> {
                    throw new RecursoNaoEncontradoException("Pessoa com o ID " + id + " não foi encontrado.");
                });
    }
}
