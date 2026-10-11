package pe.cibertec.grupo08.cita.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.grupo08.cita.dto.ConsultaMedicaRequest;
import pe.cibertec.grupo08.cita.dto.ConsultaMedicaResponse;
import pe.cibertec.grupo08.cita.service.ConsultaMedicaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/consultas-medicas")
@RequiredArgsConstructor
public class ConsultaMedicaController {
    private final ConsultaMedicaService consultaService;

    @PostMapping
    public ResponseEntity<ConsultaMedicaResponse> registrar(@Valid @RequestBody ConsultaMedicaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(consultaService.registrar(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaMedicaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.buscarPorId(id));
    }

    @GetMapping("/cita/{citaId}")
    public ResponseEntity<ConsultaMedicaResponse> buscarPorCita(@PathVariable Long citaId) {
        return ResponseEntity.ok(consultaService.buscarPorCita(citaId));
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<ConsultaMedicaResponse>> listarPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(consultaService.listarPorPaciente(pacienteId));
    }

    @GetMapping("/profesional/{profesionalId}")
    public ResponseEntity<List<ConsultaMedicaResponse>> listarPorProfesional(@PathVariable Long profesionalId) {
        return ResponseEntity.ok(consultaService.listarPorProfesional(profesionalId));
    }
}
