package gestionbiblioteca;

import java.util.List;

public class Solicitud {
    private final String recurso;
    private final String accion;
    private final List<String> parametros;
    private final Sesion sesion;

    public Solicitud(String recurso, String accion, List<String> parametros, Sesion sesion) {
        this.recurso = recurso;
        this.accion = accion;
        this.parametros = parametros;
        this.sesion = sesion;
    }

    public String recurso() { return recurso; }
    public String accion() { return accion; }
    public List<String> parametros() { return parametros; }
    public Sesion sesion() { return sesion; }

    public String parametro(int indice, String nombre) {
        if (indice < 0 || indice >= parametros.size() || parametros.get(indice).trim().isEmpty()) {
            throw new SolicitudInvalidaException("Falta " + nombre);
        }
        return parametros.get(indice);
    }


    public String parametro(int indice, String nombre, Validador validador) {
        String valor = parametro(indice, nombre);
        validador.validar(nombre, valor);
        return valor;
    }
}
