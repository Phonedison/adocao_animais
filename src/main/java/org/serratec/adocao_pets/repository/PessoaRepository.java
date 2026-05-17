package org.serratec.adocao_pets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.serratec.adocao_pets.domain.Animal;
import org.serratec.adocao_pets.domain.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    @Query(("Select p FROM pessoa p WHERE a.nome LIKE %:parte%"))
    List<Animal> findByNome(@Param("parte") String valor);

    @Query(("Select p FROM pessoa p WHERE a.email LIKE %:parte%"))
    List<Animal> findByEmail(@Param("parte") String valor);

}
