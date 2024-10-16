package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T>{
    private class Node {
        private Node prev;
        private final T item;
        private Node next;

        Node(Node p, T i, Node n) {
            prev = p;
            item = i;
            next = n;
        }
    }


    private final Node sentinel;
    private Node last;
    private int nodeSize;

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        last = sentinel;
        nodeSize = 0;
    }

    public LinkedListDeque(T item) {
        this();
        addLast(item);
    }

    @Override
    public void addFirst(T item) {
        Node oriFirstNode = sentinel.next;
        Node newNode = new Node(sentinel, item, sentinel.next);
        sentinel.next = newNode;
        oriFirstNode.prev = newNode;
        if (last.item == null) {
            last = newNode;
        }
        nodeSize += 1;
    }

    @Override
    public void addLast(T item) {
        Node newNode = new Node(last, item, last.next);
        last.next = newNode;
        sentinel.prev = newNode;
        last = newNode;
        nodeSize += 1;
    }

    @Override
    public boolean isEmpty() {
        return (nodeSize == 0);
    }

    @Override
    public int size() {
        return nodeSize;
    }

    @Override
    public void printDeque() {
        Node curNode = sentinel;
        while (curNode.next != sentinel) {
            System.out.print(curNode.next.item);
            System.out.print(" ");
            curNode = curNode.next;
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        T oriFirstItem = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        if (nodeSize > 0) {
            nodeSize -= 1;
        }
        return oriFirstItem;
    }

    @Override
    public T removeLast() {
        T oriLastItem = last.item;
        sentinel.prev = last.prev;
        last.prev.next = sentinel;
        if (nodeSize > 0) {
            nodeSize -= 1;
        }
        last = last.prev;
        return oriLastItem;
    }

    @Override
    public T get(int index) {
        if (index > nodeSize - 1) {
            return null;
        }
        Node curNode = sentinel;
        while (index >= 0) {
            curNode = curNode.next;
            index -= 1;
        }
        return curNode.item;
    }

    /**
     * Same as get, but uses recursion.
     * @param index the index of deque.
     * @return the index item in deque. If no such item exists, returns null.
     */
    public T getRecursive(int index) {
        if (index > nodeSize - 1) {
            return null;
        }
        return getReccursiveHelper(index, sentinel);
    }

    /**
     * The helper method for getReccursive.
     * @param index the index of deque.
     * @param curNode current node in the current stack.
     * @return the index item in deque. If no such item exists, returns null.
     */
    private T getReccursiveHelper(int index, Node curNode) {
        if (index < 0) {
            return curNode.item;
        }
        return getReccursiveHelper(index - 1, curNode.next);
    }

    // 实现 iterator 方法
    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    // 内部类实现 Iterator<T>
    private class LinkedListDequeIterator implements Iterator<T> {
        private Node curNode = sentinel.next; // 从第一个有效节点开始遍历

        @Override
        public boolean hasNext() {
            return curNode != sentinel; // 检查是否到达尾部（sentinel）
        }

        @Override
        public T next() {
            T nodeItem = curNode.item;
            curNode = curNode.next; // 移动到下一个节点
            return nodeItem;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof LinkedListDeque)) {
            return false;
        }
        LinkedListDeque<?> toEquals = (LinkedListDeque<?>) o;
        if (toEquals.size() != this.size()) {
            return false;
        }
        for (int i = 0; i < this.size(); i++) {
            if (toEquals.get(i) != this.get(i)) {
                return false;
            }
        }
        return true;
    }

}
