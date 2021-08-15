package hw4;
/*
 * CSE2010 Homework #4: ArrayStack.java
 *
 * Complete your stack here
 *
 */

public class ArrayStack<T> implements Stack<T> {
	public static final int MaxSize = 100;
	private final T[] elements;
	private int top = -1;

	public ArrayStack() {
		elements = (T[]) new Object[MaxSize];
	}

	@SuppressWarnings("unchecked")
	public ArrayStack(int capacity) {
		elements = (T[]) new Object[capacity];
	}

	public void push(T obj) {
		if (isFull())
			throw new FullStackException("Stack Overflow");

		top += 1;
		elements[top] = obj;
	}

	public T pop() throws EmptyStackException {
		if (top == -1) {
			throw new EmptyStackException("Stack Underflow");
		}

		top -= 1;

		return elements[top + 1];
	}

	public T top() {
		if (top == -1) {
			throw new EmptyStackException("Stack Underflow");
		}

		return elements[top];
	}

	public boolean isEmpty() {
		return top == -1;
	}

	public boolean isFull() {
		return top == MaxSize - 1;
	}
}
