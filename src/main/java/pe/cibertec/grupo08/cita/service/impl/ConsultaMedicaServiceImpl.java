package pe.cibertec.grupo08.cita.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.cibertec.grupo08.cita.dto.ConsultaMedicaRequest;
import pe.cibertec.grupo08.cita.dto.ConsultaMedicaResponse;
import pe.cibertec.grupo08.cita.entity.Cita;
import pe.cibertec.grupo08.cita.entity.ConsultaMedica;
import pe.cibertec.grupo08.cita.enums.EstadoCita;
import pe.cibertec.grupo08.cita.exception.ConflictoCitaException;
import pe.cibertec.grupo08.cita.exception.RecursoNoEncontradoException;
import pe.cibertec.grupo08.cita.mapper.ConsultaMedicaMapper;
import pe.cibertec.grupo08.cita.repository.CitaRepository;
import pe.cibertec.grupo08.cita.repository.ConsultaMedicaRepository;
import pe.cibertec.grupo08.cita.service.ConsultaMedicaService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultaMedicaServiceImpl implements ConsultaMedicaService {
    private final ConsultaMedicaRepository consultaRepository;
    private final CitaRepository citaRepository;
    private final ConsultaMedicaMapper consultaMapper;

    @Transactional
    @Override
    public ConsultaMedicaResponse registrar(ConsultaMedicaRequest request) {
        Cita cita = citaRepository.findById(request.citaId())
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("No existe la cita con ID " + request.citaId())
                );
        if (cita.getEstado() != EstadoCita.CONFIRMADA) {
            throw new ConflictoCitaException("Solo se puede registrar una consulta para una cita confirmada");
        }
        if (consultaRepository.existsByCitaId(cita.getId())) {
            throw new ConflictoCitaException("La cita ya tiene una consulta médica registrada");
        }
        if (request.fechaAtencion().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha de atención no puede estar en el futuro");
        }
        if (!request.profesionalId().equals(cita.getMedicoId())) {
            throw new ConflictoCitaException("El profesional indicado no coincide con el médico de la cita");
        }
        ConsultaMedica consulta = new ConsultaMedica(
                cita,
                request.fechaAtencion(),
                request.diagnostico(),
                request.observaciones(),
                request.profesionalId()
        );
        ConsultaMedica guardada = consultaRepository.save(consulta);
        cita.marcarComoAtendida();
        citaRepository.save(cita);
        return consultaMapper.toResponse(guardada);
    }

    @Transactional(readOnly = true)
    @Override
    public ConsultaMedicaResponse buscarPorId(Long id) {
        ConsultaMedica consulta = consultaRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("No existe la consulta médica con ID " + id)
                );
        return consultaMapper.toResponse(consulta);
    }

    @Transactional(readOnly = true)
    @Override
    public ConsultaMedicaResponse buscarPorCita(Long citaId) {
        ConsultaMedica consulta = consultaRepository.findByCitaId(citaId)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("No existe una consulta para la cita " + citaId)
                );
        return consultaMapper.toResponse(consulta);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ConsultaMedicaResponse> listarPorPaciente(Long pacienteId) {
        return consultaRepository.findByCitaPacienteId(pacienteId)
                .stream()
                .map(consultaMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ConsultaMedicaResponse> listarPorProfesional(Long profesionalId) {
        return consultaRepository.findByProfesionalId(profesionalId)
                .stream()
                .map(consultaMapper::toResponse)
                .toList();
    }
}
