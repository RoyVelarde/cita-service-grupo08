package pe.cibertec.grupo08.cita.dto;

import java.time.LocalDateTime;

public record DetalleConsultaFichaResponse(
        Long consultaId,
        Long citaId,
        Long profesionalId,
        LocalDateTime fechaAtencion,
        String diagnostico,
        String observaciones
) {
}
