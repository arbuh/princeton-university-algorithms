import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] arr;
    private int size = 0;

    private class RandomArrayIterator implements Iterator<Item> {
        private Item[] arrCopy;
        private int i;

        private RandomArrayIterator(){
            arrCopy = (Item[]) new Object[size];
            for (i = 0; i < size; i++)
                arrCopy[i] = arr[i];
        }

        public boolean hasNext(){
            return i > 0;
        }

        public void remove(){
            throw new UnsupportedOperationException();
        }

        public Item next(){
            int randomIndex = StdRandom.uniformInt(i);
            Item randomItem = arrCopy[randomIndex];
            arrCopy[randomIndex] = arrCopy[--i];
            arrCopy[i] = null;
            return randomItem;
        }
    }

    public RandomizedQueue() {
        arr = (Item[]) new Object[2];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        if (size == arr.length) {
            resize(2 * arr.length);
        }
        arr[size++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }

        if (size > 0 && size <= arr.length / 4) {
            resize(arr.length / 2);
        }

        int randomIndex = getRandomIndex();
        Item randomItem = arr[randomIndex];
        arr[randomIndex] = arr[--size];
        arr[size] = null;
        return randomItem;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }

        int randomIndex = getRandomIndex();
        Item randomItem = arr[randomIndex];
        return randomItem;
    }

    public Iterator<Item> iterator() {
        return new RandomArrayIterator();
    }

    public static void main(String[] args) {
        StdOut.println("Test Randomized queue");
        StdOut.println("");

        RandomizedQueue<String> randQueue = new RandomizedQueue<String>();
        StdOut.println("Is empty: " + randQueue.isEmpty());

        StdOut.println("Add 'K'");
        randQueue.enqueue("B");
        StdOut.println("Size: " + randQueue.size());
        StdOut.println("Add 'A'");
        randQueue.enqueue("A");
        StdOut.println("Size: " + randQueue.size());
        StdOut.println("Add 'B'");
        randQueue.enqueue("B");
        StdOut.println("Size: " + randQueue.size());
        StdOut.println("Add 'O'");
        randQueue.enqueue("O");
        StdOut.println("Size: " + randQueue.size());
        StdOut.println("Add 'M'");
        randQueue.enqueue("M");
        StdOut.println("Size: " + randQueue.size());

        StdOut.println("Is empty: " + randQueue.isEmpty());

        StdOut.println("");

        StdOut.println("Print randomly using an iterator:");
        for (String s : randQueue)
            StdOut.print(s);

        StdOut.println("Print randomly using an iterator:");
        for (String s : randQueue)
            StdOut.print(s);

        StdOut.println("Print randomly using an iterator:");
        for (String s : randQueue)
            StdOut.print(s);

        StdOut.println("");

        StdOut.println("Sample random items:");
        StdOut.println("Sample 1: " + randQueue.sample());
        StdOut.println("Sample 2: " + randQueue.sample());
        StdOut.println("Sample 3: " + randQueue.sample());
        StdOut.println("Sample 4: " + randQueue.sample());
        StdOut.println("Sample 5: " + randQueue.sample());
        StdOut.println("Is empty: " + randQueue.isEmpty());
        StdOut.println("Size: " + randQueue.size());

        StdOut.println("Remove random items:");
        StdOut.println("Item 1: " + randQueue.dequeue());
        StdOut.println("Size: " + randQueue.size());
        StdOut.println("Is empty: " + randQueue.isEmpty());
        StdOut.println("Item 2: " + randQueue.dequeue());
        StdOut.println("Size: " + randQueue.size());
        StdOut.println("Is empty: " + randQueue.isEmpty());
        StdOut.println("Item 3: " + randQueue.dequeue());
        StdOut.println("Size: " + randQueue.size());
        StdOut.println("Is empty: " + randQueue.isEmpty());
        StdOut.println("Item 4: " + randQueue.dequeue());
        StdOut.println("Size: " + randQueue.size());
        StdOut.println("Is empty: " + randQueue.isEmpty());
        StdOut.println("Item 5: " + randQueue.dequeue());
        StdOut.println("Size: " + randQueue.size());
        StdOut.println("Is empty: " + randQueue.isEmpty());
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++)
            copy[i] = arr[i];
        arr = copy;
    }

    private int getRandomIndex() {
        return StdRandom.uniformInt(size);
    }
}
