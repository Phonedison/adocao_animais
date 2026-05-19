package org.serratec.adocao_pets.controller;

import java.util.List;

import org.serratec.adocao_pets.domain.Animal;
import org.serratec.adocao_pets.dto.request.AnimalDTORequest;
import org.serratec.adocao_pets.dto.response.AnimalDTOResponse;
import org.serratec.adocao_pets.exception.RecursoNaoEncontradoException;
import org.serratec.adocao_pets.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<AnimalDTOResponse>> buscarNome(@PathVariable String nome)
            throws RecursoNaoEncontradoException {
        return service.buscarNome(nome);
    }

    @GetMapping("/especie/{especie}")
    public ResponseEntity<List<AnimalDTOResponse>> buscarEspecie(@PathVariable String especie)
            throws RecursoNaoEncontradoException {
        return service.buscarEspecies(especie);
    }

    @GetMapping("/tamanho/{tamanho}")
    public ResponseEntity<List<AnimalDTOResponse>> buscarTamanho(@PathVariable String tamanho)
            throws RecursoNaoEncontradoException {
        return service.buscarTamanho(tamanho);
    }

    @GetMapping("/sexo/{sexo}")
    public ResponseEntity<List<AnimalDTOResponse>> buscarSexo(@PathVariable String sexo)
            throws RecursoNaoEncontradoException {
        return service.buscarSexo(sexo);
    }

    @GetMapping("/adocao/{adocao}")
    public ResponseEntity<List<AnimalDTOResponse>> buscarAdocao(@PathVariable String adocao)
            throws RecursoNaoEncontradoException {
        return service.buscarAdocao(adocao);
    }

    @PostMapping
    public ResponseEntity<AnimalDTOResponse> salvar(@Valid @RequestBody AnimalDTORequest request) {
        Animal animal = AnimalService.toAnimal(request);
        Animal animalSalvo = service.salvar(animal);
        AnimalDTOResponse animalResponse = AnimalService.toAnimalResponse(animalSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(animalResponse);
    }

    @PostMapping("/lista")
    public ResponseEntity<List<AnimalDTOResponse>> salvarVarios(@Valid @RequestBody List<AnimalDTORequest> request) {
        List<Animal> animais = request.stream().map(AnimalService::toAnimal).toList();
        List<Animal> animaisSalvos = service.salvarList(animais);
        List<AnimalDTOResponse> animaisResponse = animaisSalvos.stream()
                .map(AnimalService::toAnimalResponse).toList();

        return ResponseEntity.status(HttpStatus.CREATED).body(animaisResponse);
    }
}