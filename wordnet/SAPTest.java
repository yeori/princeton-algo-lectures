import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class SAPTest {
    @Test
    public void test1() {
        Digraph g = graph("digraph1.txt");
        SAP sap = new SAP(g);

        assertEquals(-1, sap.ancestor(2, 6));
        assertEquals(-1, sap.length(2, 6));

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