package org.serratec.adocao_pets.repository;

import java.util.List;

import org.serratec.adocao_pets.domain.Caracteristica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaracteristicaRepository extends JpaRepository<Caracteristica, Long> {

    List<Caracteristica> findByDescricaoContainingIgnoreCase(String valor);
}
