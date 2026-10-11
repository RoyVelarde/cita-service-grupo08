package pe.cibertec.grupo08.cita.dto;

import java.time.LocalDateTime;

public record ConsultaMedicaResponse(
        Long id,
        Long citaId,
        Long pacienteId,
        Long profesionalId,
        LocalDateTime fechaAtencion,
        String diagnostico,
        String observaciones,
        LocalDateTime createdAt
) {
}