package org.serratec.adocao_pets.service;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.adocao_pets.domain.Endereco;
import org.serratec.adocao_pets.dto.request.EnderecoDTORequest;
import org.serratec.adocao_pets.dto.response.EnderecoDTOResponse;
import org.serratec.adocao_pets.exception.RecursoNaoEncontradoException;
import org.serratec.adocao_pets.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public List<EnderecoDTOResponse> listarTodos() {
        List<Endereco> enderecos = enderecoRepository.findAll();
        return enderecos.stream().map(EnderecoDTOResponse::toEnderecoResponse).toList();
    }

    public ResponseEntity<EnderecoDTOResponse> buscar(Long id) throws RecursoNaoEncontradoException {
        return enderecoRepository.findById(id)
                .map(EnderecoDTOResponse::toEnderecoResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Endereco de ID '" + id + "' não encontrado!"));
    }

    public ResponseEntity<List<EnderecoDTOResponse>> buscarRua(String rua) throws RecursoNaoEncontradoException {
        List<EnderecoDTOResponse> endereco = enderecoRepository.findByRuaContainingIgnoreCase(rua)
                .stream()
                .map(EnderecoDTOResponse::toEnderecoResponse)
                .collect(Collectors.toList());

        if (endereco.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum número contendo '" + rua + "' encontrado!");
        }
        return ResponseEntity.ok(endereco);
    }

    public ResponseEntity<List<EnderecoDTOResponse>> buscarNumero(String numero) throws RecursoNaoEncontradoException {
        List<EnderecoDTOResponse> endereco = enderecoRepository.findByNumeroContainingIgnoreCase(numero)
                .stream()
                .map(EnderecoDTOResponse::toEnderecoResponse)
                .collect(Collectors.toList());

        if (endereco.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum número contendo '" + numero + "' encontrado!");
        }
        return ResponseEntity.ok(endereco);
    }

    public ResponseEntity<List<EnderecoDTOResponse>> buscarBairro(String bairro) throws RecursoNaoEncontradoException {
        List<EnderecoDTOResponse> endereco = enderecoRepository.findByBairroContainingIgnoreCase(bairro)
                .stream()
                .map(EnderecoDTOResponse::toEnderecoResponse)
                .collect(Collectors.toList());

        if (endereco.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum Bairro contendo '" + bairro + "' encontrado!");
        }
        return ResponseEntity.ok(endereco);
    }

    public ResponseEntity<List<EnderecoDTOResponse>> buscarCidade(String cidade) throws RecursoNaoEncontradoException {
        List<EnderecoDTOResponse> endereco = enderecoRepository.findByCidadeContainingIgnoreCase(cidade)
                .stream()
                .map(EnderecoDTOResponse::toEnderecoResponse)
                .collect(Collectors.toList());

        if (endereco.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhuma cidade contendo '" + cidade + "' encontrado!");
        }
        return ResponseEntity.ok(endereco);
    }

    public ResponseEntity<List<EnderecoDTOResponse>> buscarEstado(String estado) throws RecursoNaoEncontradoException {
        List<EnderecoDTOResponse> endereco = enderecoRepository.findByEstadoContainingIgnoreCase(estado)
                .stream()
                .map(EnderecoDTOResponse::toEnderecoResponse)
                .collect(Collectors.toList());

        if (endereco.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum estado contendo '" + estado + "' encontrado!");
        }
        return ResponseEntity.ok(endereco);
    }

    public ResponseEntity<List<EnderecoDTOResponse>> buscarCep(String cep) throws RecursoNaoEncontradoException {
        List<EnderecoDTOResponse> endereco = enderecoRepository.findByCepContainingIgnoreCase(cep)
                .stream()
                .map(EnderecoDTOResponse::toEnderecoResponse)
                .collect(Collectors.toList());

        if (endereco.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum cep contendo '" + cep + "' encontrado!");
        }
        return ResponseEntity.ok(endereco);
    }

    // Métodos para o POST
    public EnderecoDTOResponse salvar(EnderecoDTORequest request) {
        Endereco endereco = EnderecoDTORequest.toEndereco(request);
        Endereco salvo = enderecoRepository.save(endereco);
        return EnderecoDTOResponse.toEnderecoResponse(salvo);
    }

    public List<EnderecoDTOResponse> salvarList(List<EnderecoDTORequest> request) {
        List<Endereco> enderecos = request.stream().map(EnderecoDTORequest::toEndereco).toList();
        List<Endereco> salvo = enderecoRepository.saveAll(enderecos);

        return salvo.stream().map(EnderecoDTOResponse::toEnderecoResponse).toList();
    }

    // Métodos para o PUT
    public ResponseEntity<EnderecoDTOResponse> atualizar(Long id, EnderecoDTORequest request) {
        return enderecoRepository.findById(id).map(existe -> {

            existe.setRua(request.rua());
            existe.setNumero(request.numero());
            existe.setBairro(request.bairro());
            existe.setCidade(request.cidade());
            existe.setEstado(request.estado());
            existe.setCep(request.cep());

            Endereco salvo = enderecoRepository.save(existe);
            EnderecoDTOResponse response = EnderecoDTOResponse.toEnderecoResponse(salvo);

            return ResponseEntity.ok(response);

        }).orElse(ResponseEntity.notFound().build());
    }

    // Métodos DELETE
    public void excluir(Long id) {
        enderecoRepository.findById(id).ifPresentOrElse(
                enderecoRepository::delete,
                () -> {
                    throw new RecursoNaoEncontradoException("Endereço com o ID " + id + " não foi encontrado.");
                });
    }
}
