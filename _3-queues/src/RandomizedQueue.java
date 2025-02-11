import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    // initial capacity of underlying resizing array
    private static final int INIT_CAPACITY = 8;

    private Item[] queue;       // queue elements
    private int elementCount;          // number of elements on queue
    private int last;       // index of next available slot


    /**
     * Initializes an empty queue.
     */
    public RandomizedQueue() {
        queue = (Item[]) new Object[INIT_CAPACITY];
        elementCount = 0;
        last = 0;
    }

    /**
     * Is this queue empty?
     * @return true if this queue is empty; false otherwise
     */
    public boolean isEmpty() {
        return elementCount == 0;
    }

    /**
     * Returns the number of items in this queue.
     * @return the number of items in this queue
     */
    public int size() {
        return elementCount;
    }

    // resize the underlying array
    private void resize(int capacity) {
        assert capacity >= elementCount;
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < elementCount; i++) {
            copy[i] = queue[i];
        }
        queue = copy;
        last  = elementCount;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        // double size of array if necessary and recopy to front of array
        if (elementCount == queue.length) resize(2* queue.length);   // double size of array if necessary
        queue[last++] = item;                        // add item
        elementCount++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int random = StdRandom.uniformInt(elementCount);
        Item item = queue[random];
        last--;
        queue[random] = queue[last];
        queue[last] = null;
        elementCount--;
        if (elementCount > 0 && elementCount == queue.length/4) resize(queue.length/2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return queue[StdRandom.uniformInt(elementCount)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    // an array iterator, from first to last-1
    private class ArrayIterator implements Iterator<Item> {
        private Item[] iteratorItems;
        private int i = 0;
        private int readItems = 0;
        private int totalItems = last;

        ArrayIterator() {
            this.iteratorItems = queue.clone();
            StdRandom.shuffle(this.iteratorItems);
        }

        public boolean hasNext() {
            return readItems < totalItems;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = iteratorItems[i];
            if (item == null) {
                i++;
                return next();
            }
            readItems++;
            i++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            queue.enqueue(item);
        }
        StdOut.println(queue.sample());
        Iterator<String> it1 = queue.iterator();
        Iterator<String> it2 = queue.iterator();
        while (it1.hasNext() && it2.hasNext()) {
            StdOut.println(it1.next());
            StdOut.println(it2.next());
        }
        while (!queue.isEmpty()) {
            StdOut.println(queue.dequeue());
        }
    }

}