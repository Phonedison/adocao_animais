package org.serratec.adocao_pets.controller;

import java.net.URI;
import java.util.List;

import org.serratec.adocao_pets.dto.request.EnderecoDTORequest;
import org.serratec.adocao_pets.dto.response.EnderecoDTOResponse;
import org.serratec.adocao_pets.exception.RecursoNaoEncontradoException;
import org.serratec.adocao_pets.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService service;

    @GetMapping
    public List<EnderecoDTOResponse> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTOResponse> buscar(@PathVariable Long id) throws RecursoNaoEncontradoException {
        return service.buscar(id);
    }

    @GetMapping("/bairro/{bairro}")
    public ResponseEntity<List<EnderecoDTOResponse>> buscarBairro(
            @Valid @PathVariable String bairro)
            throws RecursoNaoEncontradoException {
        return service.buscarBairro(bairro);
    }

    @GetMapping("/numero/{numero}")
    public ResponseEntity<List<EnderecoDTOResponse>> buscarNumero(
            @Valid @PathVariable String numero)
            throws RecursoNaoEncontradoException {
        return service.buscarNumero(numero);
    }

    @GetMapping("/cidade/{cidade}")
    public ResponseEntity<List<EnderecoDTOResponse>> buscarCidade(
            @Valid @PathVariable String cidade)
            throws RecursoNaoEncontradoException {
        return service.buscarCidade(cidade);
    }

    @GetMapping("/rua/{rua}")
    public ResponseEntity<List<EnderecoDTOResponse>> buscarRua(
            @Valid @PathVariable String rua)
            throws RecursoNaoEncontradoException {
        return service.buscarRua(rua);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<EnderecoDTOResponse>> buscarEstado(
            @Valid @PathVariable String estado)
            throws RecursoNaoEncontradoException {
        return service.buscarEstado(estado);
    }

    @GetMapping("/cep/{cep}")
    public ResponseEntity<List<EnderecoDTOResponse>> buscarCep(
            @Valid @PathVariable String cep)
            throws RecursoNaoEncontradoException {
        return service.buscarCep(cep);
    }

    @PostMapping
    public ResponseEntity<EnderecoDTOResponse> salvar(@Valid @RequestBody EnderecoDTORequest request) {
        EnderecoDTOResponse enderecoResponse = service.salvar(request);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(enderecoResponse.getId())
                .toUri();

        return ResponseEntity.created(uri).body(enderecoResponse);
    }

    @PostMapping("/salvar-lista")
    public ResponseEntity<List<EnderecoDTOResponse>> salvarVarios(
            @Valid @RequestBody List<EnderecoDTORequest> request) {
        List<EnderecoDTOResponse> enderecoResponse = service.salvarList(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoResponse);
    }

    // Métodos DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<EnderecoDTOResponse> deletar(@PathVariable Long id) throws RecursoNaoEncontradoException {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
