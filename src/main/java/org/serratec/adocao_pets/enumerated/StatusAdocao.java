package org.serratec.adocao_pets.enumerated;

import org.serratec.adocao_pets.exception.EnumValidationException;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum StatusAdocao {
    DISPONIVEL, ANALISE, ADOTADO;

    @JsonCreator
    public static StatusAdocao verifica(String value) throws EnumValidationException {
        if (value == null) {
            return null;
        }

        for (StatusAdocao e : values()) {
            if (e.name().equalsIgnoreCase(value)) {
                return e;
            }
        }
        throw new EnumValidationException("Status Adoção inválido(a). Valores válidos: DISPONIVEL, ANALISE OU ADOTADO");
    }
}
