package deque;

import java.lang.reflect.Array;
import java.util.Iterator;

/** The interface of double-ended queque */
public interface Deque<T> {
    /**
     * Adds an item of type T to the front of the deque.
     * You can assume that item is never `null`.
     * @param item generic item.
     */
    public void addFirst(T item);

    /**
     * Adds an item of type T to the back of the deque.
     * You can assume that item is never `null`.
     * @param item generic item.
     */
    public void addLast(T item);


    /**
     * @return true if deque is empty, false otherwise.
     */
    public boolean isEmpty();

    /**
     * @return the number of items in the deque.
     */
    public int size();

    /**
     * Prints the items in the deque from first to last,
     * separated by a space. Once all the items have been
     * printed, print out a new line.
     */
    public void printDeque();

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     * @return the item at the front of the deque.
     * If no such item exists, return null.
     */
    public T removeFirst();

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     * @return the item at the back of the deque.
     * If no such item exists, returns null.
     */
    public T removeLast();

    /**
     * Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth. If no such item exists,
     * returns null. Must not alter the deque!
     * @param index the index item in deque
     * @return the index item in deque. If no such item exists, returns null.
     */
    public T get(int index);

    /**
     * The deque objects we’ll make are iterable (i.e. Iterable<T>)
     * so we must provide this method to return an iterator.
     * @return an iterator.
     */
    public Iterator<T> iterator();

    /**
     * Returns whether the parameter o is equal to the Deque.
     * o is considered equal if it is a deque and if it contains the
     * same contents (as goverened by the generic T’s equals method)
     * in the same order.
     * @param o the object.
     * @return true if o is equal to the deque, false otherwise.
     */
    public boolean equals(Object o);
}
