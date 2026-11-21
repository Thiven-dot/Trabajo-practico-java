package app;

import java.util.HashMap;
import java.util.Map;

import gestionbiblioteca.FrameworkException;
import gestionbiblioteca.Rol;


public class ServicioUsuarios {
    private final Map<String, Usuario> usuarios = new HashMap<>();

    public ServicioUsuarios() {
        agregar(new Usuario("admin", "1234", Rol.ADMIN));     
        agregar(new Usuario("socio1", "1234", Rol.USUARIO));
        agregar(new Usuario("socio2", "1234", Rol.USUARIO));
    }

    private void agregar(Usuario u) {
        usuarios.put(u.nombre(), u);
    }

    public Usuario autenticar(String nombre, String clave) {
        Usuario u = usuarios.get(nombre);
        if (u == null || !u.clave().equals(clave)) {
            throw new FrameworkException("Usuario o clave incorrectos");
        }
        return u;
    }

    public boolean existe(String nombre) {
        return usuarios.containsKey(nombre);
    }
}
