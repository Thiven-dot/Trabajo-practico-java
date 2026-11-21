package gestionbiblioteca;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


public class Router {
    private final Map<String, Controlador> controladores = new HashMap<>();

    public void registrar(Controlador controlador) {
        controladores.put(controlador.recurso(), controlador);
    }

    public String despachar(Solicitud solicitud) {
        Controlador controlador = controladores.get(solicitud.recurso());
        if (controlador == null) {
            throw new RecursoNoEncontradoException(solicitud.recurso());
        }
        Function<Solicitud, String> accion = controlador.acciones().get(solicitud.accion());
        if (accion == null) {
            throw new AccionNoEncontradaException(solicitud.recurso(), solicitud.accion());
        }
        Rol requerido = controlador.permisos().get(solicitud.accion());
        if (requerido != null) {
            solicitud.sesion().verificar(requerido);   
        }
        return accion.apply(solicitud);
    }
}

