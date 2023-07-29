import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        if (k > 0) {
            throw new IllegalArgumentException("k cannot be smaller than zero");
        }

        RandomizedQueue randQueue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty() && k > 0) {
            String str = StdIn.readString();
            randQueue.enqueue(str);
            k--;
        }

        Iterator<String> iterator = randQueue.iterator();
        while (iterator.hasNext()){
            String str =  iterator.next();
            StdOut.println(str);
        }
    }
}
