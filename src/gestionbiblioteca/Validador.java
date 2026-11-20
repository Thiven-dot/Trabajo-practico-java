package gestionbiblioteca;

public class Validador {
    private Validador() {
    }

    // 1. Validador no vacio
    public static void validarNoVacio(String valor, String nombreCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new SolicitudInvalidaException("El campo '" + nombreCampo + "' no puede estar vacío.");
        }
    }

    // 2. Validador con el largo maximo
    public static void validarLargoMaximo(String valor, int maximo, String nombreCampo) {
        if (valor != null && valor.trim().length() > maximo) {
            throw new SolicitudInvalidaException(
                "El campo '" + nombreCampo + "' no puede superar los " + maximo + " caracteres."
            );
        }
    }

    // Validador positivo
    public static int validarEnteroPositivo(String valor, String nombreCampo) {
        validarNoVacio(valor, nombreCampo);
        try {
            int numero = Integer.parseInt(valor.trim());
            if (numero <= 0) {
                throw new SolicitudInvalidaException("El campo '" + nombreCampo + "' debe ser un número entero positivo.");
            }
            return numero;
        } catch (NumberFormatException e) {
            throw new SolicitudInvalidaException("El campo '" + nombreCampo + "' debe ser un número entero válido.");
        }
    }
}
