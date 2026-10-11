package pe.cibertec.grupo08.cita.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.cibertec.grupo08.cita.entity.ConsultaMedica;

import java.util.Optional;

@Repository
public interface ConsultaMedicaRepository extends JpaRepository<ConsultaMedica, Long> {
    Optional<ConsultaMedica> findByCitaId(Long citaId);

    boolean existsByCitaId(Long citaId);
}
