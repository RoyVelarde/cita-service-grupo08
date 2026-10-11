package pe.cibertec.grupo08.cita.service;

import pe.cibertec.grupo08.cita.dto.ConsultaMedicaRequest;
import pe.cibertec.grupo08.cita.dto.ConsultaMedicaResponse;

import java.util.List;

public interface ConsultaMedicaService {
    ConsultaMedicaResponse registrar(ConsultaMedicaRequest request);

    ConsultaMedicaResponse buscarPorId(Long id);

    ConsultaMedicaResponse buscarPorCita(Long citaId);

    List<ConsultaMedicaResponse> listarPorPaciente(Long pacienteId);

    List<ConsultaMedicaResponse> listarPorProfesional(Long profesionalId);
}
