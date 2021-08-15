package cse2010.table;

import cse2010.BaseTest;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class SearchTableTest extends BaseTest {

    @Override
    protected void createOrderedMap() {
        orderedMap = new SearchTable<>(10);
    }

    @Test
    void should_throw_exception_if_orderedMap_is_full() {
        orderedMap = new SearchTable<>(10);

        assertThrows(IllegalStateException.class,
                () -> IntStream.rangeClosed(1, 11).forEach(k -> orderedMap.put(k, k)));
    }

}
