package hw1;

public class Utils {

    /**
     * Find an index of an element matching `target`.
     * @param xs
     * @param target
     * @return index of a matching element, -1 otherwise
     */
    public static int findIndex(int[] xs, int target) {
        for (int i = 0; i < xs.length; i++) {
            if (xs[i] == target) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Calculate the sum of an array.
     * @param xs
     * @return sum of an array
     */
    public static double sum(double[] xs) {
        double sum = 0;
        for (int i = 0; i < xs.length; i++) {
            sum += xs[i];
        }
        return sum;
    }

    /**
     * Reverse the elements of a String array. For example, ["A", "BB", "C"] => ["C", "BB", "A"]
     * @param xs
     * @return a newly created array containing elements of xs in reversed order
     */
    public static String[] reverse(String[] xs) {
        String[] reverseArray = new String[xs.length];
        for (int k = 0; k < xs.length; k++) {
            reverseArray[k] = xs[k];
        }
        for (int i = 0; i < reverseArray.length/2; i++) {
            swap(reverseArray, i, reverseArray.length-1-i);
        }
        return reverseArray;
    }

    private static void swap(String[] xs, int i, int j) {
        String temp = xs[i];
        xs[i] = xs[j];
        xs[j] = temp;
    }

    /**
     * Returns an array containing running averages of an array.
     * @param xs
     * @return an array containing running average
     *
     * Given an input xs = [1, 2, 3, 4], `average()` returns a new array
     * containing running averages [1.0, 1.5, 2.0, 2.5].
     * Here,
     *      1.0 = 1 / 1
     *      1.5 = (1 + 2) / 2
     *      2.0 = (1 + 2 + 3) / 3
     *      2.5 = (1 + 2 + 3 + 4) / 4
     */
    public static double[] average(int[] xs) {
        double[] avgArray = new double[xs.length];
        double sum = 0;
        for (int i = 0; i < xs.length; i++) {
            sum += xs[i];
            avgArray[i] = sum / (i+1);
        }
        return avgArray;
    }

}
