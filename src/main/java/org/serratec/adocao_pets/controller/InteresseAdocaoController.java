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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Interesses de Adoção", description = "Endpoints para gerenciamento das intenções e pedidos de adoção")
@RestController
@RequestMapping("/interesses-adocoes")
public class InteresseAdocaoController {

    @Autowired
    private InteresseAdocaoService service;

    @Operation(summary = "Listar todos os interesses", description = "Retorna uma lista contendo todas as intenções de adoção registradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @GetMapping
    @JsonIgnoreProperties({ "endereco" })
    public ResponseEntity<List<InteresseAdocaoDTOResponse>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @Operation(summary = "Buscar interesse específico", description = "Retorna os detalhes de uma intenção de adoção com base no ID da pessoa e no ID do animal desejado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro de interesse encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Interesse de adoção não encontrado para os IDs informados")
    })
    @GetMapping("/pessoa/{pessoaId}/animal/{animalId}")
    public ResponseEntity<InteresseAdocaoDTOResponse> buscar(
            @Parameter(description = "ID da pessoa interessada", example = "3") @PathVariable Long pessoaId,
            @Parameter(description = "ID do animal", example = "7") @PathVariable Long animalId)
            throws RecursoNaoEncontradoException {
        return ResponseEntity.ok(service.buscar(pessoaId, animalId));
    }

    // Post
    @Operation(summary = "Registrar interesse de adoção", description = "Cadastra uma nova intenção de adoção no sistema vinculando uma pessoa a um pet.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Interesse de adoção registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Pessoa ou Animal informado não existe no sistema"),
            @ApiResponse(responseCode = "409", description = "Conflito: Essa pessoa já registrou interesse por esse mesmo animal")
    })
    @PostMapping("/salvar")
    public ResponseEntity<InteresseAdocaoDTOResponse> cadastrar(@Valid @RequestBody InteresseAdocaoDTORequest request) {
        InteresseAdocaoDTOResponse response = service.salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Registrar múltiplos interesses", description = "Recebe uma lista de pedidos de adoção e realiza o cadastro em lote.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lista de interesses registrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de um ou mais itens da lista inválidos")
    })
    @PostMapping("/salvar-lista")
    public ResponseEntity<List<InteresseAdocaoDTOResponse>> salvarVarios(
            @Valid @RequestBody List<InteresseAdocaoDTORequest> request) {
        List<InteresseAdocaoDTOResponse> interesseResponse = service.salvarList(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(interesseResponse);
    }

    @Operation(summary = "Atualizar interesse de adoção", description = "Altera os dados de um interesse existente (como status ou observações) com base nas chaves de pessoa e animal.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Interesse atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados informados inválidos"),
            @ApiResponse(responseCode = "404", description = "Interesse de adoção original não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito na consistência de dados ao atualizar")
    })
    @PutMapping("/pessoa/{pessoaId}/animal/{animalId}")
    public ResponseEntity<InteresseAdocaoDTOResponse> atualizar(
            @Parameter(description = "ID da pessoa vinculada ao interesse", example = "3") @PathVariable Long pessoaId,
            @Parameter(description = "ID do animal vinculado ao interesse", example = "7") @PathVariable Long animalId,
            @Valid @RequestBody InteresseAdocaoDTORequest request) throws RecursoNaoEncontradoException {
        return ResponseEntity.ok(service.atualizar(pessoaId, animalId, request));
    }

}
