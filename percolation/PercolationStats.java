import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
    private int numTest;
    private double statMean;
    private double statStddev;
    private double statConfidenceLo;
    private double statConfidenceHi;

    public PercolationStats(int n, int trials){
        if(n < 1 || trials < 1)
            throw new IllegalArgumentException("out of bound");
        numTest = trials - 1;
        Percolation test;
        double total = 0;
        int row, col;
        double[] testNum = new double [trials];
        statStddev = 0;
        while(numTest >= 0){
            test = new Percolation(n);
            while(!test.percolates()){
                row = StdRandom.uniform(n) + 1;
                col = StdRandom.uniform(n) + 1;
                if(!test.isOpen(row, col)){
                    test.open(row, col);
                    testNum[numTest]++;
                }
            }
            testNum[numTest] = testNum[numTest]/(n*n);
            total += testNum[numTest];
            numTest--;
        }
        statMean = total / trials;
        for(int i = 0; i < trials; i++){
            statStddev += (testNum[i] - statMean) * (testNum[i] - statMean);
        }
        statStddev = statStddev / (trials - 1);
        statStddev = Math.sqrt(statStddev);
        statConfidenceLo = statMean - 1.96*statStddev / Math.sqrt(trials);
        statConfidenceHi = statMean + 1.96*statStddev / Math.sqrt(trials);
        double a;
        a = mean();
        a = stddev();
        a = confidenceLo();
        a = confidenceHi();
    }    // perform trials independent experiments on an n-by-n grid


    public double mean(){
        return statMean;
    }                          // sample mean of percolation threshold


    public double stddev(){
        return statStddev;
    }                        // sample standard deviation of percolation threshold


    public double confidenceLo(){
        return statConfidenceLo;
    }                  // low  endpoint of 95% confidence interval


    public double confidenceHi(){
        return statConfidenceHi;
    }                  // high endpoint of 95% confidence interval


    public static void main(String[] args){

    }        // test client (described below)
}
