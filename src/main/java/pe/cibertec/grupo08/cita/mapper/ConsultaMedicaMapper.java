package pe.cibertec.grupo08.cita.mapper;

import org.springframework.stereotype.Component;
import pe.cibertec.grupo08.cita.dto.ConsultaMedicaResponse;
import pe.cibertec.grupo08.cita.entity.ConsultaMedica;

@Component
public class ConsultaMedicaMapper {
    public ConsultaMedicaResponse toResponse(ConsultaMedica consulta) {
        return new ConsultaMedicaResponse(
                consulta.getId(),
                consulta.getCita().getId(),
                consulta.getCita().getPacienteId(),
                consulta.getProfesionalId(),
                consulta.getFechaAtencion(),
                consulta.getDiagnostico(),
                consulta.getObservaciones(),
                consulta.getCreatedAt()
        );
    }
}
