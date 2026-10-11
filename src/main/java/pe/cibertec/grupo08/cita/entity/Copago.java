package pe.cibertec.grupo08.cita.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Entity
@Table(
        name = "copagos",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_copago_cita", columnNames = "cita_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Copago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cita_id", nullable = false)
    private Cita cita;

    @Column(name = "costo_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal costoTotal;

    @Column(name = "monto_cubierto", nullable = false, precision = 12, scale = 2)
    private BigDecimal montoCubierto;

    @Column(name = "monto_paciente", nullable = false, precision = 12, scale = 2)
    private BigDecimal montoPaciente;

    @Column(nullable = false, length = 3)
    private String moneda;

    @Column(name = "fecha_calculo", nullable = false, updatable = false)
    private LocalDateTime fechaCalculo;

    public Copago(Cita cita,BigDecimal costoTotal, BigDecimal montoCubierto, String moneda) {
        this.cita = cita;
        this.moneda = validarMoneda(moneda);
        this.fechaCalculo = LocalDateTime.now();
        actualizarImportes(costoTotal, montoCubierto);
    }

    public void actualizarImportes(BigDecimal costoTotal, BigDecimal montoCubierto) {
        validarImportes(costoTotal, montoCubierto);
        this.costoTotal = costoTotal;
        this.montoCubierto = montoCubierto;
        this.montoPaciente = costoTotal.subtract(montoCubierto);
    }

    private void validarImportes(BigDecimal costoTotal, BigDecimal montoCubierto) {
        if (costoTotal == null || montoCubierto == null) {
            throw new IllegalArgumentException("El costo total y la cobertura son obligatorios");
        }
        if (costoTotal.signum() < 0 || montoCubierto.signum() < 0) {
            throw new IllegalArgumentException("Los importes no pueden ser negativos");
        }
        if (costoTotal.scale() > 2 || montoCubierto.scale() > 2) {
            throw new IllegalArgumentException("Los importes solo pueden tener dos decimales");
        }
        if (montoCubierto.compareTo(costoTotal) > 0) {
            throw new IllegalArgumentException("La cobertura no puede superar el costo total");
        }
    }

    private String validarMoneda(String moneda) {
        if (moneda == null || moneda.isBlank()) {
            throw new IllegalArgumentException("La moneda es obligatoria");
        }
        try {
            return Currency.getInstance(moneda).getCurrencyCode();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("La moneda debe ser Soles o Dólares");
        }
    }

    @PrePersist
    protected void prePersist() {
        if (fechaCalculo == null) {
            fechaCalculo = LocalDateTime.now();
        }
    }
}
