package pe.cibertec.grupo08.cita.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    public Cita(Long pacienteId, Long medicoId, LocalDateTime fechaHora, ModalidadCita modalidad, String motivo) {
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.fechaHora = fechaHora;
        this.modalidad = modalidad;
        this.motivo = motivo;
        this.estado = EstadoCita.PENDIENTE;
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void reprogramar(LocalDateTime nuevaFechaHora) {
        if (nuevaFechaHora == null || !nuevaFechaHora.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("La nueva fecha y hora deben estar en el futuro");
        }
        if (estado == EstadoCita.ATENDIDA || estado == EstadoCita.CANCELADA) {
            throw new IllegalStateException("No se puede reprogramar una cita atendida o cancelada");
        }
        this.fechaHora = nuevaFechaHora;
    }

    public void cambiarModalidad(ModalidadCita nuevaModalidad) {
        if (nuevaModalidad == null) {
            throw new IllegalArgumentException("La modalidad es obligatoria");
        }
        if (estado == EstadoCita.ATENDIDA || estado == EstadoCita.CANCELADA) {
            throw new IllegalStateException("No se puede cambiar la modalidad de una cita atendida o cancelada");
        }
        this.modalidad = nuevaModalidad;
    }

    public void cambiarMotivo(String nuevoMotivo) {
        if (nuevoMotivo == null || nuevoMotivo.isBlank()) {
            throw new IllegalArgumentException("El motivo es obligatorio");
        }
        if (nuevoMotivo.length() > 300) {
            throw new IllegalArgumentException("El motivo no puede superar los 300 caracteres");
        }
        if (estado == EstadoCita.ATENDIDA || estado == EstadoCita.CANCELADA) {
            throw new IllegalStateException("No se puede modificar una cita atendida o cancelada");
        }
        this.motivo = nuevoMotivo;
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