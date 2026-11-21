package gestionbiblioteca;

import java.util.HashMap;
import java.util.Map;

public class Servicios {
    private final Map<Class<?>, Object> datos = new HashMap<>();

    public <T> void registrar(Class<T> tipo, T servicio) {
        datos.put(tipo, servicio);
    }

    public <T> T obtener(Class<T> tipo) {
        Object servicio = datos.get(tipo);
        if (servicio == null) {
            throw new FrameworkException("Servicio no registrado");
        }
        return tipo.cast(servicio);
    }
}
