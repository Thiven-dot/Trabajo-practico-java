package gestionbiblioteca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class Comunicador implements AutoCloseable {
    private final BufferedReader entrada;
    private final PrintWriter salida;

    public Comunicador(InputStream in, OutputStream out) {
        this.entrada = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        this.salida = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8), true);
    }

    public void enviar(String mensaje) {
        salida.println(mensaje);
    }

    public String recibir() throws IOException {
        return entrada.readLine();
    }

    @Override
    public void close() throws IOException {
        entrada.close();
        salida.close();
    }
}
