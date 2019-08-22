/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;

import java.util.ArrayList;
import java.util.List;

public class PathFinder {
    public static List<List<Integer>> findPathes(double[][] w, int s, int t) {

        List<List<Integer>> pathes = new ArrayList<>();

        int [] vs = new int[w.length]; // visited
        visit(pathes, new ArrayList<>(), w, s, t, vs);
        return pathes;
    }

    static void visit(List<List<Integer>> holder, List<Integer> curpath, double[][] w, int s,
                      int t, int[] visited) {
        if (visited[s] > 0) {
            return;
        }
        if (s == t) {
            List<Integer> copy = new ArrayList<>(curpath);
            copy.add(t);
            holder.add(copy);
            return;
        }
        visited[s] = 1;
        curpath.add(s);

        for (Integer adj : adj(w, s)) {
            visit(holder, curpath, w, adj, t, visited);
        }
        curpath.remove(Integer.valueOf(s));
        visited[s] = 0;
    }

    static List<Integer> adj(double[][] w, int s) {
        List<Integer> adj = new ArrayList<>();
        for (int i = 0; i < w[s].length; i++) {
            if(w[s][i] > 0) {
                adj.add(i);
            }
        }
        return adj;
    }

    public static void main(String[] args) {

        double [][] w = {
            {0, 1, 1, 0},
            {1, 0, 1, 1},
            {0, 0, 0, 1},
            {0, 0, 0, 0},
        };

        List<List<Integer>> pathes = findPathes(w, 0, 3);
        for (List<Integer> path : pathes) {
            System.out.println(path);
        }

        FlowEdge fe = new FlowEdge(0, 3, 10);
        FlowNetwork fnet = new FlowNetwork(4, 5);
        FordFulkerson ffk = new FordFulkerson(fnet, 0, 3);

    }
}
