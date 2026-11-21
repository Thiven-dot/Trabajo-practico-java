package gestionbiblioteca;

import java.util.ArrayList;
import java.util.List;

public class ParserSolicitudes {

    public static Solicitud parsear(String texto, Sesion sesion) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new SolicitudInvalidaException("Solicitud vacia");
        }
        String[] partes = texto.trim().split("/");
        if (partes.length < 2 || partes[0].trim().isEmpty() || partes[1].trim().isEmpty()) {
            throw new SolicitudInvalidaException("Use recurso/accion/param1/param2");
        }
        List<String> parametros = new ArrayList<>();
        for (int i = 2; i < partes.length; i++) {
            parametros.add(partes[i].trim());
        }
        return new Solicitud(partes[0].trim(), partes[1].trim(), parametros, sesion);
    }
}

