import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import org.junit.Test;

public class BFSTest {

    @Test
    public void test() {
        Digraph g = graph("rss/g00.txt");
        int source = 4;
        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(g, source);
        BreadthFirstPaths f;
        int vlen = g.V();
        for (int v = 0; v < vlen ; v++) {
            System.out.printf("%d -> %d : dist(%d)\n", source, v, bfs.distTo(v));
            for (Integer d : bfs.pathTo(v)) {
                System.out.printf("%d, ", d);
            }
            System.out.println();

        }

    }
    private Digraph graph(String graphFile) {
        In in = new In(graphFile);
        Digraph g = null;
        while (!in.isEmpty()) {
            int vlen = Integer.parseInt(in.readLine());
            g = new Digraph(vlen);

            int edges = Integer.parseInt(in.readLine());
            for (int i = 0; i < edges; i++) {
                int src = in.readInt();
                int dst = in.readInt();
                g.addEdge(src, dst);
            }
        }
        in.close();
        return g;
    }

}
