package org.serratec.adocao_pets.dto.request;

import java.util.List;
import java.util.Set;

import org.serratec.adocao_pets.domain.Animal;
import org.serratec.adocao_pets.domain.Caracteristica;
import org.serratec.adocao_pets.enumerated.Especie;
import org.serratec.adocao_pets.enumerated.Sexo;
import org.serratec.adocao_pets.enumerated.StatusAdocao;
import org.serratec.adocao_pets.enumerated.Tamanho;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Modelo de requisição para cadastro e atualização de animais")
public record AnimalDTORequest(
        @Schema(description = "Nome do pet", example = "Luna", requiredMode = Schema.RequiredMode.REQUIRED) @NotBlank(message = "O campo não pode estar em branco") String nome,
        @Schema(description = "Idade aproximada do pet em meses", example = "12", minimum = "1", requiredMode = Schema.RequiredMode.REQUIRED) @NotNull(message = "O campo não pode estar nulo") @Positive(message = "Preencha o campo com valores acima de ZERO") Integer mesesVida,
        @Schema(description = "Breve descrição sobre o comportamento ou histórico do pet", example = "Gatinha dócil que adora um colo e dormir na janela.", minLength = 10, maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED) @NotNull(message = "O campo não pode estar nulo") @Size(min = 10, max = 100, message = "Campo com permissão de 10 a 100 caracteres") String descricao,
        @Schema(description = "Espécie do animal", example = "GATO", allowableValues = {
                "CACHORRO",
                "GATO" }, requiredMode = Schema.RequiredMode.REQUIRED) @NotNull(message = "A espécie deve ser informada, informe como CACHORRO ou GATO") Especie especie,
        @Schema(description = "Porte físico do animal", example = "PEQUENO", allowableValues = { "PEQUENO", "MEDIO",
                "GRANDE" }, requiredMode = Schema.RequiredMode.REQUIRED) @NotNull(message = "O tamanho deve ser informado como PEQUENO, MEDIO ou GRANDE") Tamanho tamanho,
        @Schema(description = "Sexo biológico do animal", example = "FEMEA", allowableValues = { "MACHO",
                "FEMEA" }, requiredMode = Schema.RequiredMode.REQUIRED) @NotNull(message = "O sexo deve ser informado como MACHO ou FEMEA") Sexo sexo,
        @Schema(description = "Situação atual do processo de adoção", example = "DISPONIVEL", allowableValues = {
                "DISPONIVEL", "PENDENTE", "APROVADO", "REJEITADO",
                "CANCELADO" }, requiredMode = Schema.RequiredMode.REQUIRED) @NotNull(message = "O status de adoção deve ser informado como PENDENTE, APROVADO, REJEITADO ou CANCELADO") StatusAdocao statusAdocao,
        @Schema(description = "Lista contendo os IDs das características físicas/comportamentais do pet", example = "[1, 5, 9]", requiredMode = Schema.RequiredMode.REQUIRED) @NotNull(message = "O campo caracteristica deve ser informado com um dos Ids informado!") List<Long> caracteristicas) {

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