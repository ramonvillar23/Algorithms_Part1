
/*----------------------------------------------------------------
 *  Author:        Ramon Villar
 *  Written:       11/13/2016
 *  Last updated:  11/14/2016
 *
 *  Compilation:   javac-algs4 PercolationStats.java
 *  Execution:     java-algs4 PercolationStats
 *  
 *  Program used to run independent trials of the Percolation assignment.
 *  When finished with all the trials, is calculates mean, stddev and endpoints.
 *
 *  % java-algs4 PercolationStats 200 100
 *  mean 0.5918482500000004
 *  stddev 0.010062006155144286
 *  95% confidence interval 0.5898760967935921, 0.5938204032064087
 *
 *----------------------------------------------------------------*/
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private int trials;
    private double[] results;

    public PercolationStats(int n, int trials) { // perform trials independent
                                                 // experiments on an n-by-n
                                                 // grid
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.trials = trials;
        this.results = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation experiment = new Percolation(n);
            while (!experiment.percolates()) {
                int randomRow = StdRandom.uniform(1, n + 1);
                int randomCol = StdRandom.uniform(1, n + 1);
                if (!experiment.isOpen(randomRow, randomCol)) {
                    experiment.open(randomRow, randomCol);
                }
            }
            double openCount = 0;
            for (int z = 1; z <= n; z++) {
                for (int j = 1; j <= n; j++) {
                    if (experiment.isOpen(z, j)) {
                        openCount++;
                    }
                }
            }
            this.results[i] = openCount / (n * n);
        }
    }

    /**
     * Calculates the mean using the results stored in the array results
     */
    public double mean() { // sample mean of percolation threshold
        return StdStats.mean(results);
    }

    /**
     * Calculated the standard deviation using the results stored in array results
     *
     * Returns Double.NaN if trials == 1.
     */
    public double stddev() { // sample standard deviation of percolation
                             // threshold
        if (this.trials == 1) {
            return Double.NaN;
        }
        return StdStats.stddev(results);
    }

    /**
     * Calculates low endpoint of 95% confidence interval
     */
    public double confidenceLo() { 
        double mean = this.mean();
        double stddev = this.stddev();
        return mean - 1.96 * stddev / (java.lang.Math.sqrt(this.trials));

    }

    /**
     * Calculates high endpoint of 95% confidence interval
     */
    public double confidenceHi() { 
        double mean = this.mean();
        double stddev = this.stddev();
        return mean + 1.96 * stddev / (java.lang.Math.sqrt(this.trials));
    }

    public static void main(String[] args) { // test client (described below)
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.print("mean ");
        StdOut.println(stats.mean());
        StdOut.print("stddev ");
        StdOut.println(stats.stddev());
        StdOut.print("95% confidence interval ");
        StdOut.print(stats.confidenceLo());
        StdOut.print(", ");
        StdOut.println(stats.confidenceHi());
    }
}