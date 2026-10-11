package pe.cibertec.grupo08.cita.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultas_medicas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaMedica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cita_id", nullable = false, unique = true)
    private Cita cita;

    @Column(name = "fecha_atencion", nullable = false)
    private LocalDateTime fechaAtencion;

    @Column(length = 1000)
    private String diagnostico;

    @Column(length = 2000)
    private String observaciones;

    @Column(name = "profesional_id", nullable = false)
    private Long profesionalId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public ConsultaMedica(
            Cita cita,
            LocalDateTime fechaAtencion,
            String diagnostico,
            String observaciones,
            Long profesionalId
    ) {
        this.cita = cita;
        this.fechaAtencion = fechaAtencion;
        this.diagnostico = diagnostico;
        this.observaciones = observaciones;
        this.profesionalId = profesionalId;
        this.createdAt = LocalDateTime.now();
    }

    @PrePersist
    protected void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
