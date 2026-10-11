package pe.cibertec.grupo08.cita.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.cibertec.grupo08.cita.dto.CopagoRequest;
import pe.cibertec.grupo08.cita.dto.CopagoResponse;
import pe.cibertec.grupo08.cita.entity.Cita;
import pe.cibertec.grupo08.cita.entity.Copago;
import pe.cibertec.grupo08.cita.enums.EstadoCita;
import pe.cibertec.grupo08.cita.exception.ConflictoCitaException;
import pe.cibertec.grupo08.cita.exception.RecursoNoEncontradoException;
import pe.cibertec.grupo08.cita.mapper.CopagoMapper;
import pe.cibertec.grupo08.cita.repository.CitaRepository;
import pe.cibertec.grupo08.cita.repository.CopagoRepository;
import pe.cibertec.grupo08.cita.service.CopagoService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CopagoServiceImpl implements CopagoService {
    private final CopagoRepository copagoRepository;
    private final CitaRepository citaRepository;
    private final CopagoMapper copagoMapper;

    @Transactional
    @Override
    public CopagoResponse calcular(CopagoRequest request) {
        Cita cita = citaRepository.findById(request.citaId())
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("No existe la cita con ID " + request.citaId())
                );
        if (cita.getEstado() != EstadoCita.ATENDIDA) {
            throw new ConflictoCitaException("Solo se puede calcular el copago de una cita atendida");
        }
        if (copagoRepository.existsByCitaId(cita.getId())) {
            throw new ConflictoCitaException("La cita ya tiene un copago calculado");
        }
        Copago copago = new Copago(
                cita,
                request.costoTotal(),
                request.montoCubierto(),
                request.moneda()
        );
        Copago guardado = copagoRepository.save(copago);
        return copagoMapper.toResponse(guardado);
    }

    @Transactional(readOnly = true)
    @Override
    public CopagoResponse buscarPorId(Long id) {
        Copago copago = copagoRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("No existe el copago con ID " + id)
                );
        return copagoMapper.toResponse(copago);
    }

    @Transactional(readOnly = true)
    @Override
    public CopagoResponse buscarPorCita(Long citaId) {
        Copago copago = copagoRepository.findByCitaId(citaId)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("No existe un copago para la cita " + citaId)
                );
        return copagoMapper.toResponse(copago);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CopagoResponse> listarPorPaciente(Long pacienteId) {
        return copagoRepository.findByCitaPacienteId(pacienteId)
                .stream()
                .map(copagoMapper::toResponse)
                .toList();
    }
}
