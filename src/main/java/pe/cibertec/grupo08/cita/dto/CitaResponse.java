package pe.cibertec.grupo08.cita.dto;

import pe.cibertec.grupo08.cita.enums.EstadoCita;
import pe.cibertec.grupo08.cita.enums.ModalidadCita;

import java.time.LocalDateTime;

public record CitaResponse(
        Long id,
        Long pacienteId,
        Long medicoId,
        LocalDateTime fechaHora,
        ModalidadCita modalidad,
        EstadoCita estado,
        String motivo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
