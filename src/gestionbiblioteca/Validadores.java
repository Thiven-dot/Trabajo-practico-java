package gestionbiblioteca;


public class Validadores {

    public static Validador largoMaximo(int max) {
        return (nombre, valor) -> {
            if (valor.length() > max) {
                throw new SolicitudInvalidaException(nombre + " no puede superar " + max + " caracteres");
            }
        };
    }

    public static Validador enteroPositivo() {
        return (nombre, valor) -> {
            if (aEntero(nombre, valor) <= 0) {
                throw new SolicitudInvalidaException(nombre + " debe ser un entero positivo");
            }
        };
    }

    public static Validador rango(int min, int max) {
        return (nombre, valor) -> {
            int numero = aEntero(nombre, valor);
            if (numero < min || numero > max) {
                throw new SolicitudInvalidaException(nombre + " debe estar entre " + min + " y " + max);
            }
        };
    }

    private static int aEntero(String nombre, String valor) {
        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            throw new SolicitudInvalidaException(nombre + " debe ser un numero entero");
        }
    }
}
