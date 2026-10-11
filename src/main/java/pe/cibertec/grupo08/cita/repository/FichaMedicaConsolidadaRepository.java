package pe.cibertec.grupo08.cita.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.cibertec.grupo08.cita.entity.FichaMedicaConsolidada;

import java.util.List;

@Repository
public interface FichaMedicaConsolidadaRepository extends JpaRepository<FichaMedicaConsolidada, Long> {
    List<FichaMedicaConsolidada> findByPacienteId(Long pacienteId);
}
