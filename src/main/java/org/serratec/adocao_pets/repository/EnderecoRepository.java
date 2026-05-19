package org.serratec.adocao_pets.repository;

import java.util.List;

import org.serratec.adocao_pets.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    /*
     * @Query(("Select e FROM endereco e WHERE e.rua LIKE :parte"))
     * List<Endereco> findByRua(@Param("parte") String parte);
     * 
     * @Query(("Select e FROM endereco e WHERE e.bairro LIKE :parte"))
     * List<Endereco> findByBairro(@Param("parte") String parte);
     * 
     * @Query(("Select e FROM endereco e WHERE e.Rua LIKE :parte"))
     * List<Endereco> findByCidade(@Param("parte") String parte);
     * 
     * @Query(("Select e FROM endereco e WHERE e.estado LIKE :parte"))
     * List<Endereco> findByEstado(@Param("parte") String parte);
     * 
     * @Query(("Select e FROM endereco e WHERE e.cep LIKE :parte"))
     * List<Endereco> findByCep(@Param("parte") String parte);
     */

    List<Endereco> findByRuaContainingIgnoreCase(String valor);

    List<Endereco> findByBairroContainingIgnoreCase(String valor);

    List<Endereco> findByCidadeContainingIgnoreCase(String valor);

    List<Endereco> findByEstadoContainingIgnoreCase(String valor);

    List<Endereco> findByCepContainingIgnoreCase(String valor);
}
