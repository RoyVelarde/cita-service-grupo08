package pe.cibertec.grupo08.cita.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "copagos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Copago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cita_id", nullable = false, unique = true)
    private Cita cita;

    @Column(name = "costo_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal costoTotal;

    @Column(name = "monto_cubierto", nullable = false,precision = 12, scale = 2)
    private BigDecimal montoCubierto;

    @Column(name = "monto_paciente", nullable = false,precision = 12, scale = 2)
    private BigDecimal montoPaciente;

    @Column(nullable = false, length = 3)
    private String moneda;

    @Column(name = "fecha_calculo", nullable = false)
    private LocalDateTime fechaCalculo;
}
