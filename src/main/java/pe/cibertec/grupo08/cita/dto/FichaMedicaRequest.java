package pe.cibertec.grupo08.cita.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FichaMedicaRequest(
        @NotNull(message = "El ID del paciente es obligatorio")
        @Positive(message = "El ID del paciente debe ser positivo")
        Long pacienteId,

        @NotNull(message = "El ID del profesional es obligatorio")
        @Positive(message = "El ID del profesional debe ser positivo")
        Long profesionalId
) {
}
