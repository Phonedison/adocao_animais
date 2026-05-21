package org.serratec.adocao_pets.service;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.adocao_pets.domain.Caracteristica;
import org.serratec.adocao_pets.dto.request.CaracteristicaDTORequest;
import org.serratec.adocao_pets.dto.response.CaracteristicaDTOResponse;
import org.serratec.adocao_pets.exception.RecursoNaoEncontradoException;
import org.serratec.adocao_pets.repository.CaracteristicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CaracteristicaService {

    @Autowired
    private CaracteristicaRepository caracteristicaRepository;

    // Métodos para o GETS
    public List<CaracteristicaDTOResponse> listarTodos() {
        List<Caracteristica> caracteristicas = caracteristicaRepository.findAll();
        return caracteristicas.stream().map(c -> CaracteristicaDTOResponse.toCaracteristicaResponse(c)).toList();
    }

    public ResponseEntity<CaracteristicaDTOResponse> buscar(Long id) throws RecursoNaoEncontradoException {
        return caracteristicaRepository.findById(id)
                // estranhamente a classe não consegue se referenciar utilizando ::this, gerando
                // erro de referencia,precisei utilizar o modo lambda normal para corrigir esse
                // erro.
                .map(c -> CaracteristicaDTOResponse.toCaracteristicaResponse(
                        c))
                .map(ResponseEntity::ok)
                .orElseThrow(
                        () -> new RecursoNaoEncontradoException("Caracteristica de ID '" + id + "' não encontrado!"));
    }

    public ResponseEntity<List<CaracteristicaDTOResponse>> buscarDescricao(String descricao)
            throws RecursoNaoEncontradoException {
        List<CaracteristicaDTOResponse> caracteristicas = caracteristicaRepository
                .findByDescricaoContainingIgnoreCase(descricao)
                .stream()
                .map(c -> CaracteristicaDTOResponse.toCaracteristicaResponse(
                        c))
                .collect(Collectors.toList());

        if (caracteristicas.isEmpty()) {
            throw new RecursoNaoEncontradoException(
                    "Nenhuma descrição contendo '" + descricao + "' encontrado!");
        }
        return ResponseEntity.ok(caracteristicas);
    }

    // Métodos para o POST
    public CaracteristicaDTOResponse salvar(CaracteristicaDTORequest request) {
        Caracteristica caracteristica = CaracteristicaDTORequest.toCaracteristica(request);
        Caracteristica salvo = caracteristicaRepository.save(caracteristica);
        return CaracteristicaDTOResponse.toCaracteristicaResponse(salvo);
    }

    public List<CaracteristicaDTOResponse> salvarList(List<CaracteristicaDTORequest> request) {
        List<Caracteristica> caracteristicas = request.stream()
                .map(dtoRequest -> CaracteristicaDTORequest.toCaracteristica(dtoRequest))
                .toList();
        List<Caracteristica> salvo = caracteristicaRepository.saveAll(caracteristicas);
        return salvo.stream().map(c -> CaracteristicaDTOResponse.toCaracteristicaResponse(c)).toList();
    }

    // Métodos para o PUT
    public ResponseEntity<CaracteristicaDTOResponse> atualizar(Long id, CaracteristicaDTORequest request) {
        return caracteristicaRepository.findById(id).map(existe -> {

            if (request.descricao() != null && !request.descricao().isBlank())
                existe.setDescricao(request.descricao());

            Caracteristica salvo = caracteristicaRepository.save(existe);
            CaracteristicaDTOResponse response = CaracteristicaDTOResponse.toCaracteristicaResponse(salvo);

            return ResponseEntity.ok(response);

        }).orElse(ResponseEntity.notFound().build());
    }

    // Métodos DELETE
    public void excluir(Long id) {
        caracteristicaRepository.findById(id).ifPresentOrElse(
                caracteristicaRepository::delete,
                () -> {
                    throw new RecursoNaoEncontradoException("Caracteristica com o ID " + id + " não foi encontrado.");
                });
    }
}