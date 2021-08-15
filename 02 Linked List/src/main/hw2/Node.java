package hw2;

public class Node<T> {
    private T item;
    private Node<T> prev;
    private Node<T> next;

    /**
     * Create a new node.
     * @param item  a data
     * @param prev  previous node link
     * @param next  next node link
     */
    public Node(T item, Node<T> prev, Node<T> next) {
        this.item = item;
        this.prev = prev;
        this.next = next;
    }

    /**
     * Returns item in a node.
     * @return an item
     */
    public T getItem() {
        return item;
    }

    /**
     * Set an item in a node.
     * @param item
     */
    public void setItem(T item) {
        this.item = item;
    }

    /**
     * Return previous node link.
     * @return previous node link
     */
    public Node<T> getPrev() {
        return prev;
    }

    /**
     * Set previous node link.
     * @param prev  previous node link
     */
    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }

    /**
     * Returns next node link.
     * @return  next node link
     */
    public Node<T> getNext() {
        return next;
    }

    /**
     * Set next node link.
     * @param next  next node link
     */
    public void setNext(Node<T> next) {
        this.next = next;
    }
}
