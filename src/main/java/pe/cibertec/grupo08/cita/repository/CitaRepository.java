package pe.cibertec.grupo08.cita.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.cibertec.grupo08.cita.entity.Cita;
import pe.cibertec.grupo08.cita.enums.EstadoCita;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByPacienteId(Long pacienteId);

    List<Cita> findByMedicoId(Long medicoId);

    List<Cita> findByEstado(EstadoCita estado);

    boolean existsByMedicoIdAndFechaHoraAndEstadoIn(Long medicoId, LocalDateTime fechaHora, List<EstadoCita> estados);

    boolean existsByMedicoIdAndFechaHoraAndEstadoInAndIdNot(Long medicoId, LocalDateTime fechaHora,List<EstadoCita> estados, Long id);
}
