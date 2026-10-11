package pe.cibertec.grupo08.cita.mapper;

import org.springframework.stereotype.Component;
import pe.cibertec.grupo08.cita.dto.CitaRequest;
import pe.cibertec.grupo08.cita.dto.CitaResponse;
import pe.cibertec.grupo08.cita.entity.Cita;

@Component
public class CitaMapper {
    public Cita toEntity(CitaRequest request) {
        return new Cita(
                request.pacienteId(),
                request.medicoId(),
                request.fechaHora(),
                request.modalidad(),
                request.motivo()
        );
    }

    public CitaResponse toResponse(Cita cita) {
        return new CitaResponse(
                cita.getId(),
                cita.getPacienteId(),
                cita.getMedicoId(),
                cita.getFechaHora(),
                cita.getModalidad(),
                cita.getEstado(),
                cita.getMotivo(),
                cita.getCreatedAt(),
                cita.getUpdatedAt()
        );
    }
}
