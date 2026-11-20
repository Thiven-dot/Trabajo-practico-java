package app;

import gestionbiblioteca.AccionNoEncontradaException;
import gestionbiblioteca.RecursoNoEncontradoException;
import gestionbiblioteca.Sesion;
import gestionbiblioteca.SolicitudInvalidaException;
import gestionbiblioteca.Validador;
import java.util.ArrayList;
import java.util.List;

public class ControladorLibros {

    private final List<Libro> libros = new ArrayList<>();
    private final Sesion sesion;

    public ControladorLibros(Sesion sesion) {
        this.sesion = sesion;
        libros.add(new Libro("1", "Clean Code", "Robert C. Martin"));
        libros.add(new Libro("2", "Java Efectivo", "Joshua Bloch"));
    }

    public String procesar(String accion, String parametro) {
        Validador.validarNoVacio(accion, "accion");

        return switch (accion.toLowerCase()) {
            case "agregar" -> agregarLibro(parametro);
            case "quitar" -> quitarLibro(parametro);
            case "listar" -> listarLibros();
            case "buscar" -> buscarLibro(parametro);
            default -> throw new AccionNoEncontradaException("Acción no encontrada en libros: " + accion);
        };
    }

    private void verificarBibliotecario() {
        if (!sesion.estaAutenticada()) {
            throw new SolicitudInvalidaException("Debe iniciar sesión primero (usuarios/login).");
        }
        if (!sesion.esBibliotecario()) {
            throw new SolicitudInvalidaException("Acceso denegado: solo BIBLIOTECARIO puede ejecutar esta acción.");
        }
    }

    // Solo el bibliotecario: libros,agregar,id,titulo,autor
    private String agregarLibro(String parametro) {
        verificarBibliotecario();
        Validador.validarNoVacio(parametro, "datosLibro");

        String[] datos = parametro.split(",", 3);
        if (datos.length < 3) {
            throw new SolicitudInvalidaException("Formato inválido. Use: libros/agregar/id,titulo,autor");
        }

        String id = datos[0].trim();
        String titulo = datos[1].trim();
        String autor = datos[2].trim();

        Validador.validarEnteroPositivo(id, "id");
        Validador.validarLargoMaximo(id, 10, "id");
        Validador.validarNoVacio(titulo, "titulo");
        Validador.validarLargoMaximo(titulo, 100, "titulo");
        Validador.validarNoVacio(autor, "autor");
        Validador.validarLargoMaximo(autor, 80, "autor");

        boolean yaExiste = libros.stream().anyMatch(l -> l.getId().equals(id));
        if (yaExiste) {
            throw new SolicitudInvalidaException("Ya existe un libro con el ID: " + id);
        }

        Libro nuevo = new Libro(id, titulo, autor);
        libros.add(nuevo);
        return "Libro agregado: " + nuevo;
    }

    // Solo BIBLIOTECARIO: libros/quitar/id
    private String quitarLibro(String parametro) {
        verificarBibliotecario();
        int idNumerico = Validador.validarEnteroPositivo(parametro, "id");
        Validador.validarLargoMaximo(parametro, 10, "id");
        String idBuscado = String.valueOf(idNumerico);

        Libro libroAQuitar = libros.stream()
            .filter(l -> l.getId().equals(idBuscado))
            .findFirst()
            .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró un libro con ID: " + idBuscado));

        libros.remove(libroAQuitar);
        return "Libro eliminado: " + libroAQuitar;
    }

    private String listarLibros() {
        if (libros.isEmpty()) {
            return "No hay libros registrados.";
        }
        StringBuilder sb = new StringBuilder("Lista de libros:\n");
        libros.forEach(l -> sb.append("- ").append(l).append("\n"));
        return sb.toString();
    }

    private String buscarLibro(String parametro) {
        Validador.validarNoVacio(parametro, "criterio");
        Validador.validarLargoMaximo(parametro, 100, "criterio");
        String criterio = parametro.trim();

        return libros.stream()
            .filter(l -> l.getId().equalsIgnoreCase(criterio) || l.getTitulo().equalsIgnoreCase(criterio))
            .findFirst()
            .map(l -> "Libro encontrado: " + l)
            .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el libro: " + criterio));
    }
}