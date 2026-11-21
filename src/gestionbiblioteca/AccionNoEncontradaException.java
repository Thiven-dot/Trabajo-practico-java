package gestionbiblioteca;

public class AccionNoEncontradaException extends FrameworkException {
    
    public AccionNoEncontradaException(String recurso, String accion) {
        super("El recurso '" + recurso + "' no tiene la accion '" + accion + "'");
    }
}
