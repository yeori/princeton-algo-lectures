import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Solver {
    private Node answer;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        solve(initial);
    }

    private void solve(Board initial) {

        MinPQ<Node> q = new MinPQ<>((a,b) -> a.weight() - b.weight());
        q.insert(new Node(null, initial));

        while (q.size() > 0) {
            Node closest = q.delMin();
            if(closest.data.isGoal()) {
                this.answer = closest;
                break;
            }

            for(Board adj : closest.data.neighbors()) {
                if(!exist(closest, adj, 3)) {
                    q.insert(new Node(closest, adj));
                }
            }
        }
    }

    private boolean exist(Node closest, Board adj, int limit) {
        Node ref = closest;
        while (ref != null) {
            if (ref.data.equals(adj)) {
                return true;
            }
            ref = ref.prev;
            limit--;
            if(limit == 0) {
                break;
            }
        }
        return false;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return answer != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (answer == null) {
            return -1;
        } else {
            return answer.moves;
        }
    }
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if(answer == null) {
            return null;
        }
        return new AnswerItr(answer);
    }

    private static class AnswerItr implements Iterable<Board>, Iterator<Board> {

        Stack<Board> elems ;

        public AnswerItr(Node answer) {
            elems = new Stack<>();

            Node ref = answer;
            while (ref != null) {
                elems.push(ref.data);
                ref = ref.prev;
            }
        }

        @Override
        public Iterator<Board> iterator() {
            return this;
        }

        @Override
        public boolean hasNext() {
            return elems.size() > 0;
        }

        @Override
        public Board next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return elems.pop();
        }
    }

    private static class Node {
        Board data;
        Node prev;
        int moves;

        Node(Node closest, Board board) {
            prev = closest;
            data = board;
            moves = prev == null ? 0 : prev.moves+1;
        }
        int weight() {
            return moves + data.manhattan();
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        // In in = new In("puzzle3x3-20.txt");
        In in = new In("puzzle06.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
