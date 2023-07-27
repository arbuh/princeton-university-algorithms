import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] arr;
    private int lastIndex = 0;

    public RandomizedQueue() {
        arr = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (lastIndex == arr.length) {
            resize(2 * arr.length);
        }
        arr[lastIndex++] = item;
    }

    public Item dequeue() {

    }

    public Item sample() {
        return arr[getRandomIndex()];
    }

    public Iterator<Item> iterator() {

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
        return StdRandom.uniformInt(lastIndex + 1);
    }
}
