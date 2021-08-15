package cse2010;

import cse2010.bst.BinarySearchTree;
import cse2010.orderedmap.Entry;
import cse2010.orderedmap.OrderedMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

abstract public class BaseTest {
    protected final Integer[] inputs = { 1, 6, 3, 10, 7, 2, 4 };

    protected final Integer[] floorOutputs = { null, 1, 2, 3, 4, 4, 6, 7, 7, 7, 10, 10 };
    protected final Integer[] ceilingOutputs = { 1, 1, 2, 3, 4, 6, 6, 7, 10, 10, 10, null };
    protected final Integer[] lowerOutputs = { null, null, 1, 2, 3, 4, 4, 6, 7, 7, 7, 10 };
    protected final Integer[] higherOutputs = { 1, 2, 3, 4, 6, 6, 7, 10, 10, 10, null, null };

    protected OrderedMap<Integer,Integer> orderedMap;

    abstract protected void createOrderedMap();

    @BeforeEach
    protected void setUp() {
        createOrderedMap();
        Arrays.stream(inputs).forEach(k -> orderedMap.put(k, k));
    }

    @Test
    void should_return_null_if_empty() {
        orderedMap = new BinarySearchTree<>();

        assertEquals(0, orderedMap.size());
        assertNull(orderedMap.firstEntry());
        assertNull(orderedMap.lastEntry());
        assertNull(orderedMap.floorEntry(42));
        assertNull(orderedMap.ceilingEntry(42));
        assertNull(orderedMap.lowerEntry(42));
        assertNull(orderedMap.higherEntry(42));
    }

    @Test
    void should_insert_new_entries() {
        assertEquals(7, orderedMap.size());
        assertEquals(new HashSet<>(Arrays.asList(1, 2, 3, 4, 6, 7, 10)), orderedMap.keys());

        orderedMap.put(5,5);

        assertEquals(8, orderedMap.size());
        assertEquals(new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 10)), orderedMap.keys());
    }

    @Test
    void should_return_first_Entry() {
        assertEquals(new Entry<>(1, 1), orderedMap.firstEntry());
    }

    @Test
    void should_return_last_Entry() {
        assertEquals(new Entry<>(10, 10), orderedMap.lastEntry());
    }

    @ParameterizedTest
    @ValueSource(ints = { Integer.MIN_VALUE, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, Integer.MAX_VALUE })
    void should_return_floor_Entry(int key) {
        if (key == Integer.MIN_VALUE)
            assertNull(orderedMap.floorEntry(key));
        else if (key == Integer.MAX_VALUE)
            assertEquals(new Entry<>(10, 10), orderedMap.floorEntry(key));
        else
            assertEquals(new Entry<>(floorOutputs[key], floorOutputs[key]), orderedMap.floorEntry(key));
    }

    @ParameterizedTest
    @ValueSource(ints = { Integer.MIN_VALUE, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, Integer.MAX_VALUE })
    void should_return_ceiling_Entry(int key) {
        if (key == Integer.MIN_VALUE)
            assertEquals(new Entry<>(1, 1), orderedMap.ceilingEntry(key));
        else if (key == Integer.MAX_VALUE)
            assertNull(orderedMap.ceilingEntry(key));
        else
            assertEquals(new Entry<>(ceilingOutputs[key], ceilingOutputs[key]), orderedMap.ceilingEntry(key));
    }

    @ParameterizedTest
    @ValueSource(ints = { Integer.MIN_VALUE, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, Integer.MAX_VALUE })
    void should_return_lower_Entry(int key) {
        if (key == Integer.MIN_VALUE || key == 1)
            assertNull(orderedMap.lowerEntry(key));
        else if (key == Integer.MAX_VALUE)
            assertEquals(new Entry<>(10, 10), orderedMap.lowerEntry(key));
        else
            assertEquals(new Entry<>(lowerOutputs[key], lowerOutputs[key]), orderedMap.lowerEntry(key));
    }

    @ParameterizedTest
    @ValueSource(ints = { Integer.MIN_VALUE, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, Integer.MAX_VALUE })
    void should_return_higher_Entry(int key) {
        if (key == Integer.MIN_VALUE)
            assertEquals(new Entry<>(1, 1), orderedMap.higherEntry(key));
        else if (key == Integer.MAX_VALUE || key == 10)
            assertNull(orderedMap.higherEntry(key));
        else
            assertEquals(new Entry<>(higherOutputs[key], higherOutputs[key]), orderedMap.higherEntry(key));
    }

    @ParameterizedTest
    @ValueSource(ints = { Integer.MIN_VALUE, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, Integer.MAX_VALUE })
    void should_able_to_search(int key) {
        if (orderedMap.keys().contains(key)) {
            assertEquals(key, orderedMap.get(key));
        }
        else
            assertNull(orderedMap.get(key));
    }

    @Test
    void should_remove_entry_if_matched_key_found() {
        orderedMap.remove(1);
        assertEquals(6, orderedMap.size());
        assertEquals(new HashSet<>(Arrays.asList(2, 3, 4, 6, 7, 10)), orderedMap.keys());

        orderedMap.remove(10);
        assertEquals(5, orderedMap.size());
        assertEquals(new HashSet<>(Arrays.asList(2, 3, 4, 6, 7)), orderedMap.keys());
    }

    @Test
    void should_not_remove_if_no_matched_key_found() {
        Integer oldValue = orderedMap.remove(4);
        assertEquals(4, oldValue);
        assertEquals(6, orderedMap.size());

        oldValue = orderedMap.remove(4);
        assertNull(oldValue);
        assertEquals(6, orderedMap.size());
    }

    @Test
    void should_reflect_entry_removal_when_search() {
        orderedMap.remove(1);
        assertEquals(orderedMap.lowerEntry(1), orderedMap.floorEntry(1));
        assertEquals(new Entry<>(2,2), orderedMap.ceilingEntry(1));
        assertEquals(new Entry<>(2,2), orderedMap.higherEntry(1));

        orderedMap.remove(6);
        assertEquals(new Entry<>(4,4), orderedMap.floorEntry(6));
        assertEquals(orderedMap.ceilingEntry(6), orderedMap.higherEntry(6));
    }
}
