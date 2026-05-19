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

    public static Caracteristica toCaracteristica(CaracteristicaDTORequest request) {
        Caracteristica caracteristica = new Caracteristica();
        caracteristica.setDescricao(request.getDescricao());

        return caracteristica;
    }

    public static CaracteristicaDTOResponse toCaracteristicaResponse(Caracteristica caracteristica) {
        CaracteristicaDTOResponse response = new CaracteristicaDTOResponse();

        response.setId(caracteristica.getId());
        response.setDescricao(caracteristica.getDescricao());

        return response;
    }

    // Métodos para o GETS
    public List<CaracteristicaDTOResponse> listarTodos() {
        List<Caracteristica> caracteristicas = caracteristicaRepository.findAll();
        return caracteristicas.stream().map(CaracteristicaService::toCaracteristicaResponse).toList();
    }

    public ResponseEntity<CaracteristicaDTOResponse> buscar(Long id) throws RecursoNaoEncontradoException {
        return caracteristicaRepository.findById(id)
                .map(CaracteristicaService::toCaracteristicaResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Animal de ID '" + id + "' não encontrado!"));
    }

    public ResponseEntity<List<CaracteristicaDTOResponse>> buscarDescricao(String descricao)
            throws RecursoNaoEncontradoException {
        List<CaracteristicaDTOResponse> caracteristicas = caracteristicaRepository
                .findByDescricaoContainingIgnoreCase(descricao)
                .stream()
                .map(CaracteristicaService::toCaracteristicaResponse)
                .collect(Collectors.toList());

        if (caracteristicas.isEmpty()) {
            throw new RecursoNaoEncontradoException(
                    "Nenhum animal com descricao contendo '" + descricao + "' encontrado!");
        }
        return ResponseEntity.ok(caracteristicas);
    }

    // Métodos para o POST
    public CaracteristicaDTOResponse salvar(CaracteristicaDTORequest request) {
        Caracteristica caracteristica = toCaracteristica(request);
        Caracteristica salvo = caracteristicaRepository.save(caracteristica);
        return toCaracteristicaResponse(salvo);
    }

    public List<CaracteristicaDTOResponse> salvarList(List<CaracteristicaDTORequest> request) {
        List<Caracteristica> caracteristicas = request.stream().map(CaracteristicaService::toCaracteristica).toList();
        List<Caracteristica> salvo = caracteristicaRepository.saveAll(caracteristicas);
        return salvo.stream().map(CaracteristicaService::toCaracteristicaResponse).toList();
    }

    // Métodos para o PUT
    public ResponseEntity<CaracteristicaDTOResponse> atualizar(Long id, CaracteristicaDTORequest request) {
        return caracteristicaRepository.findById(id).map(existe -> {

            existe.setDescricao(request.getDescricao());

            Caracteristica salvo = caracteristicaRepository.save(existe);
            CaracteristicaDTOResponse response = toCaracteristicaResponse(salvo);

            return ResponseEntity.ok(response);

        }).orElse(ResponseEntity.notFound().build());
    }

    // Métodos DELETE
    public boolean excluir(Long id) {
        return caracteristicaRepository.findById(id).map(caracteristica -> {
            caracteristicaRepository.delete(caracteristica);
            return true;
        }).orElse(false);
    }
}