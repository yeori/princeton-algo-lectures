import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.TST;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

public class BoggleSolverTest {

    @Test
    public void testTST() {
        String [] words = {
            "JAVA",
            "JAVASCRIPT",
            "STRING",
            "STRINGBUILDER",
            "STRINGBUFFER"
        };
        TST<Boolean> tree = new TST<>();
        Arrays.stream(words).forEach(w-> tree.put(w, Boolean.TRUE));
        assertFalse(tree.contains("STRIKE"));
        assertFalse(tree.contains("JAV"));

        assertTrue(tree.contains("STRING"));
        assertTrue(tree.contains("JAVA"));
    }

    @Test
    public void time_for_hashing() {
        List<String> words = load("dictionary-zingarelli2005.txt");
        long s = System.nanoTime();
        Map<String, Boolean> dic = new HashMap<>(words.size());
        for (String word : words) {
            dic.put(word, Boolean.TRUE);
        }
        s = System.nanoTime() - s;
        System.out.printf("[hash] init time %.2f sec(size: %d)\n", s/1_000_000_000d, dic.size());

        s = System.nanoTime();
        for (String word : words) {
            dic.containsKey(word);
        }
        s = System.nanoTime() - s;
        System.out.printf("[hash] search time %.2f sec(size: %d)\n", s/1_000_000_000d, dic.size());

    }

    @Test
    public void time_for_tst() {
        List<String> words = load("dictionary-zingarelli2005.txt");
        long s = System.nanoTime();
        TST<Boolean> dic = new TST<>();
        for (String word : words) {
            dic.put(word, Boolean.TRUE);
        }
        s = System.nanoTime() - s;
        System.out.printf("[tst ] init time %.2f sec(size: %d)\n", s/1_000_000_000d, dic.size());

        s = System.nanoTime();
        for (String word : words) {
            dic.contains(word);
        }
        s = System.nanoTime() - s;
        System.out.printf("[tst ] search time %.2f sec(size: %d)\n", s/1_000_000_000d, dic.size());

    }

    List<String> load(String fname) {
        In in = new In(fname);
        List<String> words = new ArrayList<>();
        while(!in.isEmpty()) {
            words.add(in.readLine());
        }
        return words;
    }

    @Test
    public void unquekey() {
        int [][] map = {
            {2, 3, 5, 7, 11},
            {13, 17, 19, 23},
            {29, 31, 37, 41},
            {43, 47, 53, 59},
        };
        long v= 1;
        for (int i = 0; i < map.length; i++) {
            for (int k = 0; k < map[i].length; k++) {
                if (v > v*map[i][k]) {
                    fail("overflow "+ v*map[i][k]  + ": " + v + "and "+ map[i][k]);
                }
                v *= map[i][k];

            }
        }
        System.out.println(v);
    }

    @Test
    public void traverseBoard() {
        /*
          A . . A
          . B . B
          A B C .
         */
        BoggleBoard board = new BoggleBoard("board4x4.txt");
        int n = board.rows()*board.cols();
        int [] visit = new int[n];
        StringBuilder words = new StringBuilder(n);
        List<String> holder = new ArrayList<>(1_012_525);

        long t = System.nanoTime();
        dfs(board, 2, 2, visit,words, holder);
        t = System.nanoTime() - t;
        System.out.printf("elapsed %.2f\n", t/1_000_000_000d);
        Out out = new Out("out-2x2.txt");
        for (String s : holder) {
            out.println(s);
        }
        out.println(holder.size());
        // System.out.println(holder.size());


        /*
        1012516: APNESDEITATYUO
        1012517: APNESDEITATYUOE
        1012518: APNESDEITATYUOEE
        1012519: APNESDEITATYUOE
        1012520: APNESDEITATYUOEE

         */
        /*
        for (int ir = 0; ir < board.rows(); ir++) {
            for (int ic = 0; ic < board.cols(); ic++) {
                Arrays.fill(visit, 0);
                words.delete(0, words.length());
                dfs(board, ir, ic, visit, words);
                break;
            }
        }
        */
    }
    static int [][] vec = {
            {-1, -1}, {-1, 0}, { -1, +1}, // upleft up upright
            { 0, -1},          {  0, +1}, //   left      right
            {+1, -1}, {+1, 0}, { +1, +1}, // down ...
    };
    static int R = 0, C = 1;
    int dfs(BoggleBoard board, int ir, int ic, int[] visit, StringBuilder words, List<String> holder) {
        if (!isValid(board, ir, ic)) {
            return 0;
        }
        int vidx = ir*board.cols() + ic;
        if(visit[vidx] != 0) {
            return 0;
        }
        visit[vidx] = 1;
        words.append(board.getLetter(ir, ic));
        // holder.add(words.toString());
        // System.out.println(words.toString());
        int touched = 0;
        for (int i = 0; i < vec.length; i++) {
            int row = ir + vec[i][R];
            int col = ic + vec[i][C];

            touched += dfs(board, row, col, visit, words, holder);
        }
        if (touched == 0) {
            holder.add(words.toString());
        }
        words.delete(words.length()-1, words.length());
        visit[vidx] = 0;
        return 1;
    }

    private boolean isValid(BoggleBoard board, int row, int col) {
        return row >=0 && row < board.rows() && col >= 0 && col < board.cols();
    }

    @Test
    public void test_prefix() {
        In in = new In("dictionary-yawl.txt");
        String[] dictionary = in.readAllStrings();
        TST<Boolean> tree = new TST<>();
        for (int i = 0; i < dictionary.length; i++) {
            tree.put(dictionary[i], Boolean.TRUE);
        }
        Iterable<String> xe = tree.keysWithPrefix("D");
        int cnt = 0;
        for (String s : xe) {
            cnt++;
        }
        System.out.println("D* : " + cnt);
    }
}