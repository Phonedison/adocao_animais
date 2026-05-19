package org.serratec.adocao_pets.repository;

import java.util.List;

import org.serratec.adocao_pets.domain.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    /*
     * @Query(("Select p FROM pessoa p WHERE a.nome LIKE :parte"))
     * List<Animal> findByNome(@Param("parte") String parte);
     * 
     * @Query(("Select p FROM pessoa p WHERE a.email LIKE :parte"))
     * List<Animal> findByEmail(@Param("parte") String parte);
     */

    List<Pessoa> findByNomeContainingIgnoreCase(String valor);

    List<Pessoa> findByEmailContainingIgnoreCase(String valor);

}
