package org.serratec.adocao_pets.enumerated;

import org.serratec.adocao_pets.exception.EnumValidationException;

public enum Tamanho {
    PEQUENO, MEDIO, GRANDE;

    public static Tamanho verifica(String value) throws EnumValidationException {
        for (Tamanho t : values()) {
            if (value.equals(t.name())) {
                return t;
            }
        }
        throw new EnumValidationException(
                "Tamanho inválido(a). Valores válidos: PEQUENO, MEDIO OU GRANDE");
    }
}
