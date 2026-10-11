package pe.cibertec.grupo08.cita.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record ConsultaMedicaRequest(
        @NotNull(message = "El ID de la cita es obligatorio")
        @Positive(message = "El ID de la cita debe ser positivo")
        Long citaId,

        @NotNull(message = "La fecha de atención es obligatoria")
        LocalDateTime fechaAtencion,

        @NotBlank(message = "El diagnóstico es obligatorio")
        @Size(max = 5000, message = "El diagnóstico es demasiado extenso")
        String diagnostico,

        @Size(max = 5000, message = "Las observaciones son demasiado extensas")
        String observaciones,

        @NotNull(message = "El ID del profesional es obligatorio")
        @Positive(message = "El ID del profesional debe ser positivo")
        Long profesionalId
) {
}