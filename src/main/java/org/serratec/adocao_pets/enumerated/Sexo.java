package org.serratec.adocao_pets.enumerated;

import org.serratec.adocao_pets.exception.EnumValidationException;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Sexo {
    MACHO, FEMEA;

    @JsonCreator
    public static Sexo verifica(String value) throws EnumValidationException {
        for (Sexo s : values()) {
            if (value.equals(s.name())) {
                return s;
            }
        }
        throw new EnumValidationException("Sexo inválido(a). Valores válidos: MACHO OU FEMEA");
    }
}
