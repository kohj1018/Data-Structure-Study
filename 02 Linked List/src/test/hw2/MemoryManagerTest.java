package hw2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.Vector;

import static java.lang.Math.abs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MemoryManagerTest {

    private MemoryManager manager;
    Vector<Block> blocks;
    Random generator;

    @BeforeEach
    public void setup() {
        manager = new MemoryManager(1024);
        blocks = new Vector<>();
        generator = new Random(System.currentTimeMillis());
    }

    @Test
    public void should_allocate_memory_if_request_size_is_less_than_available_heap() {
        Block block = manager.malloc(256);

        assertEquals(256, block.getSize());
        assertEquals(1, manager.getHeap().getSize());
        assertEquals(1024 - 256, availableMemory(manager.getHeap()));
    }

    @Test
    public void should_allocate_memory_if_total_request_size_is_less_than_available_heap() {
        manager.malloc(256);
        manager.malloc(64);
        manager.malloc(128);
        manager.malloc(512);

        assertEquals(1, manager.getHeap().getSize());
        assertEquals(64, availableMemory(manager.getHeap()));
    }

    @Test
    public void should_have_reduced_heap_size_if_allocation_satisfied() {
        assertEquals(1024, availableMemory(manager.getHeap()));

        manager.malloc(256);
        manager.malloc(64);
        manager.malloc(128);
        manager.malloc(512);

        assertEquals(1, manager.getHeap().getSize());
        assertEquals(64, availableMemory(manager.getHeap()));
    }

    @Test
    public void should_combine_with_upper_adjacent_free_block() {
        Block b1 = manager.malloc(256);
        Block b2 = manager.malloc(256);
        Block b3 = manager.malloc(256);
        Block b4 = manager.malloc(256);
        manager.free(b4);
        assertEquals(1, manager.getHeap().getSize());
        manager.free(b3);
        assertEquals(1, manager.getHeap().getSize());
    }

    @Test
    public void should_combine_with_lower_adjacent_free_block() {
        Block b1 = manager.malloc(256);
        Block b2 = manager.malloc(256);
        Block b3 = manager.malloc(256);
        Block b4 = manager.malloc(256);
        manager.free(b3);
        assertEquals(1, manager.getHeap().getSize());
        manager.free(b4);
        assertEquals(1, manager.getHeap().getSize());
    }

    @Test
    public void should_combine_both_adjacent_free_blocks() {
        Block b1 = manager.malloc(256);
        Block b2 = manager.malloc(256);
        Block b3 = manager.malloc(256);
        Block b4 = manager.malloc(256);
        manager.free(b2);
        assertEquals(1, manager.getHeap().getSize());
        manager.free(b4);
        assertEquals(2, manager.getHeap().getSize());
        manager.free(b3);
        assertEquals(1, manager.getHeap().getSize());
    }

    @Test
    public void should_throw_exception_if_request_is_bigger_than_available_memory() {
        assertThrows(OutOfMemoryException.class, () -> manager.malloc(2048));
    }

    @Test
    public void should_throw_exception_if_out_of_memory_due_to_fragmentation() {
        assertThrows(OutOfMemoryException.class, () -> {
            manager.malloc(256);
            Block b1 = manager.malloc(64);
            manager.malloc(256);
            Block b2 = manager.malloc(64);
            manager.malloc(384);
            manager.free(b1);
            manager.free(b2);
            manager.malloc(128);
        });
    }

    @Test
    public void random_test() {
        for (int n = 0; n < 100; n++) {
            manager = new MemoryManager(1024);
            blocks = new Vector<Block>();

            for (int i = 0; i < 100; i++) {
                int operation = generator.nextInt() % 2;
                if (operation == 0) { // alloc
                    int size = genRequest();
                    if (size > 0) {
                        try {
                            blocks.add(manager.malloc(size));
                        } catch (OutOfMemoryException e) {
                            /* ignore this because of fragmentation */
                        }
                    }
                } else {
                    int index = pickReturnBlock();
                    if (index != -1) {
                        manager.free(blocks.get(index));
                        blocks.remove(index);
                    }
                }
            }
            assertEquals(1024,
                +availableMemory(manager.getHeap()) + allocatedMemory());
        }
    }

    private int pickReturnBlock() {
        if (blocks.size() == 0) return -1;
        else
            return abs(generator.nextInt()) % blocks.size();
    }

    private int genRequest() {
        int size = 0;
        int gen = abs(generator.nextInt()) % 5;
        switch (gen) {
            case 0:
                size = 16;
                break;
            case 1:
                size = 32;
                break;
            case 2:
                size = 64;
                break;
            case 3:
                size = 128;
                break;
            case 4:
                size = 256;
                break;
        }
        if (size <= availableMemory(manager.getHeap())) {
            return size;
        } else
            return 0;
    }

    /*
     * It would be a lot better if we made this method as MemoryManager's public method.
     */
    private int availableMemory(DLinkedList<Block> heap) {
        if (heap.isEmpty()) return 0;

        Node<Block> current = heap.getFirst();
        int sum = 0;
        for (int i = 0; i < heap.getSize(); i++) {
            sum += current.getItem().getSize();
            current = current.getNext();
        }
        return sum;
    }

    private int allocatedMemory() {
        return blocks.stream().mapToInt(b -> b.getSize()).sum();
    }


}