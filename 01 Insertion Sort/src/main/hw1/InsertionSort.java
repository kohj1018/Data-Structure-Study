package hw1;

public class InsertionSort {

    /**
     * Sort an array xs in a non-increasing order
     * @param xs
     */
    public static void isort(int[] xs) {
        for (int i = 1; i < xs.length; i++) {
            insert(xs, i);
        }
    }

    /**
     * Insert element xs[k] to already sorted group x[0]..x[k-1]
     * @param xs
     * @param k
     */
    private static void insert(int[] xs, int k) {
        while (k>0 && xs[k-1] > xs[k]) {
            swap(xs, k, k-1);
            --k;
        }
    }

    /**
     * Swap elements at position i and j in array xs
     * @param xs
     * @param i
     * @param j
     */
    private static void swap(int[] xs, int i, int j) {
        int temp = xs[i];
        xs[i] = xs[j];
        xs[j] = temp;
    }
}
