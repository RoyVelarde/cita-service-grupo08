package pe.cibertec.grupo08.cita.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.cibertec.grupo08.cita.enums.EstadoFicha;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "fichas_medicas_consolidadas",
        indexes = {
                @Index(name = "idx_ficha_paciente", columnList = "paciente_id")
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FichaMedicaConsolidada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @Column(name = "profesional_id", nullable = false)
    private Long profesionalId;

    @Column(name = "fecha_generacion", nullable = false)
    private LocalDateTime fechaGeneracion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoFicha estado;

    @Column(name = "referencia_documento", length = 500)
    private String referenciaDocumento;
}
