import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClienteConsola {

    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        int puerto = args.length > 1 ? Integer.parseInt(args[1]) : 3000;

        try (Socket socket = new Socket(host, puerto);
             BufferedReader delServidor = new BufferedReader(
                     new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter alServidor = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
             BufferedReader teclado = new BufferedReader(
                     new InputStreamReader(System.in, StandardCharsets.UTF_8))) {

            System.out.println("Conectado a " + host + ":" + puerto + " (escriba 'salir' para terminar)");

            Thread lector = new Thread(() -> {
                try {
                    String linea;
                    while ((linea = delServidor.readLine()) != null) {
                        System.out.println("< " + linea);
                    }
                    System.out.println("El servidor cerro la conexion.");
                } catch (IOException ex) {
                }
                System.exit(0);
            });
            lector.start();

            String linea;
            while ((linea = teclado.readLine()) != null && !linea.equals("salir")) {
                alServidor.println(linea);
            }
        } catch (IOException ex) {
            System.out.println("No se pudo conectar a " + host + ":" + puerto + " -> " + ex.getMessage());
        }
    }
}
