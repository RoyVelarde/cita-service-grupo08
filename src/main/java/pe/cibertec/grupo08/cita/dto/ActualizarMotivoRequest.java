package pe.cibertec.grupo08.cita.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ActualizarMotivoRequest(
        @NotBlank(message = "El motivo es obligatorio")
        @Size(max = 300, message = "El motivo no puede superar los 300 caracteres")
        String motivo
) {
}