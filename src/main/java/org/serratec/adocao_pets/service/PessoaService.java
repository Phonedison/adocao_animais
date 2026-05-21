package org.serratec.adocao_pets.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
        return repository.findAll().stream()
                .map(PessoaDTOResponse::toPessoaResponse)
                .toList();
    }

    @Transactional
    public PessoaDTOResponse buscarPorId(Long id) {
        return repository.findById(id)
                .map(PessoaDTOResponse::toPessoaResponse)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pessoa com ID '" + id + "' não encontrada!"));
    }

    public PessoaDTOResponse salvar(PessoaDTORequest request) {
        Endereco endereco = null;

        if (request.endereco() != null && request.endereco().id() != null) {
            endereco = enderecoRepository.findById(request.endereco().id())
                    .orElseThrow(() -> new RuntimeException("Endereço não encontrado!"));
        }

        Map<Long, Endereco> mapeamento = java.util.Collections.emptyMap();
        if (endereco != null) {
            mapeamento = java.util.Collections.singletonMap(endereco.getId(), endereco);
        }

        Pessoa pessoa = toPessoa(request, mapeamento);
        Pessoa salva = repository.save(pessoa);
        return toPessoaResponse(salva);
    }

    public List<PessoaDTOResponse> salvarList(List<PessoaDTORequest> request) {

        List<Long> enderecoIds = request.stream()
                .map(p -> p.endereco() != null ? p.endereco().id() : null)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        List<Endereco> enderecosSalvos = enderecoRepository.findAllById(enderecoIds);

        Map<Long, Endereco> enderecoMap = enderecosSalvos
                .stream()
                .collect(Collectors.toMap(Endereco::getId, e -> e));

        List<Pessoa> pessoas = request.stream()
                .map(dto -> toPessoa(dto, enderecoMap))
                .toList();

        List<Pessoa> salvo = repository.saveAll(pessoas);
        return salvo.stream().map(PessoaService::toPessoaResponse).toList();
    }

    public PessoaDTOResponse atualizar(Long id, PessoaDTORequest request) throws RecursoNaoEncontradoException {
        Pessoa pessoaExistente = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pessoa com ID '" + id + "' não encontrada!"));

        pessoaExistente.setNome(request.nome());
        pessoaExistente.setEmail(request.email());
        pessoaExistente.setCpf(request.cpf());
        pessoaExistente.setTelefone(request.telefone());

        if (request.endereco() != null) {
            EnderecoDTORequest encRequest = request.endereco();

            // 🎯 Se foi enviado o ID do endereço, buscamos ele e vinculamos diretamente à
            // pessoa
            if (encRequest.id() != null) {
                Endereco novoEndereco = enderecoRepository.findById(encRequest.id())
                        .orElseThrow(
                                () -> new RuntimeException("Endereço com ID " + encRequest.id() + " não encontrado!"));
                pessoaExistente.setEndereco(novoEndereco);
            }
            // Se não foi enviado ID, mas a pessoa já tem um endereço associado, atualizamos
            // os campos de texto dele
            else if (pessoaExistente.getEndereco() != null) {
                atualizarCampos(pessoaExistente.getEndereco(), encRequest);
            }
        }

        return toPessoaResponse(repository.save(pessoaExistente));
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
