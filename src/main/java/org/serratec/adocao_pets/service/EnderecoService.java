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

    public static Endereco toEndereco(EnderecoDTORequest request) {

        Endereco endereco = new Endereco();

        endereco.setRua(request.getRua());
        endereco.setNumero(request.getNumero());
        endereco.setBairro(request.getBairro());
        endereco.setCidade(request.getCidade());
        endereco.setEstado(request.getEstado());
        endereco.setCep(request.getCep());
        return endereco;
    }

    public static EnderecoDTOResponse toEnderecoResponse(Endereco endereco) {

        EnderecoDTOResponse response = new EnderecoDTOResponse();
        response.setId(endereco.getId());
        response.setRua(endereco.getRua());
        response.setNumero(endereco.getNumero());
        response.setBairro(endereco.getBairro());
        response.setCidade(endereco.getCidade());
        response.setEstado(endereco.getEstado());
        response.setCep(endereco.getCep());
        return response;
    }

    public List<EnderecoDTOResponse> listarTodos() {
        List<Endereco> enderecos = enderecoRepository.findAll();
        return enderecos.stream().map(EnderecoService::toEnderecoResponse).toList();
    }

    public ResponseEntity<EnderecoDTOResponse> buscar(Long id) throws RecursoNaoEncontradoException {
        return enderecoRepository.findById(id)
                .map(EnderecoService::toEnderecoResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Endereco de ID '" + id + "' não encontrado!"));
    }

    public ResponseEntity<List<EnderecoDTOResponse>> buscarRua(String rua) throws RecursoNaoEncontradoException {
        List<EnderecoDTOResponse> endereco = enderecoRepository.findByRuaContainingIgnoreCase(rua)
                .stream()
                .map(EnderecoService::toEnderecoResponse)
                .collect(Collectors.toList());

        if (endereco.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum número contendo '" + rua + "' encontrado!");
        }
        return ResponseEntity.ok(endereco);
    }

    public ResponseEntity<List<EnderecoDTOResponse>> buscarNumero(String numero) throws RecursoNaoEncontradoException {
        List<EnderecoDTOResponse> endereco = enderecoRepository.findByNumeroContainingIgnoreCase(numero)
                .stream()
                .map(EnderecoService::toEnderecoResponse)
                .collect(Collectors.toList());

        if (endereco.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum número contendo '" + numero + "' encontrado!");
        }
        return ResponseEntity.ok(endereco);
    }

    public ResponseEntity<List<EnderecoDTOResponse>> buscarBairro(String bairro) throws RecursoNaoEncontradoException {
        List<EnderecoDTOResponse> endereco = enderecoRepository.findByBairroContainingIgnoreCase(bairro)
                .stream()
                .map(EnderecoService::toEnderecoResponse)
                .collect(Collectors.toList());

        if (endereco.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum Bairro contendo '" + bairro + "' encontrado!");
        }
        return ResponseEntity.ok(endereco);
    }

    public ResponseEntity<List<EnderecoDTOResponse>> buscarCidade(String cidade) throws RecursoNaoEncontradoException {
        List<EnderecoDTOResponse> endereco = enderecoRepository.findByCidadeContainingIgnoreCase(cidade)
                .stream()
                .map(EnderecoService::toEnderecoResponse)
                .collect(Collectors.toList());

        if (endereco.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhuma cidade contendo '" + cidade + "' encontrado!");
        }
        return ResponseEntity.ok(endereco);
    }

    public ResponseEntity<List<EnderecoDTOResponse>> buscarEstado(String estado) throws RecursoNaoEncontradoException {
        List<EnderecoDTOResponse> endereco = enderecoRepository.findByEstadoContainingIgnoreCase(estado)
                .stream()
                .map(EnderecoService::toEnderecoResponse)
                .collect(Collectors.toList());

        if (endereco.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum estado contendo '" + estado + "' encontrado!");
        }
        return ResponseEntity.ok(endereco);
    }

    public ResponseEntity<List<EnderecoDTOResponse>> buscarCep(String cep) throws RecursoNaoEncontradoException {
        List<EnderecoDTOResponse> endereco = enderecoRepository.findByCepContainingIgnoreCase(cep)
                .stream()
                .map(EnderecoService::toEnderecoResponse)
                .collect(Collectors.toList());

        if (endereco.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum cep contendo '" + cep + "' encontrado!");
        }
        return ResponseEntity.ok(endereco);
    }

    // Métodos para o POST
    public EnderecoDTOResponse salvar(EnderecoDTORequest request) {
        Endereco endereco = toEndereco(request);
        Endereco salvo = enderecoRepository.save(endereco);
        return toEnderecoResponse(salvo);
    }

    public List<EnderecoDTOResponse> salvarList(List<EnderecoDTORequest> request) {
        List<Endereco> enderecos = request.stream().map(EnderecoService::toEndereco).toList();
        List<Endereco> salvo = enderecoRepository.saveAll(enderecos);

        return salvo.stream().map(EnderecoService::toEnderecoResponse).toList();
    }

    // Métodos para o PUT
    public ResponseEntity<EnderecoDTOResponse> atualizar(Long id, EnderecoDTORequest request) {
        return enderecoRepository.findById(id).map(existe -> {

            existe.setRua(request.getRua());
            existe.setNumero(request.getNumero());
            existe.setBairro(request.getBairro());
            existe.setCidade(request.getCidade());
            existe.setEstado(request.getEstado());
            existe.setCep(request.getCep());

            Endereco salvo = enderecoRepository.save(existe);
            EnderecoDTOResponse response = toEnderecoResponse(salvo);

            return ResponseEntity.ok(response);

        }).orElse(ResponseEntity.notFound().build());
    }

    // Métodos DELETE
    public boolean excluir(Long id) {
        return enderecoRepository.findById(id).map(endereco -> {
            enderecoRepository.delete(endereco);
            return true;
        }).orElse(false);
    }
}
