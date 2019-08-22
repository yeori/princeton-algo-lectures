/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.TST;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BoggleSolver {
    private static int [][] vec = {
            {-1, -1}, {-1, 0}, { -1, +1}, // upleft up upright
            { 0, -1},          {  0, +1}, //   left      right
            {+1, -1}, {+1, 0}, { +1, +1}, // down ...
    };
    private static int R = 0, C = 1;
    private final TST<Boolean> tree = new TST<>();

    /**
     * Initializes the data structure using the given array of strings as the dictionary.
     * (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
     */
    public BoggleSolver(String[] dictionary) {
        for (int i = 0; i < dictionary.length; i++) {
            tree.put(dictionary[i], Boolean.TRUE);
        }

    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        int row = board.rows();
        int col = board.cols();
        int n = row * col;
        int [] visit = new int[n];
        StringBuilder words = new StringBuilder(2*n); // for Q-> QU case

        Set<String> answers = new HashSet<>();
        long cnt = 0;
        for (int ir = 0; ir < row; ir++) {
            for (int ic = 0; ic < col; ic++) {
                Arrays.fill(visit, 0);
                dfs(board, ir, ic, visit, words, answers, tree);
                // countAnswer(holder, answers);
            }
        }
        // dfs(board, 2, 2, visit,words, holder, tree);
        List<String> sorted = new ArrayList<>(answers.size());
        for (String answer : answers) {
            if(answer.length() > 2) {
                sorted.add(answer);
            }
        }
        sorted.sort((a,b)->a.compareTo(b));
        return sorted;
    }

    private void dfs(BoggleBoard board,
                    int ir, int ic, int[] visit,
                    StringBuilder words,
                    Set<String> holder, TST<Boolean> tree) {
        if (!isValid(board, ir, ic)) {
            return;
        }

        int vidx = ir*board.cols() + ic;
        if(visit[vidx] != 0) {
            return;
        }
        visit[vidx] = 1;
        char ch = board.getLetter(ir, ic);
        words.append(ch);
        if (ch == 'Q') {
            words.append('U');
        }

        String token = words.toString();
        if (tree.contains(token)) {
            holder.add(token);
        }
        Queue<String> q = (Queue<String>) tree.keysWithPrefix(token);
        if (q.size() > 0) {
            for (int i = 0; i < vec.length; i++) {
                int row = ir + vec[i][R];
                int col = ic + vec[i][C];

                dfs(board, row, col, visit, words, holder, tree);
            }
        }
        words.delete(words.length()-(ch == 'Q' ? 2 : 1), words.length());
        visit[vidx] = 0;
    }

    private boolean isValid(BoggleBoard board, int row, int col) {
        return row >=0 && row < board.rows() && col >= 0 && col < board.cols();
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (!tree.contains(word)) {
            return 0;
        }
        int pointValue;
        int length = word.length();
        if (length < 3) pointValue = 0;
        else if (length < 5)  pointValue = 1;
        else if (length == 5) pointValue = 2;
        else if (length == 6) pointValue = 3;
        else if (length == 7) pointValue = 5;
        else                  pointValue = 11;
        return pointValue;
    }
}
