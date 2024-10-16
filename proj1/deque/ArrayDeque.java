package deque;

import java.util.Arrays;
import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T>{
    private T[] items;
    private int nextFirst;
    private int nextLast;
    private int size;
    private final int FACTOR = 10;

    public ArrayDeque() {
        assert FACTOR > 0;
        items = (T[]) new Object[FACTOR];
        nextFirst = 0;
        nextLast = 1;
        size = 0;
    }

    /**
     * Resize the length of the items.
     */
    private void resize(int capacity) {
        T[] newItems =  (T[]) new Object[capacity];
        int start = (nextFirst + 1) % items.length;
        int j = 0;
        for (int i = start; i < start + size; i++) {
            newItems[j] = items[i % items.length];
            j++;
        }
        items = newItems;
        nextFirst = items.length - 1;
        nextLast = j % items.length;
    }
    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[nextFirst] = item;
        nextFirst = (nextFirst - 1 + items.length) % items.length;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[nextLast] = item;
        nextLast = (nextLast + 1) % items.length;
        size += 1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        int start = (nextFirst + 1) % items.length;
        for (int i = start; i < start + size; i++) {
            System.out.print(items[i % items.length]);
            System.out.print(" ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T oriItem = items[(nextFirst + 1) % items.length];
        items[(nextFirst + 1) % items.length] = null;
        nextFirst = (nextFirst + 1) % items.length;
        size -= 1;
        if ((size < items.length / 4) && (size > 4)) {
            resize(items.length / 2);
        }
        return oriItem;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T oriItem = items[(nextLast - 1 + items.length) % items.length];
        items[(nextLast - 1 + items.length) % items.length] = null;
        nextLast = (nextLast - 1 + items.length) % items.length;
        size -= 1;
        if ((size < items.length / 4) && (size > 4)) {
            resize(items.length / 2);
        }
        return oriItem;
    }

    @Override
    public T get(int index) {
        if (index > size - 1) {
            return null;
        }
        int start = (nextFirst + 1) % items.length;
        return items[(start + index) % items.length];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T>{
        private int curNextFirst = nextFirst;
        private int curNextLast = nextLast;

        @Override
        public boolean hasNext() {
            return curNextFirst != (curNextLast - 1 + items.length) % items.length;
        }

        @Override
        public T next() {
            curNextFirst = (curNextFirst + 1) % items.length;
            return items[curNextFirst];
        }
    }
}
