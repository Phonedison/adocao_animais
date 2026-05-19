package org.serratec.adocao_pets.repository;

import java.util.List;

import org.serratec.adocao_pets.domain.InteresseAdocao;
import org.serratec.adocao_pets.domain.InteresseAdocaoPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteresseAdocaoRepository extends JpaRepository<InteresseAdocao, InteresseAdocaoPK> {

    List<InteresseAdocao> findByObservacoesContainingIgnoreCase(String valor);

}
