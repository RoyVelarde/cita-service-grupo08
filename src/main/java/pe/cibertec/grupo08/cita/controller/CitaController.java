package pe.cibertec.grupo08.cita.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.grupo08.cita.dto.ActualizarMotivoRequest;
import pe.cibertec.grupo08.cita.dto.CitaRequest;
import pe.cibertec.grupo08.cita.dto.CitaResponse;
import pe.cibertec.grupo08.cita.dto.ReprogramarCitaRequest;
import pe.cibertec.grupo08.cita.service.CitaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/citas")
@Validated
@RequiredArgsConstructor
public class CitaController {
    private final CitaService citaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CitaResponse registrar(@Valid @RequestBody CitaRequest request) {
        return citaService.registrar(request);
    }

    @PatchMapping("/{id}/reprogramacion")
    public ResponseEntity<CitaResponse> reprogramar(@PathVariable Long id, @Valid @RequestBody ReprogramarCitaRequest request) {
        return ResponseEntity.ok(citaService.reprogramar(id, request));
    }

    @PatchMapping("/{id}/motivo")
    public ResponseEntity<CitaResponse> actualizarMotivo(@PathVariable Long id, @Valid @RequestBody ActualizarMotivoRequest request) {
        return ResponseEntity.ok(citaService.actualizarMotivo(id, request));
    }

    @GetMapping
    public List<CitaResponse> listarTodas() {
        return citaService.listarTodas();
    }

    @GetMapping("/{id}")
    public CitaResponse buscarPorId(@PathVariable @Positive Long id) {
        return citaService.buscarPorId(id);
    }

    @GetMapping("/paciente/{pacienteId}")
    public List<CitaResponse> listarPorPaciente(@PathVariable @Positive Long pacienteId) {
        return citaService.listarPorPaciente(pacienteId);
    }

    @GetMapping("/medico/{medicoId}")
    public List<CitaResponse> listarPorMedico(@PathVariable @Positive Long medicoId) {
        return citaService.listarPorMedico(medicoId);
    }

    @PatchMapping("/{id}/confirmacion")
    public CitaResponse confirmar(@PathVariable @Positive Long id) {
        return citaService.confirmar(id);
    }

    @PatchMapping("/{id}/cancelacion")
    public CitaResponse cancelar(@PathVariable @Positive Long id) {
        return citaService.cancelar(id);
    }
}
