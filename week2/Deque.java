import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first = null;
    private Node last = null;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public Deque() {
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return this.size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;

        if (isEmpty()) {
            last = first;
        } else {
            oldFirst.prev = first;
        }

        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.prev = oldLast;

        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }

        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }

        Item item = first.item;

        if (first.next != null){
            first = first.next;
            first.prev = null;
        }

        size--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }

        Item item = last.item;

        if (last.prev != null){
            last = last.prev;
            last.next = null;
        }

        size--;
        return item;

    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    public static void main(String[] args) {
        StdOut.println("Test Double-ended queue");
        StdOut.println("");

        Deque<String> deque = new Deque<String>();
        StdOut.println("Is empty: " + deque.isEmpty());

        StdOut.println("Add first 'B'");
        deque.addFirst("B");
        StdOut.println("Size: " + deque.size());
        StdOut.println("Add first 'A'");
        deque.addFirst("A");
        StdOut.println("Size: " + deque.size());
        StdOut.println("Add last 'O'");
        deque.addLast("O");
        StdOut.println("Size: " + deque.size());
        StdOut.println("Add first 'K'");
        deque.addFirst("K");
        StdOut.println("Size: " + deque.size());
        StdOut.println("Add last 'M'");
        deque.addLast("M");
        StdOut.println("Size: " + deque.size());

        StdOut.println("Is empty: " + deque.isEmpty());

        StdOut.println("");

        StdOut.println("Print all using an iterator:");
        for (String s : deque)
            StdOut.print(s);

        StdOut.println("");

        StdOut.println("Remove two first items:");
        String str;
        str = deque.removeFirst();
        StdOut.println(str);
        str = deque.removeFirst();
        StdOut.println(str);
        StdOut.println("Size: " + deque.size());

        StdOut.println("Remove two last items:");
        str = deque.removeLast();
        StdOut.println(str);
        str = deque.removeLast();
        StdOut.println(str);
        StdOut.println("Size: " + deque.size());

        StdOut.println("");

        StdOut.println("Print the rest using an iterator:");
        for (String s : deque)
            StdOut.print(s);

        StdOut.println("");

        StdOut.println("Remove all:");
        str = deque.removeFirst();
        StdOut.println("Size: " + deque.size());
        StdOut.println("Is empty: " + deque.isEmpty());

        StdOut.println("");

        StdOut.println("Add last '0'");
        deque.addLast("0");
        StdOut.println("Is empty: " + deque.isEmpty());
        StdOut.println("Remove first");
        str = deque.removeFirst();
        StdOut.println("Is empty: " + deque.isEmpty());

        StdOut.println("");

        StdOut.println("Add last '0'");
        deque.addLast("0");
        StdOut.println("Is empty: " + deque.isEmpty());
        StdOut.println("Remove last");
        str = deque.removeLast();
        StdOut.println("Is empty: " + deque.isEmpty());
    }

}
