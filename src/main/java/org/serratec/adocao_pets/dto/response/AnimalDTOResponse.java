package org.serratec.adocao_pets.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.adocao_pets.domain.Animal;
import org.serratec.adocao_pets.enumerated.Especie;
import org.serratec.adocao_pets.enumerated.Sexo;
import org.serratec.adocao_pets.enumerated.StatusAdocao;
import org.serratec.adocao_pets.enumerated.Tamanho;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // cria os métodos GETs
@Setter // cria os métodos SETs
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "id",
        "nome",
        "mesesVida",
        "especie",
        "tamanho",
        "sexo",
        "descricao",
        "caracteristicas",
        "statusAdocao"
})
@JsonInclude(JsonInclude.Include.NON_NULL) // oculta parametros informados como null
@Schema(description = "Modelo de resposta com os dados detalhados do animal")
public class AnimalDTOResponse {

    @Schema(description = "Identificador único gerado automaticamente pelo banco de dados", example = "1")
    Long id;

    @Schema(description = "Nome do pet cadastrado", example = "Luna")
    String nome;

    @Schema(description = "Idade do pet calculada ou informada em meses", example = "12")
    Integer mesesVida;

    @Schema(description = "Breve descrição sobre a história, comportamento ou temperamento do pet", example = "Gatinha dócil que adora um colo e dormir na janela.")
    String descricao;

    @Schema(description = "Espécie biológica do pet", example = "GATO")
    Especie especie;

    @Schema(description = "Porte físico do animal", example = "PEQUENO")
    Tamanho tamanho;

    @Schema(description = "Sexo biológico do animal", example = "FEMEA")
    Sexo sexo;

    @Schema(description = "Situação atual do animal no sistema de adoção", example = "DISPONIVEL")
    StatusAdocao statusAdocao;

    @Schema(description = "Lista contendo os detalhes das características associadas a este animal")
    List<CaracteristicaDTOResponse> caracteristicas;

    public AnimalDTOResponse(Animal animal) {
        this.id = animal.getId();
        this.nome = animal.getNome();
        this.mesesVida = animal.getMesesVida();
        this.especie = animal.getEspecie();
        this.tamanho = animal.getTamanho();
        this.sexo = animal.getSexo();
        this.descricao = animal.getDescricao();
        this.statusAdocao = animal.getStatusAdocao();

        if (animal.getCaracteristicas() != null) {
            this.caracteristicas = animal.getCaracteristicas().stream()
                    .map(caracteristica -> {
                        CaracteristicaDTOResponse dto = new CaracteristicaDTOResponse();
                        dto.setId(caracteristica.getId());
                        dto.setDescricao(caracteristica.getDescricao());
                        return dto;
                    })
                    .collect(Collectors.toList());
        }
    }

    public static AnimalDTOResponse toAnimalResponse(Animal animal) {
        AnimalDTOResponse response = new AnimalDTOResponse();
        response.setId(animal.getId());
        response.setNome(animal.getNome());
        response.setMesesVida(animal.getMesesVida());
        response.setDescricao(animal.getDescricao());
        response.setEspecie(animal.getEspecie());
        response.setTamanho(animal.getTamanho());
        response.setSexo(animal.getSexo());
        response.setStatusAdocao(animal.getStatusAdocao());

        if (animal.getCaracteristicas() != null) {
            List<CaracteristicaDTOResponse> dto = animal.getCaracteristicas().stream()
                    .map(c -> new CaracteristicaDTOResponse(c.getId(), c.getDescricao()))
                    .collect(Collectors.toList());
            response.setCaracteristicas(dto);
        }
        return response;
    }

    public static AnimalDTOResponse toAnimalResponseResumo(Animal animal) {

        AnimalDTOResponse response = new AnimalDTOResponse();
        response.setNome(animal.getNome());
        response.setMesesVida(animal.getMesesVida());
        response.setEspecie(animal.getEspecie());
        response.setTamanho(animal.getTamanho());
        response.setSexo(animal.getSexo());
        response.setDescricao(animal.getDescricao());
        response.setStatusAdocao(animal.getStatusAdocao());
        return response;

    }

}