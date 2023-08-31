import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        if (k < 0) {
            throw new IllegalArgumentException("k cannot be smaller than zero");
        }

        RandomizedQueue<String> randQueue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            randQueue.enqueue(str);
        }

        Iterator<String> iterator = randQueue.iterator();
        while (iterator.hasNext() && k > 0){
            String str =  iterator.next();
            StdOut.println(str);
            k--;
        }
    }
}
