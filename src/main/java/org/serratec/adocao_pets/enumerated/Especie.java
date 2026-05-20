package org.serratec.adocao_pets.enumerated;

import org.serratec.adocao_pets.exception.EnumValidationException;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Especie {
    CACHORRO, GATO;

    @JsonCreator
    public static Especie verifica(String value) throws EnumValidationException {
        if (value == null) {
            return null;
        }

        for (Especie e : values()) {
            if (e.name().equalsIgnoreCase(value)) {
                return e;
            }
        }
        throw new EnumValidationException("Especie inválido(a). Valores válidos: CACHORRO OU GATO");
    }
}