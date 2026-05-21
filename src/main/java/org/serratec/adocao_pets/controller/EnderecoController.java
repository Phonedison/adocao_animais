package org.serratec.adocao_pets.controller;

import java.net.URI;
import java.util.List;

import org.serratec.adocao_pets.dto.request.EnderecoDTORequest;
import org.serratec.adocao_pets.dto.response.EnderecoDTOResponse;
import org.serratec.adocao_pets.exception.ErroResposta;
import org.serratec.adocao_pets.exception.RecursoNaoEncontradoException;
import org.serratec.adocao_pets.service.EnderecoService;
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

@Tag(name = "Endereços", description = "Endpoints para gerenciamento de endereços dos abrigos ou tutores")
@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

        @Autowired
        private EnderecoService service;

        @Operation(summary = "Listar todos os endereços", description = "Retorna uma lista contendo todos os endereços cadastrados no sistema.")
        @ApiResponse(responseCode = "200", description = "Lista de endereços retornada com sucesso")
        @GetMapping
        public List<EnderecoDTOResponse> listar() {
                return service.listarTodos();
        }

        @Operation(summary = "Buscar endereço por ID", description = "Retorna os detalhes de um endereço específico com base no ID informado.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Endereço não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @GetMapping("/{id}")
        public ResponseEntity<EnderecoDTOResponse> buscar(
                        @Parameter(description = "ID do endereço", example = "1") @PathVariable Long id)
                        throws RecursoNaoEncontradoException {
                return service.buscar(id);
        }

        @Operation(summary = "Buscar endereços por bairro", description = "Retorna uma lista de endereços filtrados pelo nome do bairro.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Nenhum endereço encontrado para o bairro informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @GetMapping("/bairro/{bairro}")
        public ResponseEntity<List<EnderecoDTOResponse>> buscarBairro(
                        @Parameter(description = "Nome do bairro", example = "Centro") @Valid @PathVariable String bairro)
                        throws RecursoNaoEncontradoException {
                return service.buscarBairro(bairro);
        }

        @Operation(summary = "Buscar endereços por número", description = "Retorna uma lista de endereços que possuem o número predial informado.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Nenhum endereço encontrado com este número", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @GetMapping("/numero/{numero}")
        public ResponseEntity<List<EnderecoDTOResponse>> buscarNumero(
                        @Parameter(description = "Número do imóvel", example = "123") @Valid @PathVariable String numero)
                        throws RecursoNaoEncontradoException {
                return service.buscarNumero(numero);
        }

        @Operation(summary = "Buscar endereços por cidade", description = "Retorna uma lista de endereços filtrados pelo nome da cidade.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Nenhum endereço encontrado para a cidade informada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @GetMapping("/cidade/{cidade}")
        public ResponseEntity<List<EnderecoDTOResponse>> buscarCidade(
                        @Parameter(description = "Nome da cidade", example = "Petrópolis") @Valid @PathVariable String cidade)
                        throws RecursoNaoEncontradoException {
                return service.buscarCidade(cidade);
        }

        @Operation(summary = "Buscar endereços por rua/logradouro", description = "Retorna uma lista de endereços filtrados pelo nome da rua.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Nenhum endereço encontrado para a rua informada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @GetMapping("/rua/{rua}")
        public ResponseEntity<List<EnderecoDTOResponse>> buscarRua(
                        @Parameter(description = "Nome da rua ou logradouro", example = "Avenida do Imperador") @Valid @PathVariable String rua)
                        throws RecursoNaoEncontradoException {
                return service.buscarRua(rua);
        }

        @Operation(summary = "Buscar endereços por estado (UF)", description = "Retorna uma lista de endereços filtrados pela sigla do estado.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Nenhum endereço encontrado para o estado informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @GetMapping("/estado/{estado}")
        public ResponseEntity<List<EnderecoDTOResponse>> buscarEstado(
                        @Parameter(description = "Sigla do estado (UF)", example = "RJ") @Valid @PathVariable String estado)
                        throws RecursoNaoEncontradoException {
                return service.buscarEstado(estado);
        }

        @Operation(summary = "Buscar endereço por CEP", description = "Retorna uma lista de endereços correspondentes ao CEP informado.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Nenhum endereço encontrado para o CEP informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @GetMapping("/cep/{cep}")
        public ResponseEntity<List<EnderecoDTOResponse>> buscarCep(
                        @Parameter(description = "CEP completo (apenas números ou com hífen)", example = "25620-000") @Valid @PathVariable String cep)
                        throws RecursoNaoEncontradoException {
                return service.buscarCep(cep);
        }

        @Operation(summary = "Cadastrar novo endereço", description = "Salva um novo endereço no sistema e retorna o recurso criado acompanhado de sua URI de acesso.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Endereço cadastrado com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class))),
                        @ApiResponse(responseCode = "409", description = "Conflito nos dados (ex: endereço idêntico já cadastrado)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
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

        @Operation(summary = "Alterar todas as propriedades do endereço", description = "Atualiza completamente as informações de um endereço existente com base no ID.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Dados informados inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class))),
                        @ApiResponse(responseCode = "404", description = "Endereço não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class))),
                        @ApiResponse(responseCode = "409", description = "Conflito ao atualizar os dados do endereço", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @PutMapping("/{id}")
        public ResponseEntity<EnderecoDTOResponse> atualizarTudo(
                        @Parameter(description = "ID do Endereço a ser atualizado", example = "1") @Valid @PathVariable Long id,
                        @Valid @RequestBody EnderecoDTORequest request) {
                return service.atualizar(id, request);
        }

        @Operation(summary = "Cadastrar múltiplos endereços", description = "Recebe uma lista de endereços e realiza o cadastro em lote.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Lista de endereços cadastrada com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Dados de um ou mais itens da lista inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @PostMapping("/salvar-lista")
        public ResponseEntity<List<EnderecoDTOResponse>> salvarVarios(
                        @Valid @RequestBody List<EnderecoDTORequest> request) {
                List<EnderecoDTOResponse> enderecoResponse = service.salvarList(request);

                return ResponseEntity.status(HttpStatus.CREATED).body(enderecoResponse);
        }

        // Métodos DELETE
        @Operation(summary = "Excluir um endereço", description = "Remove permanentemente o registro de um endereço do sistema através do ID.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Endereço excluído com sucesso (No Content)"),
                        @ApiResponse(responseCode = "404", description = "Endereço não encontrado para exclusão", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<EnderecoDTOResponse> deletar(
                        @Parameter(description = "ID do endereço a ser excluído", example = "1") @PathVariable Long id)
                        throws RecursoNaoEncontradoException {
                service.excluir(id);
                return ResponseEntity.noContent().build();
        }
}
