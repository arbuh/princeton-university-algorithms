import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] arr;
    private int size = 0;

    private class RandomArrayIterator implements Iterator<Item> {
        private Item[] arrCopy;
        private int i;

        private RandomArrayIterator(){
            arrCopy = (Item[]) new Object[size];
            arrCopy = System.arraycopy(arr, 0, arrCopy, 0, size);
            i = size;
        }

        public Boolean hasNext(){
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
            return item;
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

    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++)
            copy[i] = arr[i];
        arr = copy;
    }

    private int getRandomIndex() {
        return StdRandom.uniformInt(size);
    }
}
