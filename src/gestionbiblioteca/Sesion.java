package gestionbiblioteca;

import java.util.HashMap;
import java.util.Map;

public class Sesion {
    private final Map<String, Object> datos = new HashMap<>();

    public void guardar(String clave, Object valor) { datos.put(clave, valor); }
    public Object obtener(String clave) { return datos.get(clave); }
}
