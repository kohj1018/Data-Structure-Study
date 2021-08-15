package hw1;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class InsertionSortTest {

    @Test
    void should_create_an_array_of_a_given_length() {
        // Given

        // When
        String[] xs = new String[10];

        // Then
        assertEquals(10, xs.length);
    }

    @Test
    void should_be_empty_if_empty_array_sorted() {
        // Given
        int[] xs = {};
        int[] sorted = {};

        // When
        InsertionSort.isort(xs);

        // Then
        assertTrue(Arrays.equals(sorted, xs));
    }

    @Test
    void should_not_be_changed_if_singleton_array_sorted() {
        // Given
        int[] xs = {1};
        int[] sorted = {1};

        // When
        InsertionSort.isort(xs);

        // Then
        assertTrue(Arrays.equals(sorted, xs));
    }

    @Test
    void should_be_in_non_decreasing_order_if_array_sorted() {
        // Given
        int[] xs = {5, 4, 3, 2, 1};
        int[] sorted = {1, 2, 3, 4, 5};

        // When
        InsertionSort.isort(xs);

        // Then
        assertTrue(Arrays.equals(sorted, xs));
    }

}