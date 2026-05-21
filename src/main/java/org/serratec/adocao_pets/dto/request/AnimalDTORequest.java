package org.serratec.adocao_pets.dto.request;

import java.util.List;
import java.util.Set;

import org.serratec.adocao_pets.domain.Animal;
import org.serratec.adocao_pets.domain.Caracteristica;
import org.serratec.adocao_pets.enumerated.Especie;
import org.serratec.adocao_pets.enumerated.Sexo;
import org.serratec.adocao_pets.enumerated.StatusAdocao;
import org.serratec.adocao_pets.enumerated.Tamanho;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AnimalDTORequest(
        @NotBlank(message = "O campo não pode estar em branco") String nome,
        @NotNull(message = "O campo não pode estar nulo") @Positive(message = "Preencha o campo com valores acima de ZERO") Integer mesesVida,
        @Size(min = 10, max = 100, message = "Campo com permissão de 10 a 100 caracteres") String descricao,
        @NotNull(message = "A espécie deve ser informada, informe como CACHORRO ou GATO") Especie especie,
        @NotNull(message = "O tamanho deve ser informado como PEQUENO, MEDIO ou GRANDE") Tamanho tamanho,
        @NotNull(message = "O sexo deve ser informado como MACHO ou FEMEA") Sexo sexo,
        @NotNull(message = "O status de adoção deve ser informado como PENDENTE, APROVADO, REJEITADO ou CANCELADO") StatusAdocao statusAdocao,
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