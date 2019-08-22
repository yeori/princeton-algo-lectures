import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board {

    private final static int[][] NVEC = {
            {-1, 0}, {0, 1}, {1, 0}, {0, -1}
    };

    private final int[][] blocks;
    // private int moves;
    private int holeR, holeC;
    private int [] cost; // [hamming, manhattan]
    // private Board prev;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.blocks = copy(blocks);
        findHole();
        // this.moves = 0;
    }
    private void findHole() {
        int n = dimension();
        for (int ir = 0; ir < n; ir++) {
            for (int ic = 0; ic < n; ic++) {
                if (blocks[ir][ic] == 0) {
                   holeR = ir;
                   holeC = ic;
                   return;
                }
            }
        }
        throw new RuntimeException("no space found!");
    }

    // board dimension n
    public int dimension() {
        return blocks.length;
    }

    private int[] initCost() {
        int n = dimension();
        int hamming = 0;
        int manhattan = 0;
        for (int ir = 0; ir < n; ir++) {
            for (int ic = 0; ic < n; ic++) {
                if (blocks[ir][ic] != 0 && blocks[ir][ic] != ir*n+ic+1) {
                    hamming++;
                }

                if(blocks[ir][ic] != 0) {
                    int row = (blocks[ir][ic]-1)/n;
                    int col = (blocks[ir][ic]-1)%n;
                    manhattan += Math.abs(ir-row) + Math.abs(ic-col);
                }
            }
        }
        int [] cost = {hamming, manhattan};
        return cost;
    }

    // number of blocks out of place
    public int hamming() {
        if(cost != null) {
            return cost[0];
        } else {
            this.cost = initCost();
            return cost[0];
        }
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        if(cost != null) {
            return cost[1];
        } else {
            this.cost = initCost();
            return cost[1];
        }
    }
    // is this board the goal board?
    public boolean isGoal() {
        if (blocks[0][0] != 1) {
            return false;
        }
        int n = dimension();
        for (int num = 1; num <= n*n-1; num++) {
            int ir = (num-1) / n;
            int ic = (num-1) % n;
            if (blocks[ir][ic] != num) {
                return false;
            }
        }
        return true;

    }
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int n = dimension();
        int irow = holeR;
        int icol = holeC;
        while (!isBlock(irow, icol)) {
            irow = (irow+1)%n;
            icol = (icol+1)%n;
        }

        int nidx = 0; // StdRandom.uniform(4); // index of NVEC
        int nrow = irow + NVEC[nidx][0];
        int ncol = icol + NVEC[nidx][1];
        while (!isBlock(nrow,ncol)) {
            nidx = (nidx+1)%4;// StdRandom.uniform(4); // index of NVEC
            nrow = irow + NVEC[nidx][0];
            ncol = icol + NVEC[nidx][1];
        }

        int [][] clone = copy(this.blocks);

        int tmp = clone[irow][icol];
        clone[irow][icol] = clone[nrow][ncol];
        clone[nrow][ncol] = tmp;

        Board twin = new Board(clone);
        twin.holeR = this.holeR;
        twin.holeC = this.holeC;
        // twin.moves = this.moves;
        return twin;
    }
    private boolean isBlock(int ir, int ic) {
        return (ir >= 0 && ir < dimension() && ic >= 0 && ic < dimension() && blocks[ir][ic] != 0);
    }
    private static int[][] copy(int[][] src) {
        int n = src.length;
        int [][] clone = new int[n][n];
        for (int ir = 0; ir < n; ir++) {
            for (int ic = 0; ic < n; ic++) {
                clone[ir][ic] = src[ir][ic];
            }
        }
        return clone;
    }
    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y.getClass() != Board.class) {
            return false;
        }
        Board other = (Board) y;
        if(this.dimension() != other.dimension()) {
            return false;
        }
        int n = dimension();
        for (int ir = 0; ir < n; ir++) {
            for (int ic = 0; ic < n; ic++) {
                if (this.blocks[ir][ic] != other.blocks[ir][ic]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new Neighbor(this);
    }
    private static class Neighbor implements Iterable<Board>, Iterator<Board> {

        Board origin;
        int idx;

        Neighbor(Board board) {
            this.origin = board;
        }

        @Override
        public Iterator<Board> iterator() {
            return this;
        }

        @Override
        public boolean hasNext() {
            while (idx < NVEC.length && !validVector(idx)) {
                idx++;
            }
            return idx < NVEC.length;
        }
        private boolean validVector(int index) {
            int [] vec= NVEC[index];
            int ir = origin.holeR + vec[0];
            int ic = origin.holeC + vec[1];
            return ir >= 0 && ir < origin.dimension() && ic >= 0 && ic < origin.dimension();
        }

        @Override
        public Board next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int [] vec = NVEC[idx];
            int holeR = origin.holeR + vec[0];
            int holeC = origin.holeC + vec[1];

            Board adjBoard = new Board(origin.blocks);
            int [][] clone = adjBoard.blocks;
            int tmp = clone[holeR][holeC];
            clone[holeR][holeC] = clone[origin.holeR][origin.holeC];
            clone[origin.holeR][origin.holeC] = tmp;

            adjBoard.holeR = holeR;
            adjBoard.holeC = holeC;

            // adjBoard.moves = origin.moves+1;
            // adjBoard.prev = origin;
            idx++;
            return adjBoard;
        }
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dimension());
        sb.append('\n');
        for (int ir = 0; ir < dimension(); ir++) {
            for (int ic = 0; ic < dimension(); ic++) {
                sb.append(String.format("%2d ", blocks[ir][ic] ));
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {

    }
}
