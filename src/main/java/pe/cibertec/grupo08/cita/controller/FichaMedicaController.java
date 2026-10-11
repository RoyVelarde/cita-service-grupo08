package pe.cibertec.grupo08.cita.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.grupo08.cita.dto.FichaMedicaRequest;
import pe.cibertec.grupo08.cita.dto.FichaMedicaResponse;
import pe.cibertec.grupo08.cita.service.FichaMedicaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fichas-medicas")
@RequiredArgsConstructor
public class FichaMedicaController {
    private final FichaMedicaService fichaService;

    @PostMapping
    public ResponseEntity<FichaMedicaResponse> generar(@Valid @RequestBody FichaMedicaRequest request) {
        return ResponseEntity.status(201).body(fichaService.generar(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FichaMedicaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(fichaService.buscarPorId(id));
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<FichaMedicaResponse>> listarPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(fichaService.listarPorPaciente(pacienteId));
    }

    @PatchMapping("/{id}/anulacion")
    public ResponseEntity<FichaMedicaResponse> anular(@PathVariable Long id) {
        return ResponseEntity.ok(fichaService.anular(id));
    }

    @GetMapping("/{id}/documento")
    public ResponseEntity<byte[]> descargarPdf(@PathVariable Long id) {
        byte[] pdf = fichaService.generarPdf(id);
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"ficha-medica-" + id + ".pdf\""
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
