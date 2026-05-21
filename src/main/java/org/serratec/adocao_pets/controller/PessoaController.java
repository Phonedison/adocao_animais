package org.serratec.adocao_pets.controller;

import java.util.List;

import org.serratec.adocao_pets.dto.request.PessoaDTORequest;
import org.serratec.adocao_pets.dto.response.PessoaDTOResponse;
import org.serratec.adocao_pets.exception.ErroResposta;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Pessoas", description = "Endpoints para gerenciamento de adotantes, tutores e voluntários")
@RestController
@RequestMapping("/pessoas")
public class PessoaController {

        @Autowired
        private PessoaService service;

        @Operation(summary = "Listar todas as pessoas", description = "Retorna uma lista contendo todas as pessoas cadastradas no sistema.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lista de pessoas retornada com sucesso")
        })
        @GetMapping
        public ResponseEntity<List<PessoaDTOResponse>> listar() {
                return ResponseEntity.ok(service.listarTodas());
        }

        @Operation(summary = "Buscar pessoa por ID", description = "Retorna os detalhes de uma pessoa específica cadastrada com base no ID informado.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Pessoa encontrada com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @GetMapping("/{id}")
        public ResponseEntity<PessoaDTOResponse> buscarPorId(
                        @Parameter(description = "ID único da pessoa", example = "1") @PathVariable Long id)
                        throws RecursoNaoEncontradoException {
                return ResponseEntity.ok(service.buscarPorId(id));
        }

        @Operation(summary = "Cadastrar nova pessoa", description = "Salva uma nova pessoa (adotante/tutor) no sistema.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Pessoa cadastrada com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos (ex: CPF ou e-mail malformados)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class))),
                        @ApiResponse(responseCode = "409", description = "Conflito: CPF ou E-mail já cadastrado no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @PostMapping
        public ResponseEntity<PessoaDTOResponse> cadastrar(@Valid @RequestBody PessoaDTORequest request) {
                PessoaDTOResponse response = service.salvar(request);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        @Operation(summary = "Cadastrar múltiplas pessoas", description = "Recebe uma lista de pessoas e realiza o cadastro em lote.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Lista de pessoas cadastrada com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Dados de um ou mais itens da lista inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @PostMapping("/salvar-lista")
        public ResponseEntity<List<PessoaDTOResponse>> salvarVarios(
                        @Valid @RequestBody List<PessoaDTORequest> request) {
                List<PessoaDTOResponse> pessoaResponse = service.salvarList(request);

                return ResponseEntity.status(HttpStatus.CREATED).body(pessoaResponse);
        }

        @Operation(summary = "Atualizar dados da pessoa", description = "Atualiza completamente as informações de uma pessoa existente com base no ID.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Dados da pessoa atualizados com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Dados informados inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class))),
                        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class))),
                        @ApiResponse(responseCode = "409", description = "Conflito ao atualizar (ex: e-mail já em uso por outra pessoa)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @PutMapping("/{id}")
        public ResponseEntity<PessoaDTOResponse> atualizar(
                        @Parameter(description = "ID da pessoa a ser atualizada", example = "1") @PathVariable Long id,
                        @Valid @RequestBody PessoaDTORequest request) throws RecursoNaoEncontradoException {
                return ResponseEntity.ok(service.atualizar(id, request));
        }

        @Operation(summary = "Excluir uma pessoa", description = "Remove permanentemente o registro de uma pessoa do sistema através do ID.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Pessoa excluída com sucesso (No Content)"),
                        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada para exclusão")
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletar(
                        @Parameter(description = "ID da pessoa a ser excluída", example = "1") @PathVariable Long id)
                        throws RecursoNaoEncontradoException {
                service.deletar(id);
                return ResponseEntity.noContent().build();
        }

}
