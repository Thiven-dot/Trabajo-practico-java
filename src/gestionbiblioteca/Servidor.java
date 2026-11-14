package gestionbiblioteca;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    private final int puerto;
    private final Router router;

    public Servidor(int puerto, Router router) {
        this.puerto = puerto;
        this.router = router;
    }

    public void iniciar() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor escuchando en el puerto " + puerto);
            int contador = 0;
            while (true) {
                Socket socket = serverSocket.accept();
                contador++;
                Thread hilo = new Thread(new ManejadorCliente(socket, router), "cliente-" + contador);
                hilo.start();                             
            }
        }
    }
}
