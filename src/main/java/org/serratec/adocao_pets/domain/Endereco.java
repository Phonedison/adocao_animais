package org.serratec.adocao_pets.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "endereco")
@Getter // cria os métodos GETs
@Setter // cria os métodos SETs
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Endereco {

    @Id
    private Long id;
    private String rua;
    private String numero;
    private String bairro;
    private String estado;
    private String cep;
}
