package pe.cibertec.grupo08.cita.mapper;

import org.springframework.stereotype.Component;
import pe.cibertec.grupo08.cita.dto.FichaMedicaResponse;
import pe.cibertec.grupo08.cita.entity.FichaMedicaConsolidada;

@Component
public class FichaMedicaMapper {
    public FichaMedicaResponse toResponse(FichaMedicaConsolidada ficha) {
        return new FichaMedicaResponse(
                ficha.getId(),
                ficha.getPacienteId(),
                ficha.getProfesionalId(),
                ficha.getFechaGeneracion(),
                ficha.getEstado(),
                ficha.getReferenciaDocumento()
        );
    }
}