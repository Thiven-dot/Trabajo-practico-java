package app;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import gestionbiblioteca.Controlador;
import gestionbiblioteca.Rol;
import gestionbiblioteca.Solicitud;

public class ControladorUsuarios implements Controlador {
    private final ServicioUsuarios usuarios;

    public ControladorUsuarios(ServicioUsuarios usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public String recurso() { return "usuarios"; }

    @Override
    public Map<String, Function<Solicitud, String>> acciones() {
        Map<String, Function<Solicitud, String>> acciones = new HashMap<>();
        acciones.put("login", this::login);
        acciones.put("logout", this::logout);
        return acciones;
    }

    @Override
    public Map<String, Rol> permisos() {
        Map<String, Rol> permisos = new HashMap<>();
        permisos.put("logout", Rol.USUARIO);  
        return permisos;
    }

    private String login(Solicitud s) {
        String nombre = s.parametro(0, "nombre");
        String clave = s.parametro(1, "clave");
        Usuario u = usuarios.autenticar(nombre, clave);
        s.sesion().iniciar(u.nombre(), u.rol());   
        return "Hola, " + u.nombre();
    }

    private String logout(Solicitud s) {
        s.sesion().cerrar();
        return "Sesion cerrada";
    }
}
