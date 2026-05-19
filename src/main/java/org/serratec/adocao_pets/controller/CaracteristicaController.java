package org.serratec.adocao_pets.controller;

import java.net.URI;
import java.util.List;

import org.serratec.adocao_pets.dto.request.CaracteristicaDTORequest;
import org.serratec.adocao_pets.dto.response.CaracteristicaDTOResponse;
import org.serratec.adocao_pets.exception.RecursoNaoEncontradoException;
import org.serratec.adocao_pets.service.CaracteristicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/caracteristicas-animais")
public class CaracteristicaController {

    @Autowired
    private CaracteristicaService service;

    @GetMapping
    public List<CaracteristicaDTOResponse> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CaracteristicaDTOResponse> buscar(@PathVariable Long id)
            throws RecursoNaoEncontradoException {
        return service.buscar(id);
    }

    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<List<CaracteristicaDTOResponse>> buscarDescricao(@PathVariable String descricao)
            throws RecursoNaoEncontradoException {
        return service.buscarDescricao(descricao);
    }

    @PostMapping("/salvar")
    public ResponseEntity<CaracteristicaDTOResponse> salvar(@Valid @RequestBody CaracteristicaDTORequest request) {
        CaracteristicaDTOResponse animalResponse = service.salvar(request);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(animalResponse.getId())
                .toUri();

        return ResponseEntity.created(uri).body(animalResponse);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<CaracteristicaDTOResponse> atualizarTudo(@Valid @PathVariable Long id,
            @Valid @RequestBody CaracteristicaDTORequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<CaracteristicaDTOResponse> deletar(@Valid @PathVariable Long id) {
        return service.excluir(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
