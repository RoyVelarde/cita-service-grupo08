package pe.cibertec.grupo08.cita.dto;

import pe.cibertec.grupo08.cita.enums.EstadoFicha;

import java.time.LocalDateTime;

public record FichaMedicaResponse(
        Long id,
        Long pacienteId,
        Long profesionalId,
        LocalDateTime fechaGeneracion,
        EstadoFicha estado,
        String referenciaDocumento
) {
}
