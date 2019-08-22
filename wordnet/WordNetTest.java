import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedDFS;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class WordNetTest {


    @Test
    public void countwords() throws FileNotFoundException {
        DirectedDFS dfs;
        ST<Integer, String> s;
        Digraph g;
        Scanner sc = new Scanner(new File("synsets.txt"), "utf-8");
        int lines = 0;
        int cntWord = 0;
        // word , id
        Map<String, List<Integer>> wmap = new HashMap<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String [] params = line.split(",");

            int id = Integer.parseInt(params[0]);
            String synsets = params[1];
            String [] words = synsets.split(" ");

            lines++;
            cntWord += words.length;

            for (String word : words) {
                List<Integer> ids = wmap.getOrDefault(word, new ArrayList<>(1));
                ids.add(id);
                wmap.putIfAbsent(word, ids);
            }

        }
        sc.close();

        System.out.println(lines + ", " + cntWord + " : " + (1D*cntWord / lines));

        for (String w : wmap.keySet()) {
            List<Integer> ids = wmap.get(w);
            if (ids.size() > 1) {
                System.out.println(w + " : " + ids);
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalGraph_cycle() {
        new WordNet("synsets6.txt","hypernyms6InvalidCycle.txt");
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalGraph_multiple_roots() {
        new WordNet("synsets6.txt","hypernyms6InvalidTwoRoots.txt");
    }
    @Test(expected = IllegalArgumentException.class)
    public void illegalGraph_two_components() {
        new WordNet("synsets6.txt","hypernyms6InvalidCycle+Path.txt");
    }

    @Test
    public void  pathToRoot() {
        int root = 38003;
        In in = new In("digraph-wordnet.txt");
        Digraph g = new Digraph(in);
        g = g.reverse();
        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(g, root);
        int V = g.V();
        int maxD = Integer.MIN_VALUE;
        List<Integer> deepVtx = new ArrayList<>();
        for (int vtx = 0; vtx < V; vtx++) {
            if (g.outdegree(vtx) > 0 || !bfs.hasPathTo(vtx)) {
                continue;
            }
            int dist = bfs.distTo(vtx);
            if (dist > maxD) {
                maxD = dist;
                deepVtx.clear();
                deepVtx.add(vtx);
            } else if ( dist == maxD) {
                deepVtx.add(vtx);
            }
        }
        for (Integer vtx : deepVtx) {
            System.out.printf("%d : dist %d\n", vtx, maxD);
        }


    }
}