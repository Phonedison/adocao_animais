package org.serratec.adocao_pets.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // cria os métodos GETs
@Setter // cria os métodos SETs
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTORequest {

        private Long id;
        private String rua;
        private String numero;
        private String bairro;
        private String cidade;
        private String estado;
        private String cep;
}
