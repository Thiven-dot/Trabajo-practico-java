package app;

import java.io.IOException;

import gestionbiblioteca.Router;
import gestionbiblioteca.Servidor;

public class Main {
    public static void main(String[] args) throws IOException {
        int puerto = args.length > 0 ? Integer.parseInt(args[0]) : 3000;

        Router router = new Router();
        router.registrar("libros", new ControladorLibros());

        new Servidor(puerto, router).iniciar();
    }
}
