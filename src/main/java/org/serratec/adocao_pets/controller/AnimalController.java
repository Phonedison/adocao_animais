package org.serratec.adocao_pets.controller;

import java.net.URI;
import java.util.List;

import org.serratec.adocao_pets.dto.request.AnimalDTORequest;
import org.serratec.adocao_pets.dto.response.AnimalDTOResponse;
import org.serratec.adocao_pets.dto.response.CaracteristicaDTOResponse;
import org.serratec.adocao_pets.dto.response.EnderecoDTOResponse;
import org.serratec.adocao_pets.exception.ErroResposta;
import org.serratec.adocao_pets.exception.RecursoNaoEncontradoException;
import org.serratec.adocao_pets.service.AnimalService;
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

@Tag(name = "Animais", description = "Endpoints para gerenciamento de animais para adoção")
@RestController
@RequestMapping("/animais")
public class AnimalController {

        @Autowired
        private AnimalService service;

        @Operation(summary = "Listar todos os animais", description = "Retorna uma lista contendo todos os animais cadastrados no sistema.")
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
        @GetMapping
        public List<AnimalDTOResponse> listar() {
                return service.listarTodos();
        }

        @Operation(summary = "Buscar animal por ID", description = "Retorna os detalhes completos de um animal específico com base no ID informado.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Animal encontrado com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Nenhum animal encontrado com o ID informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @GetMapping("/{id}")
        public AnimalDTOResponse buscar(
                        @Parameter(description = "ID único do animal", example = "1") @PathVariable Long id)
                        throws RecursoNaoEncontradoException {
                return service.buscar(id);
        }

        @Operation(summary = "Buscar animais por nome", description = "Retorna uma lista de animais cujo nome contenha o termo informado (Ignore Case).")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Nenhum animal localizado com o nome informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @GetMapping("/nome/{nome}")
        public List<AnimalDTOResponse> buscarNome(
                        @Parameter(description = "Nome do animal ou parte dele", example = "Rex") @Valid @PathVariable String nome)
                        throws RecursoNaoEncontradoException {
                return service.buscarNome(nome);
        }

        // implementar depois de forma mais organizada
        @Operation(summary = "Buscar animais por espécie", description = "Retorna uma lista de animais filtrados pela espécie informada.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Espécie informada inválida ou sem animais cadastrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @GetMapping("/especie/{especie}")
        public List<AnimalDTOResponse> buscarEspecie(
                        @Parameter(description = "Espécie do animal", example = "CAO") @Valid @PathVariable String especie)
                        throws RecursoNaoEncontradoException {
                return service.buscarEspecies(especie);
        }

        @Operation(summary = "Buscar animais por tamanho", description = "Retorna uma lista de animais filtrados pelo porte físico/tamanho.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Porte informado inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @GetMapping("/tamanho/{tamanho}")
        public List<AnimalDTOResponse> buscarTamanho(
                        @Parameter(description = "Tamanho/Porte do animal", example = "MEDIO") @Valid @PathVariable String tamanho)
                        throws RecursoNaoEncontradoException {
                return service.buscarTamanho(tamanho);
        }

        @Operation(summary = "Buscar animais por sexo", description = "Retorna uma lista de animais filtrados pelo sexo.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Sexo informado inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @GetMapping("/sexo/{sexo}")
        public List<AnimalDTOResponse> buscarSexo(
                        @Parameter(description = "Sexo do animal", example = "MACHO") @Valid @PathVariable String sexo)
                        throws RecursoNaoEncontradoException {
                return service.buscarSexo(sexo);
        }

        @Operation(summary = "Buscar animais por status de adoção", description = "Filtra os animais pelos status cadastrados (ex: DISPONIVEL, ANALISE, ADOTADO).")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Status informado inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @GetMapping("/adocao/{adocao}")
        public List<AnimalDTOResponse> buscarAdocao(
                        @Parameter(description = "Status da adoção", example = "DISPONIVEL") @Valid @PathVariable String adocao)
                        throws RecursoNaoEncontradoException {
                return service.buscarAdocao(adocao);
        }

        @Operation(summary = "Listar características do animal", description = "Retorna apenas o conjunto de características vinculadas ao ID do animal informado.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Características listadas com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Animal não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @GetMapping("/{id}/caracteristicas")
        public List<CaracteristicaDTOResponse> listarCaracteristicas(
                        @Parameter(description = "ID do animal", example = "1") @Valid @PathVariable Long id) {
                return service.listarCaracteristicas(id);
        }

        @Operation(summary = "Cadastrar novo animal", description = "Salva um novo animal no banco de dados e retorna o recurso criado acompanhado da sua URI de localização.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Animal cadastrado com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos ou mal formatados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @PostMapping
        public ResponseEntity<AnimalDTOResponse> salvar(@Valid @RequestBody AnimalDTORequest request) {
                AnimalDTOResponse animalResponse = service.salvar(request);

                URI uri = ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(animalResponse.getId())
                                .toUri();

                return ResponseEntity.created(uri).body(animalResponse);
        }

        @Operation(summary = "Cadastrar múltiplos animais", description = "Recebe uma lista de animais em lote e realiza o cadastro em massa de uma única vez.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Lista de animais processada e cadastrada com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Um ou mais elementos da lista contêm dados inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @PostMapping("/salvar-lista")
        public ResponseEntity<List<AnimalDTOResponse>> salvarVarios(
                        @Valid @RequestBody List<AnimalDTORequest> request) {
                List<AnimalDTOResponse> animaisResponse = service.salvarList(request);

                return ResponseEntity.status(HttpStatus.CREATED).body(animaisResponse);
        }

        @Operation(summary = "Alterar todas as propriedades do animal", description = "Substitui completamente os dados de um animal existente com base no ID informado.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Animal atualizado com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Corpo da requisição possui dados inconsistentes", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class))),
                        @ApiResponse(responseCode = "404", description = "Animal não localizado para atualização", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
        })
        @PutMapping("/{id}")
        public AnimalDTOResponse atualizarTudo(
                        @Parameter(description = "ID do animal a ser modificado", example = "1") @Valid @PathVariable Long id,
                        @Valid @RequestBody AnimalDTORequest request) {
                return service.atualizar(id, request);
        }

        @Operation(summary = "Excluir um animal", description = "Remove definitivamente o registro do animal do sistema com base no ID informado.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Animal excluído com sucesso (Sem conteúdo de retorno)"),
                        @ApiResponse(responseCode = "404", description = "Animal não encontrado no banco para exclusão", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class))),
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<EnderecoDTOResponse> deletar(
                        @Parameter(description = "ID do animal a ser deletado", example = "1") @PathVariable Long id)
                        throws RecursoNaoEncontradoException {
                service.excluir(id);
                return ResponseEntity.noContent().build();
        }
}
