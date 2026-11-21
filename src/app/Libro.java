package app;

public class Libro {
    private final String codigo;
    private final String titulo;
    private final int año;
    private String prestadoA;  
    private int dias;

    public Libro(String codigo, String titulo, int año) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.año = año;
    }

    public String getCodigo() { return codigo; }
    public String getTitulo() { return titulo; }
    public String getPrestadoA() { return prestadoA; }
    public boolean estaPrestado() { return prestadoA != null; }

    public void prestar(String socio, int dias) {
        this.prestadoA = socio;
        this.dias = dias;
    }

    public void devolver() {
        this.prestadoA = null;
        this.dias = 0;
    }

    @Override
    public String toString() {
        String estado = estaPrestado() ? "prestado a " + prestadoA + " por " + dias + " dias" : "disponible";
        return codigo + " - " + titulo + " (" + año + ") [" + estado + "]";
    }
}
