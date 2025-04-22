package punto1;

import java.util.Arrays;

public class AlgoritmosDeOrdenamiento {

    // 1. Bubble Sort          Θ(n²)
// https://www.geeksforgeeks.org/bubble-sort/
    public static void bubbleSort(int[] a) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (a[j] > a[j + 1]) {
                    int t = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = t;
                }
    }


// https://www.geeksforgeeks.org/quick-sort/
    public static void quickSort(int[] a, int lo, int hi) {
        if (lo < hi) {
            int p = partition(a, lo, hi);
            quickSort(a, lo, p - 1);
            quickSort(a, p + 1, hi);
        }
    }

    static int partition(int[] a, int lo, int hi) {
        int pivot = a[hi], i = lo - 1;
        for (int j = lo; j < hi; j++) {
            if (a[j] < pivot) {
                i++;
                int t = a[i];
                a[i] = a[j];
                a[j] = t;
            }
        }
        int t = a[i + 1];
        a[i + 1] = a[hi];
        a[hi] = t;
        return i + 1;
    }


// https://www.geeksforgeeks.org/stooge-sort/
    public static void stoogeSort(int[] a, int i, int j) {
        if (a[i] > a[j]) {
            int t = a[i];
            a[i] = a[j];
            a[j] = t;
        }
        if (j - i + 1 > 2) {
            int t = (j - i + 1) / 3;
            stoogeSort(a, i, j - t);
            stoogeSort(a, i + t, j);
            stoogeSort(a, i, j - t);
        }
    }


// https://www.geeksforgeeks.org/radix-sort/
    public static void radixSort(int[] a) {
        int max = Arrays.stream(a).max().getAsInt();
        for (int exp = 1; max / exp > 0; exp *= 10) countSort(a, exp);
    }

    static void countSort(int[] a, int exp) {
        int n = a.length, output[] = new int[n], cnt[] = new int[10];
        for (int v : a) cnt[(v / exp) % 10]++;
        for (int i = 1; i < 10; i++) cnt[i] += cnt[i - 1];
        for (int i = n - 1; i >= 0; i--) {
            int d = (a[i] / exp) % 10;
            output[--cnt[d]] = a[i];
        }
        System.arraycopy(output, 0, a, 0, n);
    }


// https://www.geeksforgeeks.org/merge-sort/
    public static void mergeSort(int[] a, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSort(a, l, m);
            mergeSort(a, m + 1, r);
            merge(a, l, m, r);
        }
    }

    static void merge(int[] a, int l, int m, int r) {
        int n1 = m - l + 1, n2 = r - m;
        int L[] = Arrays.copyOfRange(a, l, m + 1);
        int R[] = Arrays.copyOfRange(a, m + 1, r + 1);
        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2)
            a[k++] = (L[i] <= R[j]) ? L[i++] : R[j++];
        while (i < n1) a[k++] = L[i++];
        while (j < n2) a[k++] = R[j++];
    }


// https://en.wikipedia.org/wiki/Bitonic_sorter
    public static void bitonicSort(int[] a) {
        int n = a.length, size = 1;
        while (size < n) size <<= 1;
        int[] b = Arrays.copyOf(a, size);
        bitonic(b, 0, size, true);
        System.arraycopy(b, 0, a, 0, n);
    }

    static void bitonic(int[] a, int lo, int cnt, boolean dir) {
        if (cnt > 1) {
            int k = cnt / 2;
            bitonic(a, lo, k, true);
            bitonic(a, lo + k, k, false);
            bitonicMerge(a, lo, cnt, dir);
        }
    }

    static void bitonicMerge(int[] a, int lo, int cnt, boolean dir) {
        if (cnt > 1) {
            int k = cnt / 2;
            for (int i = lo; i < lo + k; i++)
                if ((a[i] > a[i + k]) == dir) {
                    int t = a[i];
                    a[i] = a[i + k];
                    a[i + k] = t;
                }
            bitonicMerge(a, lo, k, dir);
            bitonicMerge(a, lo + k, k, dir);
        }
    }
}

