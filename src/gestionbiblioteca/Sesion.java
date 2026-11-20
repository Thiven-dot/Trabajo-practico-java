package gestionbiblioteca;

public class Sesion {
    private String usuario;
    private Rol rol;

    public void iniciarSesion(String usuario, Rol rol) {
        this.usuario = usuario;
        this.rol = rol;
    }

    public void cerrarSesion() {
        this.usuario = null;
        this.rol = null;
    }

    public boolean estaAutenticada() {
        return usuario != null && rol != null;
    }

    public boolean esBibliotecario() {
        return estaAutenticada() && rol == Rol.BIBLIOTECARIO;
    }

    public String getUsuario() {
        return usuario;
    }

    public Rol getRol() {
        return rol;
    }
}