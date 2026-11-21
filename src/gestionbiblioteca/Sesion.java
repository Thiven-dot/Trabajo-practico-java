package gestionbiblioteca;

public class Sesion {
    private String usuario;
    private Rol rol;

    public void iniciar(String usuario, Rol rol) {
        this.usuario = usuario;
        this.rol = rol;
    }

    public void cerrar() {
        usuario = null;
        rol = null;
    }

    public boolean estaLogueado() { return usuario != null; }
    public String usuario() { return usuario; }

    public void verificar(Rol requerido) {
        if (!estaLogueado()) {
            throw new FrameworkException("Inicie sesion");
        }
        if (!rol.puedeHacer(requerido)) {
            throw new FrameworkException("Permiso insuficiente");
        }
    }
}
