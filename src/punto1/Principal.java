package punto1;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Principal {

    private static final int UMBRAL_LENTO = 50_000;

    public static void main(String[] args) {
        try {
            generarArchivos();
            int[] tamaños = {10000, 100000, 1000000};

            System.out.println("Pruebas de ordenamiento (tiempos en ms)\n");

            for (int n : tamaños) {
                String archivo = "datos" + n + ".txt";

                // Línea modificada aquí:
                System.out.println(">>> n = " + n + " elementos (archivo: " + archivo + ")");

                int[] original = leerArreglo(archivo);

                ejecutar("Bubble Sort", original, n);
                ejecutar("Quick Sort", original, n);
                ejecutar("Stooge Sort", original, n);
                ejecutar("Radix Sort", original, n);
                ejecutar("Merge Sort", original, n);
                ejecutar("Bitonic Sort", original, n);

                System.out.println();
            }

        } catch (IOException e) {
            System.err.println("¡Error de E/S!: " + e.getMessage());
        }
    }

    private static void ejecutar(String nombre, int[] original, int n) {
        if ((nombre.equals("Bubble Sort") || nombre.equals("Stooge Sort")) && n > UMBRAL_LENTO) {
            System.out.printf("%-12s : omitir para n > %,d%n", nombre, UMBRAL_LENTO);
            return;
        }

        int[] copia = Arrays.copyOf(original, original.length);
        long t0 = System.nanoTime();

        if (nombre.equals("Bubble Sort")) {
            AlgoritmosDeOrdenamiento.bubbleSort(copia);
        } else if (nombre.equals("Quick Sort")) {
            AlgoritmosDeOrdenamiento.quickSort(copia, 0, copia.length - 1);
        } else if (nombre.equals("Stooge Sort")) {
            AlgoritmosDeOrdenamiento.stoogeSort(copia, 0, copia.length - 1);
        } else if (nombre.equals("Radix Sort")) {
            AlgoritmosDeOrdenamiento.radixSort(copia);
        } else if (nombre.equals("Merge Sort")) {
            AlgoritmosDeOrdenamiento.mergeSort(copia, 0, copia.length - 1);
        } else if (nombre.equals("Bitonic Sort")) {
            AlgoritmosDeOrdenamiento.bitonicSort(copia);
        }

        long t1 = System.nanoTime();
        long ms = TimeUnit.NANOSECONDS.toMillis(t1 - t0);

        System.out.printf("%-12s : %,6d ms%n", nombre, ms);
    }

    private static void generarArchivos() throws IOException {
        generarArchivo("datos10000.txt", 10000);
        generarArchivo("datos100000.txt", 100000);
        generarArchivo("datos1000000.txt", 1000000);
    }

    private static void generarArchivo(String nombre, int cantidad) throws IOException {
        File f = new File(nombre);
        if (f.exists()) return;
        Random rnd = new Random(12345);
        try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
            for (int i = 0; i < cantidad; i++) {
                int num8 = rnd.nextInt(90000000) + 10000000;
                pw.println(num8);
            }
            System.out.println("Archivo creado: " + nombre);
        }
    }

    private static int[] leerArreglo(String nombreArchivo) throws IOException {
        Scanner sc = new Scanner(new File(nombreArchivo));
        int[] datos = new int[1000000];
        int n = 0;

        while (sc.hasNextInt()) {
            datos[n++] = sc.nextInt();
        }
        sc.close();

        int[] resultado = new int[n];
        System.arraycopy(datos, 0, resultado, 0, n);
        return resultado;
    }
}
