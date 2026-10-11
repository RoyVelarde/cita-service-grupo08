package pe.cibertec.grupo08.cita.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CopagoRequest(
        @NotNull(message = "El ID de la cita es obligatorio")
        @Positive(message = "El ID de la cita debe ser positivo")
        Long citaId,

        @NotNull(message = "El costo total es obligatorio")
        @DecimalMin(value = "0.00",message = "El costo total no puede ser negativo")
        @Digits(integer = 10,fraction = 2, message = "El costo total admite hasta dos decimales")
        BigDecimal costoTotal,

        @NotNull(message = "El monto cubierto es obligatorio")
        @DecimalMin(value = "0.00", message = "La cobertura no puede ser negativa")
        @Digits(integer = 10, fraction = 2,message = "La cobertura admite hasta dos decimales")
        BigDecimal montoCubierto,

        @NotBlank(message = "La moneda es obligatoria")
        String moneda
) {
}
