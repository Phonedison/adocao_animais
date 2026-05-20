package org.serratec.adocao_pets.controller;

import java.net.URI;
import java.util.List;

import org.serratec.adocao_pets.dto.request.AnimalDTORequest;
import org.serratec.adocao_pets.dto.response.AnimalDTOResponse;
import org.serratec.adocao_pets.dto.response.CaracteristicaDTOResponse;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/animais")
public class AnimalController {

    @Autowired
    private AnimalService service;

    @GetMapping
    public List<AnimalDTOResponse> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalDTOResponse> buscar(@PathVariable Long id) throws RecursoNaoEncontradoException {
        return service.buscar(id);
    }

    @GetMapping("/{nome}")
    public ResponseEntity<List<AnimalDTOResponse>> buscarNome(@Valid @PathVariable String nome)
            throws RecursoNaoEncontradoException {
        return service.buscarNome(nome);
    }

    // implementar depois de forma mais organizada

    @GetMapping("/especie/{especie}")
    public ResponseEntity<List<AnimalDTOResponse>> buscarEspecie(

            @Valid @PathVariable String especie)
            throws RecursoNaoEncontradoException {
        return service.buscarEspecies(especie);
    }

    @GetMapping("/tamanho/{tamanho}")
    public ResponseEntity<List<AnimalDTOResponse>> buscarTamanho(

            @Valid @PathVariable String tamanho)
            throws RecursoNaoEncontradoException {
        return service.buscarTamanho(tamanho);
    }

    @GetMapping("/sexo/{sexo}")
    public ResponseEntity<List<AnimalDTOResponse>> buscarSexo(

            @Valid @PathVariable String sexo)
            throws RecursoNaoEncontradoException {
        return service.buscarSexo(sexo);
    }

    @GetMapping("/adocao/{adocao}")
    public ResponseEntity<List<AnimalDTOResponse>> buscarAdocao(

            @Valid @PathVariable String adocao)
            throws RecursoNaoEncontradoException {
        return service.buscarAdocao(adocao);
    }

    @GetMapping("/{id}/caracteristicas")
    public ResponseEntity<List<CaracteristicaDTOResponse>> listarCaracteristicas(@Valid @PathVariable Long id) {
        return service.listarCaracteristicas(id);
    }

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

    @PostMapping("/salvar-lista")
    public ResponseEntity<List<AnimalDTOResponse>> salvarVarios(@Valid @RequestBody List<AnimalDTORequest> request) {
        List<AnimalDTOResponse> animaisResponse = service.salvarList(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(animaisResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalDTOResponse> atualizarTudo(@Valid @PathVariable Long id,
            @Valid @RequestBody AnimalDTORequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AnimalDTOResponse> deletar(@Valid @PathVariable Long id) {
        return service.excluir(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
