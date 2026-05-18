package org.serratec.adocao_pets.enumerated;

import org.serratec.adocao_pets.exception.EnumValidationException;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum StatusAdocao {
    DISPONIVEL, ANALISE, ADOTADO;

    @JsonCreator
    public static StatusAdocao verifica(String value) throws EnumValidationException {
        for (StatusAdocao st : values()) {
            if (value.equals(st.name())) {
                return st;
            }
        }
        throw new EnumValidationException("Status Adoção inválido(a). Valores válidos: DISPONIVEL, ANALISE OU ADOTADO");
    }
}
