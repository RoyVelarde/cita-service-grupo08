package pe.cibertec.grupo08.cita.dto;

import java.time.LocalDateTime;
import java.util.List;

public record FichaMedicaContenidoResponse(
        Long fichaId,
        Long pacienteId,
        Long profesionalGeneradorId,
        LocalDateTime fechaGeneracion,
        List<DetalleConsultaFichaResponse> consultas
) {
}
