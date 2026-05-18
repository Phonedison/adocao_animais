package org.serratec.adocao_pets.enumerated;

import org.serratec.adocao_pets.exception.EnumValidationException;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum StatusProcesso {
    PENDENTE, APROVADO, REJEITADO, CANCELADO;

    @JsonCreator
    public static StatusProcesso verifica(String value) throws EnumValidationException {
        for (StatusProcesso s : values()) {
            if (value.equals(s.name())) {
                return s;
            }
        }
        throw new EnumValidationException(
                "Status Processo inválido(a). Valores válidos: PENDENTE, APROVADO, REJEITADO OU CANCELADO");
    }
}
