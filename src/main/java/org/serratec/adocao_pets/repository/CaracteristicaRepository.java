package org.serratec.adocao_pets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.serratec.adocao_pets.domain.Caracteristica;
import org.springframework.stereotype.Repository;

@Repository
public interface CaracteristicaRepository extends JpaRepository<Caracteristica, Long> {

}
