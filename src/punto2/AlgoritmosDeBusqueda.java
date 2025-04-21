package punto2;

public class AlgoritmosDeBusqueda {
    public static int busquedaLineal(int[] a, int x) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == x) {
                return i;  // posición encontrada
            }
        }
        return -1;  // no encontrado
    }
    public static int busquedaBinaria(int[] a, int x) {
        int izquierda = 0, derecha = a.length - 1;
        while (izquierda <= derecha) {
            int medio = (izquierda + derecha) / 2;
            if (a[medio] == x) return medio;
            if (a[medio] < x) izquierda = medio + 1;
            else derecha = medio - 1;
        }
        return -1;
    }

    // 3. Búsqueda Ternaria O(log_3 n)
    public static int busquedaTernaria(int[] a, int x) {
        return busquedaTernariaRec(a, x, 0, a.length - 1);
    }
    private static int busquedaTernariaRec(int[] a, int x, int izq, int der) {
        if (izq > der) return -1;
        int terzo = (der - izq) / 3;
        int mid1 = izq + terzo;
        int mid2 = der - terzo;
        if (a[mid1] == x) return mid1;
        if (a[mid2] == x) return mid2;
        if (x < a[mid1]) return busquedaTernariaRec(a, x, izq, mid1 - 1);
        else if (x > a[mid2]) return busquedaTernariaRec(a, x, mid2 + 1, der);
        else return busquedaTernariaRec(a, x, mid1 + 1, mid2 - 1);

    }
    // 4. Búsqueda por saltos O(√n)
    public static int busquedaPorSaltos(int[] a, int x) {
        int n = a.length;
        int salto = (int) Math.sqrt(n);
        int prev = 0;
        while (prev < n && a[Math.min(n-1, prev + salto)] < x) {
            prev += salto;
        }
        int fin = Math.min(prev + salto, n);
        for (int i = prev; i < fin; i++) {
            if (a[i] == x) return i;
        }
        return -1;
    }
}

