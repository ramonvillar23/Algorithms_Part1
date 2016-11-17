/*----------------------------------------------------------------
 *  Author:        Ramon Villar
 *  Written:       11/13/2016
 *  Last updated:  11/14/2016
 *
 *  Compilation:   javac-algs4 Percolation.java
 *  Execution:     java-algs4 Percolation
 *  
 *  API for the percolation experiment implemented using the
 *  weighted tree union algorithm
 *
 *
 *----------------------------------------------------------------*/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF wqu;
    private int n;
    private int[] size;
    private int[] openOrClosed;

    public Percolation(int n) { // create n-by-n grid, with all sites blocked
        if(n<=0){
            throw new java.lang.IllegalArgumentException();
        }
        wqu = new WeightedQuickUnionUF(n * n + 2); // n indices plus 2 for start
                                                   // and end
        this.n = n;
        this.size = new int[n * n + 2];
        this.openOrClosed = new int[n * n + 2];
        for (int i = 0; i < n * n + 2; i++) {
            this.size[i] = 1;
        }
        this.openBeginAndEnd();
    }

    /**
     * Function to check against invalid values of row and col
     * 
     * Throw IndexOutOfBoundsException if less than 0 or higher than n
     */
    private void checkInput(int row, int col) {
        if (row < 1 || row > this.n || col < 1 || col > this.n) {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    /**
     * Converts row and col (starting from 1) to an index in the 1-D Array
     */
    private int rowColToIndex(int row, int col) {
        row--;
        col--;
        return row * this.n + col;
    }

    /**
     * Opens the imaginary start and end points
     */
    private void openBeginAndEnd() {
        this.openOrClosed[n * n] = 1;
        this.openOrClosed[n * n + 1] = 1;
    }

    /**
     * Merges two elements in the given indexes
     */
    private void quickMerge(int index1, int index2) {
        wqu.union(index1, index2);
    }

    /**
     * Returns true if the two elements in the given indexes are in the same
     * connected component
     */
    private boolean quickFind(int index1, int index2) {
        if (wqu.find(index1) == wqu.find(index2)) {
            return true;
        }
        return false;
    }

    /**
     * Open a square in the percolation graph
     * Checks if the surrounding squares are open, and if so, merges
     */
    public void open(int row, int col) { // open site (row, col) if it is not
                                         // open already
        checkInput(row, col);
        int indexToOpen = rowColToIndex(row, col);
        this.openOrClosed[indexToOpen] = 1;
        if (row > 1) { // check up
            int indexToCheck = rowColToIndex(row - 1, col);
            if (this.openOrClosed[indexToCheck] == 1) {
                this.quickMerge(indexToOpen, indexToCheck);
            }
        } else {
            this.quickMerge(indexToOpen, n * n);
        }
        if (col > 1) { // check left
            int indexToCheck = rowColToIndex(row, col - 1);
            if (this.openOrClosed[indexToCheck] == 1) {
                this.quickMerge(indexToOpen, indexToCheck);
            }
        }
        if (row < n) { // check down
            int indexToCheck = rowColToIndex(row + 1, col);
            if (this.openOrClosed[indexToCheck] == 1) {
                this.quickMerge(indexToOpen, indexToCheck);
            }
        } else {
            this.quickMerge(indexToOpen, n * n + 1);
        }
        if (col < n) { // check right
            int indexToCheck = rowColToIndex(row, col + 1);
            if (this.openOrClosed[indexToCheck] == 1) {
                this.quickMerge(indexToOpen, indexToCheck);
            }
        }
    }

    /**
     * Checks if a certain square is open
     */
    public boolean isOpen(int row, int col) { // is site (row, col) open?
        checkInput(row, col);
        if (this.openOrClosed[this.rowColToIndex(row, col)] == 1) {
            return true;
        }
        return false;
    }

    /**
     * Checks that the element at row,col is percolated from the start
     */
    public boolean isFull(int row, int col) { // is site (row, col) full?
        checkInput(row, col);
        return quickFind(rowColToIndex(row, col), n * n);

    }

    /**
     * Returns true if the system percolates from top to bottom
     */
    public boolean percolates() { // does the system percolate?
        return quickFind(n * n, n * n + 1);
    }

    public static void main(String[] args) { // test client (optional)
    }
}