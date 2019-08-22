import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufTop;
    private final int sizeOfUf ;
    private boolean [][] sites;
    private int openSites;

    public Percolation(int n) {
        if(n <= 0) {
            throw new IllegalArgumentException();
        }
        sites = new boolean[n+1][n+1]; // reset to false
        /*
        0  1  2  ... n-2   n-1
        T  S  S  ... S     B
         */
        sizeOfUf = 1 + n*n+ 1;
        uf = new WeightedQuickUnionUF(sizeOfUf);
        ufTop = new WeightedQuickUnionUF(sizeOfUf);
    }
    public boolean isOpen(int row, int col) {
        assertRange(row, col);
        return sites[row][col];
    }

    public boolean isFull(int row, int col) {
        assertRange(row, col);
        if (!sites[row][col]) {
            return false;
        }
        int ufIdx = toUfIndex(row, col);
        return ufTop.connected(0, ufIdx);
    }

    private int toUfIndex(int row, int col) {
        return (row-1)*(sites[0].length-1) + (col-1) + 1;
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return uf.connected(0, sizeOfUf-1);
    }
    public void open(int row, int col) {
        assertRange(row, col);
        if(sites[row][col]) {
            return;
        }
        int irow = row;// toRowIndex(row);
        int icol = col;// toColIndex(col);
        sites[irow][icol] = true;
        openSites ++ ;

        final int curIdx = toUfIndex(row, col);
        if (row == 1) {
            uf.union(0, curIdx);
            ufTop.union(0, curIdx);
        }
        if(row == sites.length-1){
            uf.union(curIdx, sizeOfUf-1);
        }
        // uf for testing percolation
        // Set<Integer> roots = new HashSet<>();
        findOpenNeighbors(irow, icol, (nrow, ncol) -> {
            int idxInUf = toUfIndex(nrow, ncol);
            uf.union(curIdx, idxInUf);
        });

        // uf for fullness
        findNeighbors(irow, icol, (nrow, ncol) -> {
            if(sites[nrow][ncol]) {
                int ngbIndex = toUfIndex(nrow, ncol);
                ufTop.union(curIdx, ngbIndex);
            }
        });
    }
    final  private static int [][] vec = {
            {-1, 0}, // up    vector
            {0, 1},  // right vector
            {1, 0},  // down  vector
            {0, -1}  // left  vector
    };
    final private static int ROW=0,COL=1;

    private void findOpenNeighbors(final int irow, final int icol, BiConsumer<Integer, Integer> fn ) {
        int nrow, ncol;
        for (int i = 0 ; i < vec.length; i++) {
            nrow = irow + vec[i][ROW];
            ncol = icol + vec[i][COL];
            if (validLoc(nrow,ncol) && sites[nrow][ncol]) {
                fn.accept(nrow, ncol);
            }
        }
    }

    private void findNeighbors(final int irow, final int icol, BiConsumer<Integer, Integer> fn) {
        int nrow, ncol;
        for (int i = 0 ; i < vec.length; i++) {
            nrow = irow + vec[i][ROW];
            ncol = icol + vec[i][COL];
            if (validLoc(nrow,ncol) && sites[nrow][ncol]) {
                fn.accept(nrow, ncol);
            }
        }
    }

    private void assertRange(int row, int col) {
        if(row < 1 || row >= sites.length || col < 1 || col >= sites.length) {
            throw new IllegalArgumentException();
        }
    }
    private boolean validLoc(int row, int col) {
        return row >= 1 && row < sites.length && col >= 1 && col < sites[0].length;
    }

    private interface BiConsumer<T, U> {
        void accept(T t, U u);
    }
}
