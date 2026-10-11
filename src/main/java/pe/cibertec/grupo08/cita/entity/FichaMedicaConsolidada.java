package pe.cibertec.grupo08.cita.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.cibertec.grupo08.cita.enums.EstadoFicha;

import java.time.LocalDateTime;

@Entity
@Table(name = "fichas_medicas_consolidadas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FichaMedicaConsolidada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @Column(name = "profesional_id", nullable = false)
    private Long profesionalId;

    @Column(name = "fecha_generacion", nullable = false, updatable = false)
    private LocalDateTime fechaGeneracion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoFicha estado;

    @Column(name = "referencia_documento", length = 500)
    private String referenciaDocumento;

    public FichaMedicaConsolidada(
            Long pacienteId,
            Long profesionalId
    ) {
        this.pacienteId = pacienteId;
        this.profesionalId = profesionalId;
        this.fechaGeneracion = LocalDateTime.now();
        this.estado = EstadoFicha.GENERADA;
    }

    public void asignarReferenciaDocumento(String referenciaDocumento) {
        if (referenciaDocumento == null || referenciaDocumento.isBlank()) {
            throw new IllegalArgumentException("La referencia del documento es obligatoria");
        }
        this.referenciaDocumento = referenciaDocumento;
    }

    public void anular() {
        if (this.estado == EstadoFicha.ANULADA) {
            throw new IllegalStateException("La ficha médica ya se encuentra anulada");
        }
        this.estado = EstadoFicha.ANULADA;
    }
}