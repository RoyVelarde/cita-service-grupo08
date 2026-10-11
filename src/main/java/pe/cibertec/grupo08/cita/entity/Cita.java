package pe.cibertec.grupo08.cita.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.cibertec.grupo08.cita.enums.EstadoCita;
import pe.cibertec.grupo08.cita.enums.ModalidadCita;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "citas",
        indexes = {
                @Index(name = "idx_cita_paciente", columnList = "paciente_id"),
                @Index(name = "idx_cita_medico_fecha", columnList = "medico_id, fecha_hora")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @Column(name = "medico_id", nullable = false)
    private Long medicoId;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ModalidadCita modalidad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoCita estado;

    @Column(nullable = false, length = 300)
    private String motivo;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void reprogramar(LocalDateTime nuevaFechaHora) {
        this.fechaHora = nuevaFechaHora;
    }

    public void cambiarModalidad(ModalidadCita modalidad) {
        this.modalidad = modalidad;
    }

    public void cambiarMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void confirmar() {
        if (this.estado != EstadoCita.PENDIENTE) {
            throw new IllegalStateException("Solo se pueden confirmar citas pendientes");
        }
        this.estado = EstadoCita.CONFIRMADA;
    }

    public void cancelar() {
        if (this.estado == EstadoCita.ATENDIDA || this.estado == EstadoCita.CANCELADA) {
            throw new IllegalStateException("No se puede cancelar una cita atendida o cancelada");
        }

        this.estado = EstadoCita.CANCELADA;
    }

    public void marcarComoAtendida() {
        if (this.estado != EstadoCita.CONFIRMADA) {
            throw new IllegalStateException("Solo se pueden atender citas confirmadas");
        }
        this.estado = EstadoCita.ATENDIDA;
    }
}