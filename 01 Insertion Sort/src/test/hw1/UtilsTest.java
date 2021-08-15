package hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    @DisplayName("findIndex should return -1 if no matching element found")
    void test_findIndex1() {
        // Given
        int[] xs = new int[10];
        Arrays.setAll(xs, i -> i + 1);

        // When
        int index = Utils.findIndex(xs, 777);

        // Then
        assertEquals(-1, index);
    }

    @Test
    @DisplayName("findIndex should return a index if the first matching element found")
    void test_findIndex2() {
        // Given
        int[] xs = new int[10];
        Arrays.setAll(xs, i -> i + 1);

        // When
        int index = Utils.findIndex(xs, 5);

        // Then
        assertEquals(4, index);
    }

    @Test
    void sum_returns_total_of_an_entire_array() {
        // Given
        double[] xs = new double[100];
        Arrays.setAll(xs, i -> i + 1);

        // When
        double total = Utils.sum(xs);

        // Then
        assertEquals(5050d, total, 0.1);
    }

    @Test
    void reverse_returns_reversed_array() {
        // Given
        String[] xs = {"Hello", "Mellow", "Yellow"};
        String[] ys = {"Yellow", "Mellow", "Hello"};

        // When
        String[] result = Utils.reverse(xs);

        // Then
        assertEquals(3, result.length);
        assertArrayEquals(ys, result);
    }

    @Test
    void average_returns_running_averages() {
        // Given
        int[] xs = {1, 3, 5, 7, 9};
        double[] ys = {1.0, 2.0, 3.0, 4.0, 5.0};

        // When
        double[] result = Utils.average(xs);

        // Then
        assertArrayEquals(ys, result);
    }

    @Test
    void average_returns_running_averages2() {
        // Given
        int[] xs = {11, 5, 7, 13};
        double[] ys = {11.0, 8.0, 7.66, 9.0};

        // When
        double[] result = Utils.average(xs);

        // Then
        assertArrayEquals(ys, result, 0.01);
    }
}