public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node next;
    }

    private Node first = null;
    private Node last = null;
    private int size = 0;

    public Deque() {}

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return this.size;
    }

    public void addFirst(Item item) {
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        size++;
    }

    public void addLast(Item item) {
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        oldLast.next = last;
        size++;
    }

    public Item removeFirst() {
        Item item = first.item;
        first = first.next;
        size--;
        return item;
    }

    public Item removeLast() {
        Item item = last.item;
        ???
        size--;

    }

    public Iterator<Item> iterator() {

    }

    public static void main(String[] args) {

    }

}
