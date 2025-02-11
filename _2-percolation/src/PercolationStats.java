import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE = 1.96;
    private double[] percolationThreshold;
    private double[] stdDevValues;
    private int n;
    private int trials;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.n = n;
        this.trials = trials;
        if (n < 1 || trials < 1) throw new IllegalArgumentException();
        percolationThreshold = new double[trials];
        stdDevValues = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                percolation.open(StdRandom.uniformInt(n) + 1, StdRandom.uniformInt(n) + 1);
            }
            percolationThreshold[i] = (double) percolation.numberOfOpenSites() / (double) (n*n);
        }
        for (int i = 0; i < trials; i++) {
            stdDevValues[i] = Math.pow(percolationThreshold[i] - mean(), 2);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolationThreshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() { return StdStats.stddev(stdDevValues); }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((CONFIDENCE * stddev())/Math.sqrt(n));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((CONFIDENCE * stddev())/Math.sqrt(trials));
    }

    // test client (see below){}
    public static void main(String[] args) {
        int gridSize = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(gridSize, trials);
        StdOut.println("mean = " + percolationStats.mean());
        StdOut.println("stdDev = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
    }
}