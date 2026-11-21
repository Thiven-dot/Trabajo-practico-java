package app;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import gestionbiblioteca.Controlador;
import gestionbiblioteca.Rol;
import gestionbiblioteca.Solicitud;
import gestionbiblioteca.Validadores;

public class ControladorLibros implements Controlador {
    private final ServicioLibros libros;

    public ControladorLibros(ServicioLibros libros) {
        this.libros = libros;
    }

    @Override
    public String recurso() { return "libros"; }

    @Override
    public Map<String, Function<Solicitud, String>> acciones() {
        Map<String, Function<Solicitud, String>> acciones = new HashMap<>();
        acciones.put("listar", s -> libros.listar());
        acciones.put("buscar", s -> libros.buscar(s.parametro(0, "texto")));
        acciones.put("agregar", this::agregar);
        acciones.put("quitar", s -> libros.quitar(s.parametro(0, "codigo")));
        return acciones;
    }

    @Override
    public Map<String, Rol> permisos() {
        Map<String, Rol> permisos = new HashMap<>();
        permisos.put("listar", Rol.USUARIO);
        permisos.put("buscar", Rol.USUARIO);
        permisos.put("agregar", Rol.ADMIN);
        permisos.put("quitar", Rol.ADMIN);
        return permisos;
    }

    private String agregar(Solicitud s) {
        String codigo = s.parametro(0, "codigo", Validadores.largoMaximo(10));
        String titulo = s.parametro(1, "titulo", Validadores.largoMaximo(60));
        String año = s.parametro(2, "año", Validadores.enteroPositivo());
        return libros.agregar(codigo, titulo, Integer.parseInt(año));
    }
}
