package app;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import gestionbiblioteca.Controlador;
import gestionbiblioteca.FrameworkException;
import gestionbiblioteca.Rol;
import gestionbiblioteca.Solicitud;
import gestionbiblioteca.Validadores;

public class ControladorPrestamos implements Controlador {
    private final ServicioLibros libros;      
    private final ServicioUsuarios usuarios;

    public ControladorPrestamos(ServicioLibros libros, ServicioUsuarios usuarios) {
        this.libros = libros;
        this.usuarios = usuarios;
    }

    @Override
    public String recurso() { return "prestamos"; }

    @Override
    public Map<String, Function<Solicitud, String>> acciones() {
        Map<String, Function<Solicitud, String>> acciones = new HashMap<>();
        acciones.put("registrar", this::registrar);
        acciones.put("devolver", s -> libros.devolver(s.parametro(0, "codigo")));
        acciones.put("misprestamos", s -> libros.prestamosDe(s.sesion().usuario()));
        return acciones;
    }

    @Override
    public Map<String, Rol> permisos() {
        Map<String, Rol> permisos = new HashMap<>();
        permisos.put("registrar", Rol.ADMIN);
        permisos.put("devolver", Rol.ADMIN);
        permisos.put("misprestamos", Rol.USUARIO);
        return permisos;
    }

    private String registrar(Solicitud s) {
        String codigo = s.parametro(0, "codigo");
        String socio = s.parametro(1, "socio");
        String dias = s.parametro(2, "dias", Validadores.rango(1, 30));
        if (!usuarios.existe(socio)) {
            throw new FrameworkException("No existe el socio " + socio);
        }
        return libros.prestar(codigo, socio, Integer.parseInt(dias));
    }
}
