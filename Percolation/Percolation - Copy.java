public class Percolation {
    private int grid[];
    private int size[];
    private int n;
    public Percolation(int n){                // create n-by-n grid, with all sites blocked
        if(n<=0){
            throw new java.lang.IllegalArgumentException();
        }
        this.grid = new int[n*n + 2];
        this.size = new int[n*n + 2];
        this.n = n;
        int count = 0;
        for (int i = 0 ; i < n * n + 2; i++) {
            this.size[i] = 1;
            this.grid[i] = count++;
        }
    }
    private void checkInput(int row, int col){
        if(row < 1 || row > this.n || col < 1 || col > this.n){
            throw new java.lang.IndexOutOfBoundsException();
        }
    }
    
    private int rowColToIndex(int row, int col){
        return row * this.n + col;
    }
    private int findRoot(int index){
        while(index != this.grid[index]){
            index = this.grid[index];
        }
        return index;
    }
    private void quickMerge(int index1, int index2){
        int root1 = findRoot(index1);
        int root2 = findRoot(index2);
        if(this.size[index1] > this.size[index2]){
            this.grid[root2] = root1;
        }
        else{
            this.grid[root1] = root2;
        }
    }
    
    private boolean quickFind(int index1, int index2){
        if(findRoot(index1) == findRoot(index2)){
            return true;
        }
        return false;
    }
    public void open(int row, int col){       // open site (row, col) if it is not open already
        checkInput(row, col);
        row--;
        col--;
        int indexToOpen = rowColToIndex(row,col);
        if(row - 1 >= 0){
            this.quickMerge(indexToOpen, rowColToIndex(row-1,col));
        }
        else{
            this.quickMerge(indexToOpen, this.n*this.n);
        }
        if(col - 1 >= 0){
            this.quickMerge(indexToOpen, rowColToIndex(row,col-1));
        }
        if(row+1<n){
            this.quickMerge(indexToOpen, rowColToIndex(row+1,col));
        }
        else{
            this.quickMerge(indexToOpen, this.n*this.n + 1);
        }
        if(col+1<n){
            this.quickMerge(indexToOpen, rowColToIndex(row, col+1));
        }
    }
    public boolean isOpen(int row, int col){  // is site (row, col) open?
        checkInput(row,col);
        row --;
        col --;
        if(size[this.rowColToIndex(row,col)] > 1){
            return true;
        }
        return false;
    }
    public boolean isFull(int row, int col){  // is site (row, col) full?
        checkInput(row,col);
        row--;
        col--;
        return quickFind(rowColToIndex(row,col), n*n);
            
    }
    public boolean percolates(){              // does the system percolate?
        return quickFind(n*n, n*n+1);
    }
    public static void main(String[] args){   // test client (optional)
    }
}