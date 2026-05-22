package org.serratec.adocao_pets.controller;

import java.net.URI;
import java.util.List;

import org.serratec.adocao_pets.dto.request.CaracteristicaDTORequest;
import org.serratec.adocao_pets.dto.response.CaracteristicaDTOResponse;
import org.serratec.adocao_pets.exception.ErroResposta;
import org.serratec.adocao_pets.exception.RecursoNaoEncontradoException;
import org.serratec.adocao_pets.service.CaracteristicaService;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Características", description = "Endpoints para gerenciamento das características comportamentais e físicas dos animais")
@RestController
@RequestMapping("/caracteristicas-animais")
public class CaracteristicaController {

        @Autowired
        private CaracteristicaService service;

        @Operation(summary = "Listar todas as características", description = "Retorna uma lista contendo todas as características cadastradas no sistema.")
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
        @GetMapping
        public List<CaracteristicaDTOResponse> listar() {
                return service.listarTodos();
        }

        @Operation(summary = "Buscar característica por ID", description = "Retorna os detalhes de uma característica específica com base no ID informado.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Característica encontrada com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Nenhuma característica localizada com o ID informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @GetMapping("/{id}")
        public ResponseEntity<CaracteristicaDTOResponse> buscar(
                        @Parameter(description = "ID da característica", example = "1") @PathVariable Long id)
                        throws RecursoNaoEncontradoException {
                return service.buscar(id);
        }

        @Operation(summary = "Buscar características por descrição", description = "Retorna uma lista de características que coincidem ou contêm o termo informado na descrição.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Nenhuma característica encontrada para a descrição informada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @GetMapping("/descricao/{descricao}")
        public ResponseEntity<List<CaracteristicaDTOResponse>> buscarDescricao(
                        @Parameter(description = "Texto da descrição da característica", example = "Dócil") @PathVariable String descricao)
                        throws RecursoNaoEncontradoException {
                return service.buscarDescricao(descricao);
        }

        @Operation(summary = "Cadastrar nova característica", description = "Salva uma nova característica no banco de dados e retorna o recurso criado com sua respectiva URI de localização.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Característica criada com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos ou mal formatados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class))),
                        @ApiResponse(responseCode = "409", description = "Característica com esta mesma descrição já existe no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @PostMapping
        public ResponseEntity<CaracteristicaDTOResponse> salvar(@Valid @RequestBody CaracteristicaDTORequest request) {
                CaracteristicaDTOResponse caracteristicasResponse = service.salvar(request);

                URI uri = ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(caracteristicasResponse.getId())
                                .toUri();

                return ResponseEntity.created(uri).body(caracteristicasResponse);
        }

        @Operation(summary = "Cadastrar múltiplas características", description = "Recebe uma lista de características e realiza o processamento e salvamento em lote.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Lista de características cadastrada com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Um ou mais elementos da lista contêm dados inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @PostMapping("/salvar-lista")
        public ResponseEntity<List<CaracteristicaDTOResponse>> salvarLista(
                        @Valid @RequestBody List<CaracteristicaDTORequest> request) {
                List<CaracteristicaDTOResponse> salvas = service.salvarList(request);
                return ResponseEntity.status(HttpStatus.CREATED).body(salvas);
        }

        @Operation(summary = "Alterar propriedades da característica", description = "Atualiza completamente os dados de uma característica existente através do ID informado.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Característica atualizada com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Corpo da requisição possui dados inconsistentes", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class))),
                        @ApiResponse(responseCode = "404", description = "Característica não encontrada para o ID informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class))),
                        @ApiResponse(responseCode = "409", description = "Conflito ao atualizar (ex: nova descrição já está em uso por outro registro)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @PutMapping("/{id}")
        public ResponseEntity<CaracteristicaDTOResponse> atualizarTudo(@Valid @PathVariable Long id,
                        @Parameter(description = "ID único da característica a ser alterada", example = "1") @Valid @RequestBody CaracteristicaDTORequest request) {
                return service.atualizar(id, request);
        }

        @Operation(summary = "Excluir uma característica", description = "Remove permanentemente o registro de uma característica do sistema através de seu ID.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Característica excluída com sucesso (Sem conteúdo de retorno)"),
                        @ApiResponse(responseCode = "404", description = "Característica não localizada para exclusão", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletar(
                        @Parameter(description = "ID da característica a ser excluída", example = "1") @PathVariable Long id)
                        throws RecursoNaoEncontradoException {
                service.excluir(id);
                return ResponseEntity.noContent().build();
        }

}
