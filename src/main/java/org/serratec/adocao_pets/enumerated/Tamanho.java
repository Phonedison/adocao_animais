package org.serratec.adocao_pets.enumerated;

import org.serratec.adocao_pets.exception.EnumValidationException;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Tamanho {
    PEQUENO, MEDIO, GRANDE;

    @JsonCreator
    public static Tamanho verifica(String value) throws EnumValidationException {

        if (value == null) {
            return null;
        }

        for (Tamanho e : values()) {
            if (e.name().equalsIgnoreCase(value)) {
                return e;
            }
        }
        throw new EnumValidationException(
                "Tamanho inválido(a). Valores válidos: PEQUENO, MEDIO OU GRANDE");
    }
}
