package org.serratec.adocao_pets.enumerated;

import org.serratec.adocao_pets.exception.EnumValidationException;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Sexo {
    MACHO, FEMEA;

    @JsonCreator
    public static Sexo verifica(String value) throws EnumValidationException {
        if (value == null) {
            return null;
        }

        for (Sexo e : values()) {
            if (e.name().equalsIgnoreCase(value)) {
                return e;
            }
        }
        throw new EnumValidationException("Sexo inválido(a). Valores válidos: MACHO OU FEMEA");
    }
}
