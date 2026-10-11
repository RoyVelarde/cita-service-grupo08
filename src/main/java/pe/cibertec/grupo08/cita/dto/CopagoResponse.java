package pe.cibertec.grupo08.cita.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CopagoResponse(
        Long id,
        Long citaId,
        Long pacienteId,
        BigDecimal costoTotal,
        BigDecimal montoCubierto,
        BigDecimal montoPaciente,
        String moneda,
        LocalDateTime fechaCalculo
) {
}