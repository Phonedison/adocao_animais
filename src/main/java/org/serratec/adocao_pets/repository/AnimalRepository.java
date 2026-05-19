package org.serratec.adocao_pets.repository;

import java.util.List;

import org.serratec.adocao_pets.domain.Animal;
import org.serratec.adocao_pets.enumerated.Especie;
import org.serratec.adocao_pets.enumerated.Sexo;
import org.serratec.adocao_pets.enumerated.StatusAdocao;
import org.serratec.adocao_pets.enumerated.Tamanho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> findByNomeContainingIgnoreCase(String nome);

    List<Animal> findByEspecie(Especie especie);

    List<Animal> findByTamanho(Tamanho tamanho);

    List<Animal> findBySexo(Sexo sexo);

    List<Animal> findByStatusAdocao(StatusAdocao statusAdocao);
}
