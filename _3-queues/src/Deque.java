import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int n;         // number of elements on deque
    private Node<Item> first;    // beginning of deque
    private Node<Item> last;     // end of deque

    // helper linked list class
    private class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> previous;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last  = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (n == 0) {
            Node<Item> node = new Node<>();
            node.item = item;
            first = node;
            last = node;
        } else {
            Node<Item> oldFirst = first;
            first = new Node<>();
            first.item = item;
            first.next = oldFirst;
            oldFirst.previous = first;
            if (n == 1) {
                last.previous = first;
            }
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (n == 0) {
            Node<Item> node = new Node<>();
            node.item = item;
            first = node;
            last = node;
        } else {
            Node<Item> oldLast = last;
            last = new Node<>();
            last.item = item;
            last.previous = oldLast;
            oldLast.next = last;
        }
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size() < 1) throw new NoSuchElementException();
        Node<Item> oldFirst = first;
        if (n == 1) {
            last = null;
            first = null;
        } else {
            first = oldFirst.next;
            first.previous = null;
        }
        n--;
        return oldFirst.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size() < 1) throw new NoSuchElementException();
        Node<Item> oldLast = last;
        if (n == 1) {
            last = null;
            first = null;
        } else {
            last = oldLast.previous;
            last.next = null;
        }
        n--;
        return oldLast.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // a linked-list iterator
    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        deque.addFirst("first");
        deque.addFirst("newFirst");
        deque.addFirst("newNewFirst");
        deque.addLast("Last");
        deque.addLast("newLast");
        deque.addFirst("ultraFirst");
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeLast());
        if (deque.size() > 0 || !deque.isEmpty()) {
            Iterator<String> it = deque.iterator();
            while (it.hasNext()) {
                StdOut.println(it.next());
            }
        }
    }

}
