package punto1;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Principal {

    @FunctionalInterface
    interface Ordenador { void ordenar(int[] a); }

    // Para no colgar con algoritmos super‑lentos
    private static final int UMBRAL_LENTO = 50_000;

    public static void main(String[] args) {
        try {
            generarArchivos();
            int[] tamaños = {10_000, 100_000, 1_000_000};
            Map<String, Ordenador> algoritmos = new LinkedHashMap<>();
            algoritmos.put("Bubble Sort", a -> AlgoritmosDeOrdenamiento.bubbleSort(a));
            algoritmos.put("Quick Sort",  a -> AlgoritmosDeOrdenamiento.quickSort(a, 0, a.length - 1));
            algoritmos.put("Stooge Sort", a -> AlgoritmosDeOrdenamiento.stoogeSort(a, 0, a.length - 1));
            algoritmos.put("Radix Sort",  a -> AlgoritmosDeOrdenamiento.radixSort(a));
            algoritmos.put("Merge Sort",  a -> AlgoritmosDeOrdenamiento.mergeSort(a, 0, a.length - 1));
            algoritmos.put("Bitonic Sort",a -> AlgoritmosDeOrdenamiento.bitonicSort(a));

            System.out.println("Pruebas de ordenamiento (tiempos en ms)\n");

            for (int n : tamaños) {
                String archivo = "datos" + n + ".txt";
                System.out.printf(">>> n = %,d elementos (archivo: %s)%n", n, archivo);


                int[] original = leerArreglo(archivo);

                for (Map.Entry<String,Ordenador> entry : algoritmos.entrySet()) {
                    String nombre = entry.getKey();
                    Ordenador ord = entry.getValue();

                    if ((nombre.equals("Bubble Sort") || nombre.equals("Stooge Sort"))
                            && n > UMBRAL_LENTO) {
                        System.out.printf("%-12s : omitir para n > %,d%n", nombre, UMBRAL_LENTO);
                        continue;
                    }

                    int[] copia = Arrays.copyOf(original, original.length);
                    long t0 = System.nanoTime();
                    ord.ordenar(copia);
                    long t1 = System.nanoTime();
                    long ms = TimeUnit.NANOSECONDS.toMillis(t1 - t0);

                    System.out.printf("%-12s : %,6d ms%n", nombre, ms);
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
        Random rnd = new Random(12345);      // semilla fija
        try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
            for (int i = 0; i < cantidad; i++) {
                int num8 = rnd.nextInt(90_000_000) + 10_000_000; // de 10_000_000 a 99_999_999
                pw.println(num8);
            }
            System.out.println("Archivo creado: " + nombre);
        }
    }


    private static int[] leerArreglo(String nombreArchivo) throws IOException {
        List<Integer> lista = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(nombreArchivo))) {
            while (sc.hasNextInt()) {
                lista.add(sc.nextInt());
            }
        }

        return lista.stream().mapToInt(i -> i).toArray();
    }
}
