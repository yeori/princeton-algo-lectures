import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class WordNet {
    /*
     * {
     *   word : [ 2, 5, 12, 45, 901,..]
     *   zoo  : [ 23, 56, 123, ...]
     * }
     */
    private final TreeMap<String, List<Integer>> w2id;
    /*
     * {
     *   ...
     *   55764 : 'musical_soiree soiree_musicale'
     *   ...
     *   55825 : 'mutant mutation variation sport'
     *   ...
     * }
     */
    private final TreeMap<Integer, String> id2synset;
    // private Digraph dag;
    private SAP sap ;

    // constructor takes the name of the two input files
    public WordNet(String synsetFile, String hypernymFile) {
        checkNN(synsetFile, hypernymFile);
        w2id = new TreeMap<>();
        id2synset = new TreeMap<>();

        processSynset(synsetFile);

        // TODO - The input to the constructor does not correspond to a rooted DAG.
        Digraph g = buildGraph(hypernymFile);
        int vlen = g.V();
        int numfOfRoots = 0;
        for (int vtx = 0; vtx < vlen; vtx++) {
            int outdeg = g.outdegree(vtx);
            if (outdeg == 0) {
                numfOfRoots++;
            }
            if (numfOfRoots > 1) {
                throw new IllegalArgumentException();
            }
        }
        DirectedCycle dc = new DirectedCycle(g);
        if (dc.hasCycle()) {
            throw new IllegalArgumentException();
        }
        this.sap = new SAP(g);
    }

    private void checkNN(Object... vs) {
        for (int i = 0; i < vs.length; i++) {
            if (vs[i] == null) {
                throw new IllegalArgumentException();
            }
        }
    }

    private Digraph buildGraph(String hypernymFile) {
        int vetexSize = id2synset.size();
        Digraph g = new Digraph(vetexSize);
        In in = new In(hypernymFile);
        while (!in.isEmpty()) {
            String [] edge = in.readLine().split(",");
            int src = Integer.parseInt(edge[0]);
            for (int i = 1; i < edge.length; i++) {
                int dst = Integer.parseInt(edge[i]);
                g.addEdge(src, dst);
            }
        }
        return g;
    }

    private void processSynset(String file) {
        In in = new In(file);
        while (!in.isEmpty()) {
            String line = in.readLine();
            String [] tokens = line.split(",");
            int id = Integer.parseInt(tokens[0]);
            String synset = tokens[1];
            // tokens[2] - description ignored!

            // step-1. (id, synset)
            id2synset.put(id, synset);

            // step-2. (word, [id, id, id, ..])
            String [] words = tokens[1].split(" ");
            for (String word : words) {
                List<Integer> idList = w2id.getOrDefault(word, new ArrayList<>());
                idList.add(id);
                w2id.putIfAbsent(word, idList);
            }
        }
        in.close();
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return w2id.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return w2id.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        checkExist(nounA, nounB);
        // SAP sap = new SAP(dag);
        List<Integer> idOfA = w2id.get(nounA);
        List<Integer> idOfB = w2id.get(nounB);
        return sap.length(idOfA, idOfB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        checkExist(nounA, nounB);
        List<Integer> idOfA = w2id.get(nounA);
        List<Integer> idOfB = w2id.get(nounB);
        // SAP sap = new SAP(dag);
        int ancestorId = sap.ancestor(idOfA, idOfB);
        return id2synset.get(ancestorId);
    }

    private void checkExist(String... nouns) {
        for (int i = 0; i < nouns.length; i++) {
            if (!isNoun(nouns[i])) {
                throw new IllegalArgumentException();
            }
        }
    }

    public static void main(String[] args) {

    }
}
