package punto2;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Principal {

    public static void main(String[] args) {
        try {
            int[] tamaños = {10_000, 100_000, 1_000_000};

            System.out.println("Pruebas de búsqueda (tiempos en ms)\n");

            for (int n : tamaños) {
                String archivo = "datos" + n + ".txt";
                int[] arreglo = leerArreglo(archivo);

                // Asegurarse de que el arreglo esté ordenado para búsquedas binarias y similares
                Arrays.sort(arreglo);

                int valorBuscado = arreglo[arreglo.length - 1];  // Último valor (peor caso)

                System.out.printf(">>> n = %,d elementos (archivo: %s)%n", n, archivo);

                // Búsqueda Lineal
                long t0 = System.nanoTime();
                int posLineal = AlgoritmosDeBusqueda.busquedaLineal(arreglo, valorBuscado);
                long t1 = System.nanoTime();
                imprimirTiempo("Lineal", posLineal, t0, t1);

                // Búsqueda Binaria
                t0 = System.nanoTime();
                int posBinaria = AlgoritmosDeBusqueda.busquedaBinaria(arreglo, valorBuscado);
                t1 = System.nanoTime();
                imprimirTiempo("Binaria", posBinaria, t0, t1);

                // Búsqueda Ternaria
                t0 = System.nanoTime();
                int posTernaria = AlgoritmosDeBusqueda.busquedaTernaria(arreglo, valorBuscado);
                t1 = System.nanoTime();
                imprimirTiempo("Ternaria", posTernaria, t0, t1);

                // Búsqueda por Saltos
                t0 = System.nanoTime();
                int posSaltos = AlgoritmosDeBusqueda.busquedaPorSaltos(arreglo, valorBuscado);
                t1 = System.nanoTime();
                imprimirTiempo("Saltos", posSaltos, t0, t1);

                System.out.println();
            }

        } catch (IOException e) {
            System.err.println("¡Error de E/S!: " + e.getMessage());
        }
    }

    private static void imprimirTiempo(String nombre, int posicion, long t0, long t1) {
        double ms = (t1 - t0) / 1_000_000.0;  // convierte a milisegundos con decimales
        System.out.printf("%-8s : pos=%,d, %7.3f ms%n", nombre, posicion, ms);
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
