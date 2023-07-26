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

        public Boolean hasNext(){
            return current != null;
        }

        public void remove(){
            throw new UnsupportedOperationException();
        }

        public Item next(){
            if (current == null){
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public Deque() {}

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return this.size;
    }

    public void addFirst(Item item) {
        if (item == null){
            throw new IllegalArgumentException();
        }

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;

        if (isEmpty()){
            first = last;
        } else {
            oldFirst.prev = first;
        }

        size++;
    }

    public void addLast(Item item) {
        if (item == null){
            throw new IllegalArgumentException();
        }

        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.prev = oldLast;

        if (isEmpty()){
            last = first;
        } else {
            oldLast.next = last;
        }

        size++;
    }

    public Item removeFirst() {
        if (isEmpty()){
            throw new NoSuchElementException();
        }

        Item item = first.item;
        first = first.next;
        first.prev = null;
        size--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()){
            throw new NoSuchElementException();
        }

        Item item = last.item;
        last = last.prev;
        last.next = null;
        size--;
        return item;

    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    public static void main(String[] args) {

    }

}
