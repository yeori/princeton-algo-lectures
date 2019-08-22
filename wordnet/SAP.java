/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

import java.util.Arrays;

public class SAP {

    private final Digraph dag;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph g) {
        if (g == null) {
            throw new IllegalArgumentException();
        }
        this.dag = new Digraph(g);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return sca(Arrays.asList(v), Arrays.asList(w))[1];
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return sca(Arrays.asList(v), Arrays.asList(w))[0];
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        int [] min = sca(v, w);
        return min[1];
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return sca(v, w)[0];
    }

    private void checkNN(Iterable<Integer> it, int limit) {
        if (it == null) {
            throw new IllegalArgumentException();
        }
        for (Integer v : it) {
            if (v ==  null || v.intValue() < 0 || v.intValue() >= limit) {
                throw new IllegalArgumentException();
            }
        }
    }

    private int[] sca (Iterable<Integer> vs, Iterable<Integer> ws) {
        checkNN(vs, dag.V());
        checkNN(ws, dag.V());
        BreadthFirstDirectedPaths bfs0 = new BreadthFirstDirectedPaths(dag, vs);
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(dag, ws);
        int vsize = dag.V();
        int [] min = {-1, Integer.MAX_VALUE};
        for (int x = 0; x < vsize; x++) {
            if (!bfs0.hasPathTo(x) || !bfs1.hasPathTo(x)) {
                continue;
            }
            int dist = bfs0.distTo(x) + bfs1.distTo(x);
            if (dist < min[1]) {
                min[0] = x;
                min[1] = dist;
            }
        }
        if (min[0] == -1) {
            min[1] = -1;
        }
        return min;
    }
    public static void main(String[] args) {

    }
}
