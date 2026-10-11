package pe.cibertec.grupo08.cita.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.cibertec.grupo08.cita.entity.Copago;

import java.util.Optional;

@Repository
public interface CopagoRepository extends JpaRepository<Copago, Long> {
    Optional<Copago> findByCitaId(Long citaId);

    boolean existsByCitaId(Long citaId);
}