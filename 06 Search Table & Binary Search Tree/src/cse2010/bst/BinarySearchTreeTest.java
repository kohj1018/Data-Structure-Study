package cse2010.bst;

import cse2010.BaseTest;
import cse2010.orderedmap.OrderedMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class BinarySearchTreeTest extends BaseTest {

    @Override
    protected void createOrderedMap() {
        orderedMap = new BinarySearchTree<>();
    }

    OrderedMap<Integer, Integer> tree;

    @BeforeEach
    public void setUp() {
        super.setUp();
        tree = makeTree(10, 5, 3, 4, 7, 6, 20, 30, 15, 25);
    }

    @Test
    void tree_with_root_only() {
        tree = makeTree(7);

        assertEquals(7, tree.firstEntry().getKey());
        assertEquals(7, tree.lastEntry().getKey());
        assertEquals(7, tree.floorEntry(7).getKey());
        assertNull(tree.higherEntry(7));
        assertEquals(7, tree.ceilingEntry(7).getKey());
        assertNull(tree.lowerEntry(7));
    }

    @Test
    void test_lowerEntry_when_left_subtree_exits() {
        assertEquals(7, tree.lowerEntry(10).getKey());
        assertEquals(4, tree.lowerEntry(5).getKey());
        assertEquals(6, tree.lowerEntry(7).getKey());
        assertEquals(15, tree.lowerEntry(20).getKey());
    }

    @Test
    void test_lowerEntry_when_no_left_subtree_exits() {
        assertNull(tree.lowerEntry(3));
        assertEquals(10, tree.lowerEntry(15).getKey());
        assertEquals(20, tree.lowerEntry(25).getKey());
    }

    @Test
    void test_higherEntry_when_right_subtree_exits() {
        assertEquals(4,  tree.higherEntry(3).getKey());
        assertEquals(6,  tree.higherEntry(5).getKey());
        assertEquals(15,  tree.higherEntry(10).getKey());
        assertEquals(25,  tree.higherEntry(20).getKey());
    }

    @Test
    void test_higherEntry_when_no_right_subtree_exits() {
        assertEquals(5,  tree.higherEntry(4).getKey());
        assertEquals(10,  tree.higherEntry(7).getKey());
        assertEquals(20,  tree.higherEntry(15).getKey());
        assertEquals(30,  tree.higherEntry(25).getKey());
        assertNull(tree.higherEntry(30));
    }

    private OrderedMap<Integer, Integer> makeTree(Integer... elements) {
        return makeTree(Arrays.asList(elements));
    }

    private OrderedMap<Integer, Integer> makeTree(List<Integer> elements) {
        OrderedMap<Integer, Integer> tree = new BinarySearchTree<>();
        elements.forEach(i -> tree.put(i, i));
        return tree;
    }

    @Test
    void basic_tests() {
        List<Integer> as = Arrays.asList(6, 2, 1, 4, 3, 7, 5);
        OrderedMap<Integer, Integer> tree = makeTree(as);

        assertEquals(new HashSet<>(as), tree.keys());
    }

    @Test
    void test_insertion() {
        OrderedMap<Integer, Integer> tree = new BinarySearchTree<>();

        Set<Integer> as = genList(10, 100);
        Set<Integer> bs = new HashSet<>();

        for (int i : as) {
            tree.put(i, i);
            bs.add(i);
            assertEquals(bs, tree.keys());
        }
    }

    @Test
    void test_deletion() {
        OrderedMap<Integer, Integer> tree = new BinarySearchTree<>();

        Set<Integer> as = genList(10, 100);
        Set<Integer> bs = new HashSet<>();
        as.forEach(i -> { tree.put(i,i); bs.add(i); });

        for (int i : as) {
            tree.remove(i);
            bs.remove(i);
            assertEquals(bs, tree.keys());
        }
    }

    private Set<Integer> genList(int count, int limit) {
        Random rnd = new Random(System.currentTimeMillis());
        Set<Integer> as = new HashSet<>();
        int i = 0;

        while (i++ < count) {
            int p = rnd.nextInt(limit);
            while (as.contains(p)) p = rnd.nextInt(limit);
            as.add(p);
        }

        return as;
    }

}