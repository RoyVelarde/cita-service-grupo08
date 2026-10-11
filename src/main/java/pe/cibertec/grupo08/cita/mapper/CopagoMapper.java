package pe.cibertec.grupo08.cita.mapper;

import org.springframework.stereotype.Component;
import pe.cibertec.grupo08.cita.dto.CopagoResponse;
import pe.cibertec.grupo08.cita.entity.Copago;

@Component
public class CopagoMapper {
    public CopagoResponse toResponse(Copago copago) {
        return new CopagoResponse(
                copago.getId(),
                copago.getCita().getId(),
                copago.getCita().getPacienteId(),
                copago.getCostoTotal(),
                copago.getMontoCubierto(),
                copago.getMontoPaciente(),
                copago.getMoneda(),
                copago.getFechaCalculo()
        );
    }
}