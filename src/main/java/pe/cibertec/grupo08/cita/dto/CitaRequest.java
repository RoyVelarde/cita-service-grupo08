package pe.cibertec.grupo08.cita.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import pe.cibertec.grupo08.cita.enums.ModalidadCita;

import java.time.LocalDateTime;

public record CitaRequest(
        @NotNull(message = "El identificador del paciente es obligatorio")
        @Positive(message = "El identificador del paciente debe ser positivo")
        Long pacienteId,

        @NotNull(message = "El identificador del médico es obligatorio")
        @Positive(message = "El identificador del médico debe ser positivo")
        Long medicoId,

        @NotNull(message = "La fecha y hora son obligatorias")
        @Future(message = "La cita debe programarse para una fecha futura")
        LocalDateTime fechaHora,

        @NotNull(message = "La modalidad es obligatoria")
        ModalidadCita modalidad,

        @NotBlank(message = "El motivo de la cita es obligatorio")
        @Size(max = 300, message = "El motivo no puede superar los 300 caracteres")
        String motivo

) {
}
