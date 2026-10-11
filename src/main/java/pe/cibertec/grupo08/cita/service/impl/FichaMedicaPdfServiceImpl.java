package pe.cibertec.grupo08.cita.service.impl;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import pe.cibertec.grupo08.cita.dto.DetalleConsultaFichaResponse;
import pe.cibertec.grupo08.cita.dto.FichaMedicaContenidoResponse;
import pe.cibertec.grupo08.cita.service.FichaMedicaPdfService;

import java.io.ByteArrayOutputStream;

@Service
public class FichaMedicaPdfServiceImpl implements FichaMedicaPdfService {

    @Override
    public byte[] generarPdf(FichaMedicaContenidoResponse ficha) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();
            document.add(new Paragraph("FICHA MEDICA CONSOLIDADA"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Ficha ID: " + ficha.fichaId()));
            document.add(new Paragraph("Paciente ID: " + ficha.pacienteId()));
            document.add(new Paragraph("Profesional generador ID: " + ficha.profesionalGeneradorId()));
            document.add(new Paragraph("Fecha de generación: " + ficha.fechaGeneracion()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("HISTORIAL DE CONSULTAS"));
            document.add(new Paragraph(" "));
            for (DetalleConsultaFichaResponse consulta : ficha.consultas()) {
                document.add(new Paragraph("Consulta ID: " + consulta.consultaId()));
                document.add(new Paragraph("Cita ID: " + consulta.citaId()));
                document.add(new Paragraph("Profesional ID: " + consulta.profesionalId()));
                document.add(new Paragraph("Fecha de atención: " + consulta.fechaAtencion()));
                document.add(new Paragraph("Diagnostico: " + consulta.diagnostico()));
                document.add(new Paragraph(
                        "Observaciones: " + (consulta.observaciones() == null
                                ? "Sin observaciones" : consulta.observaciones()))
                );
                document.add(new Paragraph(" "));
            }
            document.close();
            return outputStream.toByteArray();
        } catch (DocumentException ex) {
            throw new IllegalStateException("No se pudo generar el PDF de la ficha médica", ex);
        } catch (java.io.IOException ex) {
            throw new IllegalStateException("No se pudo cerrar el archivo PDF", ex);
        }
    }
}
