package pe.cibertec.grupo08.cita.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import pe.cibertec.grupo08.cita.dto.ActualizarMotivoRequest;
import pe.cibertec.grupo08.cita.dto.CitaRequest;
import pe.cibertec.grupo08.cita.dto.CitaResponse;
import pe.cibertec.grupo08.cita.dto.ReprogramarCitaRequest;
import pe.cibertec.grupo08.cita.entity.Cita;
import pe.cibertec.grupo08.cita.enums.EstadoCita;
import pe.cibertec.grupo08.cita.exception.ConflictoCitaException;
import pe.cibertec.grupo08.cita.exception.RecursoNoEncontradoException;
import pe.cibertec.grupo08.cita.mapper.CitaMapper;
import pe.cibertec.grupo08.cita.repository.CitaRepository;
import pe.cibertec.grupo08.cita.service.CitaService;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CitaServiceImpl implements CitaService {
    private final CitaRepository citaRepository;
    private final CitaMapper citaMapper;

    @Transactional
    @Override
    public CitaResponse registrar(CitaRequest request) {
        boolean horarioOcupado = citaRepository.existsByMedicoIdAndFechaHoraAndEstadoIn(
                request.medicoId(),
                request.fechaHora(),
                List.of(EstadoCita.PENDIENTE, EstadoCita.CONFIRMADA)
        );
        if (horarioOcupado) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El médico ya tiene una cita en esa fecha y hora");
        }
        Cita cita = citaMapper.toEntity(request);
        Cita citaGuardada = citaRepository.save(cita);
        return citaMapper.toResponse(citaGuardada);
    }

    @Transactional
    @Override
    public CitaResponse reprogramar(Long id, ReprogramarCitaRequest request) {
        Cita cita = obtenerEntidad(id);
        if (cita.getEstado() == EstadoCita.ATENDIDA || cita.getEstado() == EstadoCita.CANCELADA) {
            throw new ConflictoCitaException("No se puede reprogramar una cita atendida o cancelada");
        }
        boolean horarioOcupado = citaRepository.existsByMedicoIdAndFechaHoraAndEstadoInAndIdNot(
                cita.getMedicoId(),
                request.fechaHora(),
                List.of(EstadoCita.PENDIENTE, EstadoCita.CONFIRMADA),
                cita.getId()
        );
        if (horarioOcupado) {
            throw new ConflictoCitaException("El médico ya tiene otra cita en la fecha y hora seleccionadas");
        }
        cita.reprogramar(request.fechaHora());
        cita.cambiarModalidad(request.modalidad());
        if (request.motivo() != null && !request.motivo().isBlank()) {
            cita.cambiarMotivo(request.motivo());
        }
        Cita citaGuardada = citaRepository.save(cita);
        return citaMapper.toResponse(citaGuardada);
    }

    @Transactional
    @Override
    public CitaResponse actualizarMotivo(Long id, ActualizarMotivoRequest request) {
        Cita cita = obtenerEntidad(id);
        cita.cambiarMotivo(request.motivo());
        Cita citaGuardada = citaRepository.save(cita);
        return citaMapper.toResponse(citaGuardada);
    }

    @Override
    public CitaResponse buscarPorId(Long id) {
        return citaMapper.toResponse(obtenerEntidad(id));
    }

    @Override
    public List<CitaResponse> listarTodas() {
        return citaRepository.findAll()
                .stream()
                .map(citaMapper::toResponse)
                .toList();
    }

    @Override
    public List<CitaResponse> listarPorPaciente(Long pacienteId) {
        return citaRepository.findByPacienteId(pacienteId)
                .stream()
                .map(citaMapper::toResponse)
                .toList();
    }

    @Override
    public List<CitaResponse> listarPorMedico(Long medicoId) {
        return citaRepository.findByMedicoId(medicoId)
                .stream()
                .map(citaMapper::toResponse)
                .toList();
    }

    @Transactional
    @Override
    public CitaResponse confirmar(Long id) {
        Cita cita = obtenerEntidad(id);
        cita.confirmar();
        Cita citaActualizada = citaRepository.save(cita);
        return citaMapper.toResponse(citaActualizada);
    }

    @Transactional
    @Override
    public CitaResponse cancelar(Long id) {
        Cita cita = obtenerEntidad(id);
        cita.cancelar();
        Cita citaActualizada = citaRepository.save(cita);
        return citaMapper.toResponse(citaActualizada);
    }

    private Cita obtenerEntidad(Long id) {
        return citaRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("No existe una cita con ID " + id)
        );
    }
}