package gestionbiblioteca;

import java.io.IOException;
import java.net.Socket;

public class ManejadorCliente implements Runnable {
    private final Socket socket;
    private final Router router;

    public ManejadorCliente(Socket socket, Router router) {
        this.socket = socket;
        this.router = router;
    }

    @Override
    public void run() {
        String hilo = Thread.currentThread().getName();
        System.out.println("[" + hilo + "] conectado: " + socket.getInetAddress() + ":" + socket.getPort());
        Sesion sesion = new Sesion();

        try (Comunicador com = new Comunicador(socket.getInputStream(), socket.getOutputStream())) {

            com.enviar("OK Conectado. Formato: recurso/accion/param1/param2");

            String linea;
            while ((linea = com.recibir()) != null) {
                try {
                    Solicitud solicitud = ParserSolicitudes.parsear(linea);
                    String resultado = router.despachar(solicitud, sesion);
                    com.enviar("OK " + resultado);
                } catch (FrameworkException e) {
                    com.enviar("ERROR " + e.getMessage());
                } catch (Exception e) {
                   
                    System.out.println("[" + hilo + "] error inesperado: " + e);
                    com.enviar("ERROR interno del servidor");
                }
            }
        } catch (IOException e) {
            System.out.println("[" + hilo + "] error de conexion: " + e.getMessage());
        } finally {
            System.out.println("[" + hilo + "] desconectado");
        }
    }
}
