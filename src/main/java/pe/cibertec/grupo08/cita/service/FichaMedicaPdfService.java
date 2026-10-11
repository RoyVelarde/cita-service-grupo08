package pe.cibertec.grupo08.cita.service;

import pe.cibertec.grupo08.cita.dto.FichaMedicaContenidoResponse;

public interface FichaMedicaPdfService {
    byte[] generarPdf(FichaMedicaContenidoResponse ficha);
}
