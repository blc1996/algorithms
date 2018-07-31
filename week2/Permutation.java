import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class Permutation {
    public static void main(String[] args){
       int k = Integer.parseInt(args[0]);
        //Deque<String> queue = new Deque<String>();
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            //queue.addLast(StdIn.readString());
            queue.enqueue(StdIn.readString());
        }

        Iterator iter = queue.iterator();
        for (int i = 0; i < k; i++) {
            StdOut.println(iter.next());
        }
    }
}
