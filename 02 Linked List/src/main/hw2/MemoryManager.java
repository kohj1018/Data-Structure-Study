package hw2;

import hw2.DLinkedList;

/* Block will be used as a type argument */
class Block {
    private int size;    // size of a block
    private int start;   // start address of a block
    private int end;     // end address of a block

    public Block(int size, int start, int end) {
        this.size = size;
        this.start = start;
        this.end = end;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "(" + size +", " + start + ", " + end + ")";
    }
}

public class MemoryManager {

    private DLinkedList<Block> heap = new DLinkedList<>();

    /**
     * Create a memeory manager with capacity given as parameter.
     * @param capacity
     */
    public MemoryManager(int capacity) {
        Block initBlock = new Block(capacity, 0, capacity - 1);
        this.heap.addFirst(initBlock);
    }

    /**
     * Returns a block information allocated for the requested size.
     * @param size  the size of requested memtory
     * @return  a block of the requested size if satisfieed, throws `OutOfMemoryException` exception otherwise.
     */
    public Block malloc(int size) {
        if (heap.isEmpty()) return null;
        Node<Block> cursor = heap.getFirst();
        while (cursor.getNext() != null && cursor.getItem().getSize() < size) { // cursor.getNext() != null은 trailer을 고려한 것.
            cursor = cursor.getNext();
        }
        if (cursor.getNext() == null) {
            throw new OutOfMemoryException("Out of Memory Exception");
        }

        Block b = new Block(size, cursor.getItem().getStart(), cursor.getItem().getStart() + size - 1);
        if (cursor.getItem().getSize() == size) {
            heap.remove(cursor);
        } else {
            cursor.getItem().setSize(cursor.getItem().getSize() - size);
            cursor.getItem().setStart(cursor.getItem().getStart() + size);
        }

        return b;
    }

    /**
     * Add a freed block to the free memory block list.
     * @param block  the size of requested memtory
     */
    public void free(Block block) {
        if (heap.isEmpty()) {
            heap.addFirst(block);
        } else {
            Node<Block> cursor = heap.getFirst();
            while (cursor.getNext() != null && cursor.getItem().getStart() < block.getStart()) {    // 커서가 trailer에 도달하거나, 커서의 메모리가 block보다 뒷순위이면 while문이 종료됨.
                cursor = cursor.getNext();
            }

            Node<Block> n = new Node<>(block, null, null);
            heap.addBefore(cursor, n);

            // merge
            if (cursor.getPrev().getPrev().getPrev() != null && cursor.getPrev().getItem().getStart() - cursor.getPrev().getPrev().getItem().getEnd() == 1) {   // cursor.getPrev().getPrev().getPrev() != null은 header을 고려한 것.
                cursor.getPrev().getItem().setSize(cursor.getPrev().getItem().getSize() + cursor.getPrev().getPrev().getItem().getSize());
                cursor.getPrev().getItem().setStart(cursor.getPrev().getPrev().getItem().getStart());
                heap.remove(cursor.getPrev().getPrev());
            }
            if (cursor.getNext() != null && cursor.getItem().getStart() - cursor.getPrev().getItem().getEnd() == 1) {   // cursor.getNext() != null은 trailer을 고려한 것.
                cursor.getItem().setSize(cursor.getItem().getSize() + cursor.getPrev().getItem().getSize());
                cursor.getItem().setStart(cursor.getPrev().getItem().getStart());
                heap.remove(cursor.getPrev());
            }
        }
    }

    /**
     * Returns the capacity of the free storage.
     * @return  capacity of the free storage
     */
    public int getCapacity() {
        if (heap.isEmpty()) return 0;

        int sum = 0;
        Node<Block> cursor = heap.getFirst();
        for (int i = 0; i < heap.getSize(); i++) {
            sum += cursor.getItem().getSize();
            cursor = cursor.getNext();
        }
        return sum;
    }

    // for testing purpose only
    DLinkedList<Block> getHeap() {
        return heap;
    }

    @Override
    public String toString() {
        return heap.toString();
    }
}


