import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        String token;
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        int step = 0;
        while (k > 0 && !StdIn.isEmpty()) {
            token = StdIn.readString();

            if (rq.size() == k) {
                if (StdRandom.uniform() < 1D/(step+1)) {
                    rq.dequeue();
                    rq.enqueue(token);
                }
            } else {
                rq.enqueue(token);
            }
            step++;
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }

    }
}
