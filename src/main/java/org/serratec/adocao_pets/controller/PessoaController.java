package org.serratec.adocao_pets.controller;

import java.util.List;

import org.serratec.adocao_pets.dto.request.PessoaDTORequest;
import org.serratec.adocao_pets.dto.response.PessoaDTOResponse;
import org.serratec.adocao_pets.exception.RecursoNaoEncontradoException;
import org.serratec.adocao_pets.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService service;

    @GetMapping
    public ResponseEntity<List<PessoaDTOResponse>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTOResponse> buscarPorId(@PathVariable Long id) throws RecursoNaoEncontradoException {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<PessoaDTOResponse> cadastrar(@Valid @RequestBody PessoaDTORequest request) {
        PessoaDTOResponse response = service.salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/salvar-lista")
    public ResponseEntity<List<PessoaDTOResponse>> salvarVarios(@Valid @RequestBody List<PessoaDTORequest> request) {
        List<PessoaDTOResponse> pessoaResponse = service.salvarList(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaDTOResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PessoaDTORequest request) throws RecursoNaoEncontradoException {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws RecursoNaoEncontradoException {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
