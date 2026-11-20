package app;

import gestionbiblioteca.AccionNoEncontradaException;
import gestionbiblioteca.Rol;
import gestionbiblioteca.Sesion;
import gestionbiblioteca.SolicitudInvalidaException;
import gestionbiblioteca.Validador;
import java.util.List;

public class ControladorUsuarios {

    private record UsuarioRegistrado(String usuario, String password, Rol rol) {
    }

    private final Sesion sesion;
    private final List<UsuarioRegistrado> usuarios;

    public ControladorUsuarios(Sesion sesion) {
        this.sesion = sesion;
        // Usuarios cargados a mano en el código
        this.usuarios = List.of(
            new UsuarioRegistrado("socio1", "1234", Rol.SOCIO),
            new UsuarioRegistrado("biblio1", "admin123", Rol.BIBLIOTECARIO)
        );
    }

    public String procesar(String accion, String parametro) {
        Validador.validarNoVacio(accion, "accion");

        return switch (accion.toLowerCase()) {
            case "login" -> login(parametro);
            default -> throw new AccionNoEncontradaException("Acción no encontrada en usuarios: " + accion);
        };
    }

    private String login(String parametro) {
        Validador.validarNoVacio(parametro, "credenciales");

        String[] partes = parametro.split(",", 2);
        if (partes.length < 2) {
            throw new SolicitudInvalidaException("Formato inválido. Use: usuarios/login/usuario,password");
        }

        String usuarioIngresado = partes[0].trim();
        String passwordIngresado = partes[1].trim();

        Validador.validarNoVacio(usuarioIngresado, "usuario");
        Validador.validarLargoMaximo(usuarioIngresado, 20, "usuario");
        Validador.validarNoVacio(passwordIngresado, "password");

        UsuarioRegistrado encontrado = usuarios.stream()
            .filter(u -> u.usuario().equals(usuarioIngresado) && u.password().equals(passwordIngresado))
            .findFirst()
            .orElseThrow(() -> new SolicitudInvalidaException("Usuario o contraseña incorrectos."));

        sesion.iniciarSesion(encontrado.usuario(), encontrado.rol());
        return "Login correcto. Usuario: " + encontrado.usuario() + " | Rol: " + encontrado.rol();
    }
}