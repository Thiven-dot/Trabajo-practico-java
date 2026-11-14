package app;

import gestionbiblioteca.AccionNoEncontradaException;
import gestionbiblioteca.SolicitudInvalidaException;
import java.util.ArrayList;
import java.util.List;

public class ControladorLibros {
    private List<Libro> libros;

    public ControladorLibros() {
        this.libros = new ArrayList<>();
        libros.add(new Libro("1", "Programacion en Java", "Juan Perez"));
        libros.add(new Libro("2", "Estructuras de Datos", "Maria Gomez"));
    }

    public String procesarAccion(String accion, String parametro) {
        if (accion == null) {
            throw new SolicitudInvalidaException("La acción no puede ser nula.");
        }

        switch (accion.toLowerCase()) {
            case "listar":
                return listarLibros();
            case "buscar":
                return buscarLibro(parametro);
            case "alta":
                return darDeAltaLibro(parametro);
            default:
                throw new AccionNoEncontradaException("La acción '" + accion + "' no existe en el controlador de libros.");
        }
    }

    private String listarLibros() {
        if (libros.isEmpty()) {
            return "No hay libros registrados en el sistema.";
        }
        StringBuilder resultado = new StringBuilder("Lista de libros:\n");
        for (Libro libro : libros) {
            resultado.append("- ").append(libro.toString()).append("\n");
        }
        return resultado.toString();
    }

    private String buscarLibro(String parametro) {
        if (parametro == null || parametro.trim().isEmpty()) {
            throw new SolicitudInvalidaException("Debe proporcionar un parámetro de búsqueda (ID o Título).");
        }

        for (Libro libro : libros) {
            if (libro.getId().equalsIgnoreCase(parametro) || libro.getTitulo().equalsIgnoreCase(parametro)) {
                return "Libro encontrado: " + libro.toString();
            }
        }
        return "No se encontró ningún libro con el criterio: " + parametro;
    }

    private String darDeAltaLibro(String parametro) {
        if (parametro == null || parametro.trim().isEmpty()) {
            throw new SolicitudInvalidaException("El parametro para dar de alta esta vacio, informacion necesaria: id,titulo,autor");
        }

        String[] datos = parametro.split(",");
        if (datos.length < 3) {
            throw new SolicitudInvalidaException("Falta informacion, tenes que mandar: id,titulo,autor");
        }

        Libro nuevoLibro = new Libro(datos[0].trim(), datos[1].trim(), datos[2].trim());
        libros.add(nuevoLibro);
        return "Libro agregado exitosamente: " + nuevoLibro.toString();
    }
}
