package org.serratec.adocao_pets.enumerated;

import org.serratec.adocao_pets.exception.EnumValidationException;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum StatusProcesso {
    PENDENTE, APROVADO, REJEITADO, CANCELADO;

    @JsonCreator
    public static StatusProcesso verifica(String value) throws EnumValidationException {
        if (value == null) {
            return null;
        }

        for (StatusProcesso e : values()) {
            if (e.name().equalsIgnoreCase(value)) {
                return e;
            }
        }
        throw new EnumValidationException(
                "Status Processo inválido(a). Valores válidos: PENDENTE, APROVADO, REJEITADO OU CANCELADO");
    }
}
