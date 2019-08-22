import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int[] count;
    private final int size;

    // perform trials independent experiments on an n-by-n grid

    private Double mean = null;
    private Double stddev = null;
    public PercolationStats(int n, int trials) {
        if(n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.count = new int[trials];
        this.size = n * n ;
        run(n, trials);
    }

    private void run(int n, int trials) {

        for(int i = 0; i < trials ; i++) {
            int cnt = trial(n);
            count[i] = cnt;
        }
    }

    private int trial(int n) {
        Percolation pc = new Percolation(n);
        int limit = n * n ;
        int cnt = 0;
        int [] visited = new int[limit];
        while(!pc.percolates()) {
            int pos = StdRandom.uniform(limit);
            while (visited[pos] != 0) {
                pos = StdRandom.uniform(limit);
            }
            int row = pos / n + 1;
            int col = pos % n + 1;
            pc.open(row, col);
            cnt++;
            visited[pos] ++;
        }
        return cnt;
    }

    // sample mean of percolation threshold
    public double mean() {
        if(mean != null) {
            return mean.doubleValue();
        }
        StdStats.mean(new int[0]); // fake call!
        double sum = 0;
        for (int i = 0; i < count.length; i++) {
            sum += count[i];
        }

        return mean = sum / (size * count.length);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if( stddev != null) {
            return stddev.doubleValue();
        }
        StdStats.stddev(new int[0]); // fake call!
        double mean = mean();
        double sqrsum = 0;
        for (int i = 0; i < count.length; i++) {
            double m = 1D * count[i] / size ;
            sqrsum += (m - mean)*(m - mean);
        }

        return stddev = Math.sqrt(sqrsum / (count.length-1));
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        double stddev = stddev();
        double mean = mean();
        double rootT = Math.sqrt(count.length);
        return mean - 1.96 * stddev / rootT;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double stddev = stddev();
        double mean = mean();
        double rootT = Math.sqrt(count.length);
        return mean + 1.96 * stddev / rootT;
    }

    // test client (described below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, T);
        /*
        mean                    = 0.5929934999999997
        stddev                  = 0.00876990421552567
        95% confidence interval = [0.5912745987737567, 0.5947124012262428]
         */
        System.out.printf("mean                    = %.16f\n", ps.mean());
        System.out.printf("stddev                  = %.16f\n", ps.stddev());
        System.out.printf("95%% confidence interval = [%.16f,%.16f]", ps.confidenceLo(), ps.confidenceHi());


    }
}
