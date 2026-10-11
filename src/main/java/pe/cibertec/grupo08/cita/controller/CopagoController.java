package pe.cibertec.grupo08.cita.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.grupo08.cita.dto.CopagoRequest;
import pe.cibertec.grupo08.cita.dto.CopagoResponse;
import pe.cibertec.grupo08.cita.service.CopagoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/copagos")
@RequiredArgsConstructor
public class CopagoController {
    private final CopagoService copagoService;

    @PostMapping
    public ResponseEntity<CopagoResponse> calcular(@Valid @RequestBody CopagoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(copagoService.calcular(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CopagoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(copagoService.buscarPorId(id));
    }

    @GetMapping("/cita/{citaId}")
    public ResponseEntity<CopagoResponse> buscarPorCita(@PathVariable Long citaId) {
        return ResponseEntity.ok(copagoService.buscarPorCita(citaId));
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<CopagoResponse>> listarPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(copagoService.listarPorPaciente(pacienteId));
    }
}
