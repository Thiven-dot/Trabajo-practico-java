package gestionbiblioteca;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public interface Controlador {
    String recurso();

    Map<String, Function<Solicitud, String>> acciones();


    default Map<String, Rol> permisos() {
        return new HashMap<>();
    }
}
