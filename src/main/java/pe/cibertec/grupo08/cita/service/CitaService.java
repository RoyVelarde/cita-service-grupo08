package pe.cibertec.grupo08.cita.service;

import pe.cibertec.grupo08.cita.dto.ActualizarMotivoRequest;
import pe.cibertec.grupo08.cita.dto.CitaRequest;
import pe.cibertec.grupo08.cita.dto.CitaResponse;
import pe.cibertec.grupo08.cita.dto.ReprogramarCitaRequest;

import java.util.List;

public interface CitaService {
    CitaResponse registrar(CitaRequest request);

    CitaResponse reprogramar(Long id, ReprogramarCitaRequest request);

    CitaResponse actualizarMotivo(Long id, ActualizarMotivoRequest request);

    CitaResponse buscarPorId(Long id);

    List<CitaResponse> listarTodas();

    List<CitaResponse> listarPorPaciente(Long pacienteId);

    List<CitaResponse> listarPorMedico(Long medicoId);

    CitaResponse confirmar(Long id);

    CitaResponse cancelar(Long id);
}
