package app;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import gestionbiblioteca.FrameworkException;

public class ServicioLibros {
    private final List<Libro> libros = new ArrayList<>();

    private Libro buscarPorCodigo(String codigo) {
        for (Libro l : libros) {
            if (l.getCodigo().equals(codigo)) {
                return l;
            }
        }
        throw new FrameworkException("No existe el libro " + codigo);
    }

    public synchronized String agregar(String codigo, String titulo, int año) {
        for (Libro l : libros) {
            if (l.getCodigo().equals(codigo)) {
                throw new FrameworkException("Ya existe el libro " + codigo);
            }
        }
        libros.add(new Libro(codigo, titulo, año));
        return "Libro agregado: " + codigo;
    }

    public synchronized String quitar(String codigo) {
        Libro l = buscarPorCodigo(codigo);
        if (l.estaPrestado()) {
            throw new FrameworkException("No se puede quitar: el libro esta prestado");
        }
        libros.remove(l);
        return "Libro quitado: " + codigo;
    }

    public synchronized String listar() {
        if (libros.isEmpty()) {
            return "No hay libros";
        }
        return libros.stream().map(Libro::toString).collect(Collectors.joining("; "));
    }

    public synchronized String buscar(String texto) {
        String resultado = libros.stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(texto.toLowerCase()))
                .map(Libro::toString)
                .collect(Collectors.joining("; "));
        return resultado.isEmpty() ? "Sin resultados" : resultado;
    }

    public synchronized String prestar(String codigo, String socio, int dias) {
        Libro l = buscarPorCodigo(codigo);
        if (l.estaPrestado()) {
            throw new FrameworkException("El libro ya esta prestado a " + l.getPrestadoA());
        }
        l.prestar(socio, dias);
        return "Prestamo registrado: " + codigo + " a " + socio + " por " + dias + " dias";
    }

    public synchronized String devolver(String codigo) {
        Libro l = buscarPorCodigo(codigo);
        if (!l.estaPrestado()) {
            throw new FrameworkException("El libro no esta prestado");
        }
        l.devolver();
        return "Libro devuelto: " + codigo;
    }

    public synchronized String prestamosDe(String socio) {
        String resultado = libros.stream()
                .filter(l -> socio.equals(l.getPrestadoA()))
                .map(Libro::toString)
                .collect(Collectors.joining("; "));
        return resultado.isEmpty() ? "No tiene prestamos" : resultado;
    }
}
