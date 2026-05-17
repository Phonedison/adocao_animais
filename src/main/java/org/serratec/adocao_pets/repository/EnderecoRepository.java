package org.serratec.adocao_pets.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.serratec.adocao_pets.domain.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    @Query(("Select e FROM endereco e WHERE e.rua LIKE %:parte%"))
    List<Endereco> findByRua(@Param("parte") String valor);

    @Query(("Select e FROM endereco e WHERE e.bairro LIKE %:parte%"))
    List<Endereco> findByBairro(@Param("parte") String valor);

    @Query(("Select e FROM endereco e WHERE e.Rua LIKE %:parte%"))
    List<Endereco> findByCidade(@Param("parte") String valor);

    @Query(("Select e FROM endereco e WHERE e.estado LIKE %:parte%"))
    List<Endereco> findByEstado(@Param("parte") String valor);

    @Query(("Select e FROM endereco e WHERE e.cep LIKE %:parte%"))
    List<Endereco> findByCep(@Param("parte") String valor);
}
