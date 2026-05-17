package org.serratec.adocao_pets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.serratec.adocao_pets.domain.Animal;
import org.serratec.adocao_pets.domain.InteresseAdocao;

@Repository
public interface InteresseAdocaoRepository extends JpaRepository<InteresseAdocao, Long> {

    @Query(("Select ia FROM interesse_adocao ia WHERE a.observacoes LIKE %:parte%"))
    List<Animal> findByObs(@Param("parte") String valor);

}
