package app;

import java.io.IOException;

import gestionbiblioteca.Router;
import gestionbiblioteca.Servicios;
import gestionbiblioteca.Servidor;

public class Main {
    public static void main(String[] args) throws IOException {
        int puerto = args.length > 0 ? Integer.parseInt(args[0]) : 3000;

        Servicios servicios = new Servicios();
        servicios.registrar(ServicioLibros.class, new ServicioLibros());
        servicios.registrar(ServicioUsuarios.class, new ServicioUsuarios());
        ServicioLibros libros = servicios.obtener(ServicioLibros.class);
        ServicioUsuarios usuarios = servicios.obtener(ServicioUsuarios.class);

        Router router = new Router();
        router.registrar(new ControladorUsuarios(usuarios));
        router.registrar(new ControladorLibros(libros));
        router.registrar(new ControladorPrestamos(libros, usuarios));

        new Servidor(puerto, router).iniciar();
    }
}
