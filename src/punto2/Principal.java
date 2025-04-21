package punto2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Principal {
    @FunctionalInterface
    interface Buscador { int buscar(int[] a, int x); }

    public static void main(String[] args) {
        try {
            generarArchivos();

            int[] tamaños = {10000, 100000, 1000000};
            Map<String, Buscador> métodos = new LinkedHashMap<>();
            métodos.put("Lineal",   (a, x) -> AlgoritmosDeBusqueda.busquedaLineal(a, x));
            métodos.put("Binaria",  (a, x) -> AlgoritmosDeBusqueda.busquedaBinaria(a, x));
            métodos.put("Ternaria", (a, x) -> AlgoritmosDeBusqueda.busquedaTernaria(a, x));
            métodos.put("Saltos",   (a, x) -> AlgoritmosDeBusqueda.busquedaPorSaltos(a, x));

            System.out.println("Pruebas de búsqueda (tiempos en ms)\n");

            for (int n : tamaños) {
                String archivo = "datos" + n + ".txt";
                System.out.printf(">>> n = %,d elementos (archivo: %s)%n", n, archivo);

                int[] datos = leerArreglo(archivo);
                Arrays.sort(datos);
                int objetivo = datos[datos.length - 1];

                for (Map.Entry<String, Buscador> entry : métodos.entrySet()) {
                    String nombre = entry.getKey();
                    Buscador buscador = entry.getValue();

                    long t0 = System.nanoTime();
                    int pos = buscador.buscar(datos, objetivo);
                    long t1 = System.nanoTime();
                    double ms = (t1 - t0) / 1_000_000.0;


                    System.out.printf("%-8s : pos=%d, %7.3f ms%n", nombre, pos, ms);

                }
                System.out.println();
            }

        } catch (IOException e) {
            System.err.println("¡Error de E/S!: " + e.getMessage());
        }
    }

    private static void generarArchivos() throws IOException {
        generarArchivo("datos10000.txt",  10_000);
        generarArchivo("datos100000.txt", 100_000);
        generarArchivo("datos1000000.txt",1_000_000);
    }

    private static void generarArchivo(String nombre, int cantidad) throws IOException {
        File f = new File(nombre);
        if (f.exists()) return;
        Random rnd = new Random(12345);
        try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
            for (int i = 0; i < cantidad; i++) {
                int num8 = rnd.nextInt(90_000_000) + 10_000_000;
                pw.println(num8);
            }
            System.out.println("Archivo creado: " + nombre);
        }
    }

    private static int[] leerArreglo(String nombreArchivo) throws IOException {
        List<Integer> lista = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(nombreArchivo))) {
            while (sc.hasNextInt()) lista.add(sc.nextInt());
        }
        return lista.stream().mapToInt(i -> i).toArray();
    }
}
