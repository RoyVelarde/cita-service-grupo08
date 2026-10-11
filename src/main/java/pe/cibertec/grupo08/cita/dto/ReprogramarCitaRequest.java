package pe.cibertec.grupo08.cita.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pe.cibertec.grupo08.cita.enums.ModalidadCita;

import java.time.LocalDateTime;

public record ReprogramarCitaRequest(
        @NotNull(message = "La fecha y hora son obligatorias")
        @Future(message = "La nueva fecha debe estar en el futuro")
        LocalDateTime fechaHora,

        @NotNull(message = "La modalidad es obligatoria")
        ModalidadCita modalidad,

        @Size(max = 300, message = "El motivo no puede superar los 300 caracteres")
        String motivo
) {
}
