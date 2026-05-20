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

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public static Pessoa toPessoa(PessoaDTORequest request, Map<Long, Endereco> enderecoMap) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(request.nome());
        pessoa.setEmail(request.email());
        pessoa.setCpf(request.cpf());
        pessoa.setTelefone(request.telefone());

        if (request.endereco() != null) {
            Endereco endereco = enderecoMap.get(request.endereco().id());
            if (endereco != null) {
                pessoa.setEndereco(endereco);
            }
        }

        return pessoa;
    }

    public static PessoaDTOResponse toPessoaResponse(Pessoa pessoa) {
        PessoaDTOResponse response = new PessoaDTOResponse();
        response.setId(pessoa.getId());
        response.setNome(pessoa.getNome());
        response.setEmail(pessoa.getEmail());
        response.setCpf(pessoa.getCpf());
        response.setTelefone(pessoa.getTelefone());

        if (pessoa.getEndereco() != null) {
            response.setEndereco(EnderecoService.toEnderecoResponse(pessoa.getEndereco()));
        }

        return response;
    }

    public List<PessoaDTOResponse> listarTodas() {
        return repository.findAll().stream()
                .map(PessoaService::toPessoaResponse)
                .toList();
    }

    public PessoaDTOResponse buscarPorId(Long id) throws RecursoNaoEncontradoException {
        return repository.findById(id)
                .map(PessoaService::toPessoaResponse)
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
        return repository.findById(id).map(pessoaExistente -> {
            pessoaExistente.setNome(request.nome());
            pessoaExistente.setEmail(request.email());
            pessoaExistente.setCpf(request.cpf());
            pessoaExistente.setTelefone(request.telefone());

            if (request.endereco() != null) {
                EnderecoDTORequest enderecoRequest = request.endereco();

                if (pessoaExistente.getEndereco() != null) {

                    Endereco enderecoExistente = pessoaExistente.getEndereco();
                    enderecoExistente.setRua(enderecoRequest.rua());
                    enderecoExistente.setNumero(enderecoRequest.numero());
                    enderecoExistente.setBairro(enderecoRequest.bairro());
                    enderecoExistente.setCidade(enderecoRequest.cidade());
                    enderecoExistente.setEstado(enderecoRequest.estado());
                    enderecoExistente.setCep(enderecoRequest.cep());

                } else if (enderecoRequest.id() != null) {
                    enderecoRepository.findById(enderecoRequest.id())
                            .ifPresent(pessoaExistente::setEndereco);
                }
            }

            Pessoa atualizada = repository.save(pessoaExistente);
            return toPessoaResponse(atualizada);
        }).orElseThrow(() -> new RecursoNaoEncontradoException("Pessoa com ID '" + id + "' não encontrada!"));

    }

    public void deletar(Long id) {
        repository.findById(id).ifPresentOrElse(
                repository::delete,
                () -> {
                    throw new RecursoNaoEncontradoException("Pessoa com o ID " + id + " não foi encontrado.");
                });
    }

}
