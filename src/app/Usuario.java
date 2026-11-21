package app;

import gestionbiblioteca.Rol;

public class Usuario {
    private final String nombre;
    private final String clave;
    private final Rol rol;

    public Usuario(String nombre, String clave, Rol rol) {
        this.nombre = nombre;
        this.clave = clave;
        this.rol = rol;
    }

    public String nombre() { return nombre; }
    public String clave() { return clave; }
    public Rol rol() { return rol; }
}
