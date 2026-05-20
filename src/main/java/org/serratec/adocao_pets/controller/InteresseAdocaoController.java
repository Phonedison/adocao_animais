package org.serratec.adocao_pets.controller;

import java.util.List;

import org.serratec.adocao_pets.dto.request.InteresseAdocaoDTORequest;
import org.serratec.adocao_pets.dto.response.InteresseAdocaoDTOResponse;
import org.serratec.adocao_pets.exception.RecursoNaoEncontradoException;
import org.serratec.adocao_pets.service.InteresseAdocaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/interesses-adocoes")
public class InteresseAdocaoController {

    @Autowired
    private InteresseAdocaoService service;

    // GETs
    @GetMapping
    @JsonIgnoreProperties({ "endereco" })
    public ResponseEntity<List<InteresseAdocaoDTOResponse>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/pessoa/{pessoaId}/animal/{animalId}")
    public ResponseEntity<InteresseAdocaoDTOResponse> buscar(
            @PathVariable Long pessoaId,
            @PathVariable Long animalId) throws RecursoNaoEncontradoException {
        return ResponseEntity.ok(service.buscar(pessoaId, animalId));
    }

    // Post
    @PostMapping("/salvar")
    public ResponseEntity<InteresseAdocaoDTOResponse> cadastrar(@Valid @RequestBody InteresseAdocaoDTORequest request) {
        InteresseAdocaoDTOResponse response = service.salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/salvar-lista")
    public ResponseEntity<List<InteresseAdocaoDTOResponse>> salvarVarios(
            @Valid @RequestBody List<InteresseAdocaoDTORequest> request) {
        List<InteresseAdocaoDTOResponse> interesseResponse = service.salvarList(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(interesseResponse);
    }

    // Puts Atualizar
    @PutMapping("/pessoa/{pessoaId}/animal/{animalId}")
    public ResponseEntity<InteresseAdocaoDTOResponse> atualizar(
            @PathVariable Long pessoaId,
            @PathVariable Long animalId,
            @Valid @RequestBody InteresseAdocaoDTORequest request) throws RecursoNaoEncontradoException {
        return ResponseEntity.ok(service.atualizar(pessoaId, animalId, request));
    }

}
