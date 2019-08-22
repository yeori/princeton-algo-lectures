/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Runner {

    public static void main(String[] args) {
        args = new String[]{"dictionary-yawl.txt" , "board-points26539.txt"}; // socre 33
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        long t = System.nanoTime();
        Iterable<String> words = solver.getAllValidWords(board);
        t = System.nanoTime() - t;
        for (String word : words) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
        StdOut.printf("%.2f secs", t /1_000_000_000d);
    }
}
