package org.serratec.adocao_pets.enumerated;

import org.serratec.adocao_pets.exception.EnumValidationException;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Especie {
    CACHORRO, GATO;

    @JsonCreator
    public static Especie verifica(String value) throws EnumValidationException {
        for (Especie e : values()) {
            if (value.equals(e.name())) {
                return e;
            }
        }
        throw new EnumValidationException("Especie inválido(a). Valores válidos: CACHORRO OU GATO");
    }
}