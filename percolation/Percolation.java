import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;
    private int dimension;
    private int numOpen;
    private WeightedQuickUnionUF id;

    public Percolation(int n){
        if(n <= 0){
            throw new IllegalArgumentException("wrong input");
        }
        dimension = n;
        id = new WeightedQuickUnionUF((n+1) * (n+1));
        for(int i = 1; i < n+1; i++){
            id.union(dimension + 1 + i, 0);
            id.union(dimension * (dimension + 1), dimension * (dimension + 1) + i);
        }
        numOpen = 0;
        grid = new int[n+1][n+1];
        for(int i = 0; i < n+1; i++){
            for(int j = 0; j < n+1; j++){
                grid[i][j] = 0;
            }
        }
        grid[dimension][0] = 1;
    }                // create n-by-n grid, with all sites blocked


    public    void open(int row, int col){
        if(row < 1 || col < 1 || row > dimension || col > dimension){
            throw new IllegalArgumentException("out of bound");
        }
        if(grid[row][col] == 0){
            grid[row][col] = 1;
            numOpen++;
            if(grid[row-1][col] == 1){
            id.union(row*(dimension + 1) + col, (row - 1)*(dimension + 1) + col);
        }
        if(row < dimension){
            if(grid[row+1][col] == 1){
                id.union((row + 1)*(dimension + 1) + col, row*(dimension + 1) + col);
            }
        }
        if(grid[row][col-1] == 1){
            id.union(row*(dimension + 1) + col, row*(dimension + 1) + col - 1);
        }
        if(col < dimension){
            if(grid[row][col+1] == 1){
                id.union(row*(dimension + 1) + col + 1, row*(dimension + 1) + col);
            }
        }
        }

    }    // open site (row, col) if it is not open already


    public boolean isOpen(int row, int col){
        if(row == dimension && col == 0)
            return true;
        if(row < 1 || col < 1 || row > dimension || col > dimension){
            throw new IllegalArgumentException("out of bound");
        }
        if(grid[row][col] == 1){
            return true;
        }else{
            return false;
        }
    }  // is site (row, col) open?


    public boolean isFull(int row, int col){
        if(isOpen(row, col) && id.connected(row*(dimension + 1) + col, 0)){
            return true;
        }else{
            return false;
        }
    }  // is site (row, col) full?


    public     int numberOfOpenSites(){
        return numOpen;
    }       // number of open sites


    public boolean percolates(){
        // for(int i = 1; i < dimension + 1; i++){
        //     if(isFull(dimension, i))
        //         return true;
        // }
        // return false;
        if(isFull(dimension,0))
            return true;
        else
            return false;
    }              // does the system percolate?


    public static void main(String[] args){

    }   // test client (optional)
}
