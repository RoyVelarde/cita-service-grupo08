package pe.cibertec.grupo08.cita.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.cibertec.grupo08.cita.dto.DetalleConsultaFichaResponse;
import pe.cibertec.grupo08.cita.dto.FichaMedicaContenidoResponse;
import pe.cibertec.grupo08.cita.dto.FichaMedicaRequest;
import pe.cibertec.grupo08.cita.dto.FichaMedicaResponse;
import pe.cibertec.grupo08.cita.entity.FichaMedicaConsolidada;
import pe.cibertec.grupo08.cita.enums.EstadoFicha;
import pe.cibertec.grupo08.cita.exception.ConflictoCitaException;
import pe.cibertec.grupo08.cita.exception.RecursoNoEncontradoException;
import pe.cibertec.grupo08.cita.mapper.FichaMedicaMapper;
import pe.cibertec.grupo08.cita.repository.ConsultaMedicaRepository;
import pe.cibertec.grupo08.cita.repository.FichaMedicaConsolidadaRepository;
import pe.cibertec.grupo08.cita.service.FichaMedicaPdfService;
import pe.cibertec.grupo08.cita.service.FichaMedicaService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FichaMedicaServiceImpl implements FichaMedicaService {
    private final FichaMedicaConsolidadaRepository fichaRepository;
    private final ConsultaMedicaRepository consultaRepository;
    private final FichaMedicaMapper fichaMapper;
    private final FichaMedicaPdfService pdfService;

    @Transactional
    @Override
    public FichaMedicaResponse generar(FichaMedicaRequest request) {
        List<DetalleConsultaFichaResponse> consultas = consultaRepository
                .findByCitaPacienteId(request.pacienteId())
                .stream()
                .map(consulta ->
                        new DetalleConsultaFichaResponse(
                                consulta.getId(),
                                consulta.getCita().getId(),
                                consulta.getProfesionalId(),
                                consulta.getFechaAtencion(),
                                consulta.getDiagnostico(),
                                consulta.getObservaciones()
                        )
                ).toList();
        if (consultas.isEmpty()) {
            throw new ConflictoCitaException("El paciente no tiene consultas médicas para consolidar");
        }
        FichaMedicaConsolidada ficha = new FichaMedicaConsolidada(request.pacienteId(), request.profesionalId());
        FichaMedicaConsolidada guardada = fichaRepository.saveAndFlush(ficha);
        FichaMedicaContenidoResponse contenido = new FichaMedicaContenidoResponse(
                guardada.getId(),
                guardada.getPacienteId(),
                guardada.getProfesionalId(),
                guardada.getFechaGeneracion(),
                consultas
        );
        byte[] pdf = pdfService.generarPdf(contenido);
        String referencia = "memoria:ficha-medica-" + guardada.getId() + ".pdf";
        guardada.asignarReferenciaDocumento(referencia);
        return fichaMapper.toResponse(fichaRepository.save(guardada));
    }

    @Transactional(readOnly = true)
    @Override
    public FichaMedicaResponse buscarPorId(Long id) {
        FichaMedicaConsolidada ficha = fichaRepository
                .findById(id).orElseThrow(() ->
                        new RecursoNoEncontradoException("No existe la ficha médica con ID " + id)
                );
        return fichaMapper.toResponse(ficha);
    }

    @Transactional(readOnly = true)
    @Override
    public List<FichaMedicaResponse> listarPorPaciente(Long pacienteId) {
        return fichaRepository.findByPacienteId(pacienteId)
                .stream()
                .map(fichaMapper::toResponse)
                .toList();
    }

    @Transactional
    @Override
    public FichaMedicaResponse anular(Long id) {
        FichaMedicaConsolidada ficha = fichaRepository
                .findById(id).orElseThrow(() ->
                        new RecursoNoEncontradoException("No existe la ficha médica con ID " + id)
                );
        ficha.anular();
        return fichaMapper.toResponse(fichaRepository.save(ficha));
    }

    @Transactional(readOnly = true)
    @Override
    public byte[] generarPdf(Long id) {
        FichaMedicaConsolidada ficha = fichaRepository
                .findById(id).orElseThrow(() ->
                        new RecursoNoEncontradoException("No existe la ficha médica con ID " + id)
                );

        if (ficha.getEstado() != EstadoFicha.GENERADA) {
            throw new ConflictoCitaException("No se puede descargar una ficha anulada");
        }
        List<DetalleConsultaFichaResponse> consultas = consultaRepository
                .findByCitaPacienteId(ficha.getPacienteId())
                .stream()
                .map(consulta ->
                        new DetalleConsultaFichaResponse(
                                consulta.getId(),
                                consulta.getCita().getId(),
                                consulta.getProfesionalId(),
                                consulta.getFechaAtencion(),
                                consulta.getDiagnostico(),
                                consulta.getObservaciones()
                        )
                ).toList();
        FichaMedicaContenidoResponse contenido = new FichaMedicaContenidoResponse(
                ficha.getId(),
                ficha.getPacienteId(),
                ficha.getProfesionalId(),
                ficha.getFechaGeneracion(),
                consultas
        );
        return pdfService.generarPdf(contenido);
    }
}
