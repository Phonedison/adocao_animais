package org.serratec.adocao_pets.dto.request;

import java.util.List;
import java.util.Set;
import org.serratec.adocao_pets.domain.Animal;
import org.serratec.adocao_pets.domain.Caracteristica;
import org.serratec.adocao_pets.enumerated.Especie;
import org.serratec.adocao_pets.enumerated.Sexo;
import org.serratec.adocao_pets.enumerated.StatusAdocao;
import org.serratec.adocao_pets.enumerated.Tamanho;

public record AnimalDTORequest(
        String nome,
        Integer mesesVida,
        String descricao,
        Especie especie,
        Tamanho tamanho,
        Sexo sexo,
        StatusAdocao statusAdocao,
        List<Long> caracteristicas) {

    public Animal toAnimal(Set<Caracteristica> caracteristicasVinculadas) {
        Animal animal = new Animal();
        animal.setNome(this.nome());
        animal.setMesesVida(this.mesesVida());
        animal.setDescricao(this.descricao());
        animal.setEspecie(this.especie());
        animal.setTamanho(this.tamanho());
        animal.setSexo(this.sexo());
        animal.setStatusAdocao(this.statusAdocao());
        animal.setCaracteristicas(caracteristicasVinculadas);

        return animal;
    }
}