/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BaseballElimination {
    private enum Group { In, Out }
    private static final double INF = 1_000_000_000.0;
    private int W;
    private int L;
    private int R;
    private final int[][] map;

    private Map<String, Integer> names;
    private List<String> idx2Name;
    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        int n = in.readInt();
        this.names = new HashMap<>(n);
        this.idx2Name = new ArrayList<>(n);
        int [][] games = new int[n][n+3];
        W = n;
        L = n + 1;
        R = n + 2;
        /*
        array games: n*(n+3)

        ex) n = 4
                t0 t1 t2 t3    W  L  R
              +-------------+-----------+
           t0 |   NxN       | # of      |
           t1 |   games     | win       |
           t2 |   btw teams | loss      |
           t3 |             | remaining |
              +-------------+-----------+
         */
        for (int i = 0; i < n; i++) {
            String teamName = in.readString();
            int winCnt = in.readInt();
            int loseCnt = in.readInt();
            int remainingGames = in.readInt();
            for (int k = 0; k < n; k++) {
                games[i][k] = in.readInt();
            }
            games[i][W] = winCnt;
            games[i][L] = loseCnt;
            games[i][R] = remainingGames;

            names.put(teamName, i);
            idx2Name.add(teamName);
        }
        this.map = games;
    }

    // number of teams
    public int numberOfTeams() {
        return map.length;
    }

    // all teams
    public Iterable<String> teams() {
        return names.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        int idx = teamToIndex(team);
        return map[idx][W];
    }

    // number of losses for given team
    public int losses(String team) {
        int idx = teamToIndex(team);
        return map[idx][L];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        int idx = teamToIndex(team);
        return map[idx][R];
    }

    private int teamToIndex(String team) {
        Integer idx = names.get(team);
        if (idx == null) {
            throw new IllegalArgumentException();
        }
        return idx;
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        int i = teamToIndex(team1);
        int k = teamToIndex(team2);
        return map[i][k];
    }

    // is given team eliminated?
    public boolean isEliminated(String teamX) {
        int x = teamToIndex(teamX);
        if (validElimination(x)) {
            return true;
        }

        Map<Group, Set<Integer>> cuts = divideByCut(teamX);
        Set<Integer> in = cuts.get(Group.In);
        Set<Integer> out = cuts.get(Group.Out);

        if (in.isEmpty()) {
            return false;
        } else if (out.contains(x)) {
            return true;
        }

        return false;
    }

    private Map<Group, Set<Integer>> divideByCut(String teamX) {
        int x = teamToIndex(teamX);
        int teams = this.numberOfTeams();

        // source index 0
        int s = 1;
        // # of matches at [1, ... n(n-1)/2 ]
        int matches = teams*(teams-1)/2;
        // team vertices except teamX
        // int teams = n; // index [ (1+matches), ... 1+matches+n)
        int t = 1; // dest
        // total # of vertice in flow network
        int nV = s + matches + teams + t;

        int v = 1;
        FlowNetwork fnet = new FlowNetwork(nV);
        // List<FlowEdge> gameList = new ArrayList<>();

        for (int ir = 0; ir < teams; ir++) {
            for (int ic = ir+1; ic < teams; ic++, v++) {
                double flow = 0;
                double games = map[ir][ic];
                if (ir == x || ic == x) {
                    // games = 0;
                    continue;
                }
                /*
                      one game edge    +     two team edges  +  two win edges
                          |                       v2Ir
                          |                     +-------> team ir ------+
                          v       vertex V     /                         \
                      s -----> match(ir, ic)  +                           +-- t
                                               \  v2Ic                   /
                                                +-------> team ic ------+
                 */

                // 1. game edge
                FlowEdge gameEdge = new FlowEdge(0, v, games, flow);
                fnet.addEdge(gameEdge);
                // gameList.add(gameEdge);
                // 2. two team edges


                int teamIr = s + matches + ir;
                int teamIc = s + matches + ic;
                // (v, teamIr) and (v, teamIc)
                double capacity = INF;
                flow = 0;

                FlowEdge v2Ir = new FlowEdge(v, teamIr, capacity, flow);
                fnet.addEdge(v2Ir);

                FlowEdge v2Ic = new FlowEdge(v, teamIc, capacity, flow);
                fnet.addEdge(v2Ic);
            }
        }

        int sinkIndex = nV - 1;
        int bestWinX = map[x][W] + map[x][R]; // win + remaining games of team X
        // List<FlowEdge> winList = new ArrayList<>();
        for (int teamIndex = 0; teamIndex < teams; teamIndex++) {
            double capacity = teamIndex == x ? 0 : bestWinX - map[teamIndex][W];
            double flow = 0;
            FlowEdge winEdge = new FlowEdge(v++, sinkIndex, capacity, flow);
            fnet.addEdge(winEdge);
            // winList.add(winEdge);
        }


        FordFulkerson ff = new FordFulkerson(fnet, 0, sinkIndex);
        /*
        System.out.println("max flow: " + ff.value());
        for(int i = 0; i < fnet.V(); i++) {
            System.out.printf("vertex %d : %s\n",i, ff.inCut(i) ? "in": "out");
        }
        System.out.println("[source -> games]");
        for (FlowEdge e : gameList) {
            System.out.printf("%d -> %d : %.1f / %.1f, %.2f%%\n", e.from(), e.to(), e.flow(), e.capacity(), 100*e.flow()/e.capacity());
        }
        System.out.println("[team -> sink]");
        for (FlowEdge e : winList) {
            System.out.printf("%d -> %d : %.1f / %.1f\n", e.from(), e.to(), e.flow(), e.capacity());
        }
         */

        Map<Group, Set<Integer>> cuts = new HashMap<>();
        cuts.put(Group.In, new HashSet<>());
        cuts.put(Group.Out, new HashSet<>());
        for (int teamIndex = 0; teamIndex < teams; teamIndex++) {
            boolean incut = ff.inCut(s + matches + teamIndex);
            Group g = incut ? Group.In : Group.Out;
            cuts.get(g).add(teamIndex);
        }

        return cuts;
    }

    private boolean validElimination(int teamX) {
        return !findEliminators(teamX).isEmpty();
    }

    private List<String> findEliminators(int teamX) {
        int teams = numberOfTeams();
        int bestWinOfX = map[teamX][W] + map[teamX][R];
        List<String> eliminators = new ArrayList<>();
        for (int i = 0; i < teams; i++) {
            if (map[i][W] > bestWinOfX) {
                eliminators.add(idx2Name.get(i));
            }
        }
        return eliminators;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String teamX) {
        int x = teamToIndex(teamX);
        List<String> eliminators = findEliminators(x);
        if (!eliminators.isEmpty()) {
            return eliminators;
        }

        Map<Group, Set<Integer>> cuts = divideByCut(teamX);
        if (isEliminated(teamX)) {
            eliminators.clear();
            Set<Integer> incuts = cuts.get(Group.In);
            for (Integer idx : incuts) {
                eliminators.add(idx2Name.get(idx));
            }
            return eliminators;
        } else {
            return null;
        }
    }
}
