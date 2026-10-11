package pe.cibertec.grupo08.cita.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/citas")
@RefreshScope
public class CitaConfiguracionController {
    @Value("${salud.monto-copago-base}")
    private Double montoCopagoBase;

    @Value("${salud.descuento-eps}")
    private Double descuentoEps;

    @Value("${salud.mensaje-bienvenida}")
    private String mensajeBienvenida;

    @Value("${salud.api-key-aseguradora}")
    private String apiKeyAseguradora;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    @GetMapping("/configuracion")
    public Map<String, Object> obtenerConfiguracion() {
        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("monto-copago-base", montoCopagoBase);
        respuesta.put("descuento-eps", descuentoEps);
        respuesta.put("mensaje-bienvenida", mensajeBienvenida);
        respuesta.put("api-key-aseguradora", apiKeyAseguradora);
        respuesta.put("datasource.password", datasourcePassword);
        return respuesta;
    }
}