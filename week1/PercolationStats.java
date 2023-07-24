import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double confidence95 = 1.96;

    private int gridSize;
    private int trialsNumber;
    private double[] resultedPerlocationThresholds;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Grid size and trials number cannot be zero or lower");
        }

        gridSize = n;
        trialsNumber = trials;
        resultedPerlocationThresholds = new double[trials];

        for (int i = 0; i < trials; i++) {
            resultedPerlocationThresholds[i] = this.getPerlocationThreshold();
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(resultedPerlocationThresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(resultedPerlocationThresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - confidence95 * Math.sqrt(this.stddev()) / Math.sqrt((double) trialsNumber);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + confidence95 * Math.sqrt(this.stddev()) / Math.sqrt((double) trialsNumber);
    }

    private double getPerlocationThreshold() {
        Percolation simulation = new Percolation(gridSize);

        while (!simulation.percolates()) {
            int randRow = StdRandom.uniformInt(1, gridSize + 1);
            int randCol = StdRandom.uniformInt(1, gridSize + 1);
            simulation.open(randRow, randCol);
        }

        int openSitesNumber = simulation.numberOfOpenSites();
        return (double) openSitesNumber / ((double) gridSize * gridSize);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, t);

        System.out.println(String.format("mean\t\t\t\t\t= %f", stats.mean()));
        System.out.println(String.format("stddev\t\t\t\t\t= %f", stats.stddev()));
        String intervals = String.format("[%f, %f]", stats.confidenceLo(), stats.confidenceHi());
        System.out.println("95% confidence interval\t\t\t= " + intervals);
    }

}
