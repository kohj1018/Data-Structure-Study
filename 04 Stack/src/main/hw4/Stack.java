package hw4;
/*
 * CSE2010 Homework #4: Stack.java
 *
 * DO NOT MODIFY THIS FILE!
 *
 */

public interface Stack<T> {
	void push(T item);
	T pop() throws EmptyStackException;
	T top() throws EmptyStackException;
	boolean isEmpty();
}
