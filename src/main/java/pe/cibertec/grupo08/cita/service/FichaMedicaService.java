package pe.cibertec.grupo08.cita.service;

import pe.cibertec.grupo08.cita.dto.FichaMedicaRequest;
import pe.cibertec.grupo08.cita.dto.FichaMedicaResponse;

import java.util.List;

public interface FichaMedicaService {
    FichaMedicaResponse generar(FichaMedicaRequest request);

    FichaMedicaResponse buscarPorId(Long id);

    List<FichaMedicaResponse> listarPorPaciente(Long pacienteId);

    FichaMedicaResponse anular(Long id);

    byte[] generarPdf(Long id);
}
