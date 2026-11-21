package gestionbiblioteca;

public class RecursoNoEncontradoException extends FrameworkException {
    public RecursoNoEncontradoException(String recurso) {
        super("No existe el recurso '" + recurso + "'");
    }
}
