package org.serratec.adocao_pets.repository;

import java.util.List;

import org.serratec.adocao_pets.domain.Animal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    @Query(("Select a FROM animal a WHERE a.nome LIKE %:parte%"))
    List<Animal> findByNome(@Param("parte") String valor);

    List<Animal> findByEspecie(String especie);

    List<Animal> findByTamanho(String tamanho);

    List<Animal> findBySexo(String sexo);

    List<Animal> findByStatusAdocao(String statusAdocao);

}
