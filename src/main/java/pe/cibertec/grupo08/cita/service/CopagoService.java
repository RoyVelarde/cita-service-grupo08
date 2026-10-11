package pe.cibertec.grupo08.cita.service;

import pe.cibertec.grupo08.cita.dto.CopagoRequest;
import pe.cibertec.grupo08.cita.dto.CopagoResponse;

import java.util.List;

public interface CopagoService {
    CopagoResponse calcular(CopagoRequest request);

    CopagoResponse buscarPorId(Long id);

    CopagoResponse buscarPorCita(Long citaId);

    List<CopagoResponse> listarPorPaciente(Long pacienteId);
}
