import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int topRootUFIndex = 0;
    private int bottomRootUFIndex = 1;

    private int gridSize;
    private int nrOfOpenSites;
    private WeightedQuickUnionUF unionFind;

    private boolean[][] grid;
    private int[][] gridToUFMapping;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid size cannot be zero or lower");
        }

        gridSize = n;
        nrOfOpenSites = 0;
        unionFind = new WeightedQuickUnionUF(2 + n * n);
        grid = new boolean[n + 1][n + 1];
        gridToUFMapping = new int[n + 1][n + 1];

        int ufIndex = 2;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                grid[i][j] = false;
                gridToUFMapping[i][j] = ufIndex;
                ufIndex++;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        this.checkArguments(row, col);

        if (this.isOpen(row, col)) {
            return;
        }

        this.grid[row][col] = true;

        int currentSiteUFIndex = this.gridToUFMapping[row][col];

        // The top neighbor
        if (row == 1) {
            this.unionFind.union(topRootUFIndex, currentSiteUFIndex);
        } else if (this.isOpen(row - 1, col)) {
            int topNeighborUFIndex = this.gridToUFMapping[row - 1][col];
            this.unionFind.union(topNeighborUFIndex, currentSiteUFIndex);
        }

        // The right neighbor
        if (col != gridSize && this.isOpen(row, col + 1)) {
            int rightNeighborUFIndex = this.gridToUFMapping[row][col + 1];
            this.unionFind.union(rightNeighborUFIndex, currentSiteUFIndex);
        }

        // The bottom neighbor
        if (row == gridSize) {
            this.unionFind.union(bottomRootUFIndex, currentSiteUFIndex);
        } else if (this.isOpen(row + 1, col)) {
            int bottomNeighborUFIndex = this.gridToUFMapping[row + 1][col];
            this.unionFind.union(bottomNeighborUFIndex, currentSiteUFIndex);
        }

        // The left neighbor
        if (col != 1 && this.isOpen(row, col - 1)) {
            int leftNeighborUFIndex = this.gridToUFMapping[row][col - 1];
            this.unionFind.union(leftNeighborUFIndex, currentSiteUFIndex);
        }

        this.nrOfOpenSites++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkArguments(row, col);
        return this.grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkArguments(row, col);
        int currentSiteUFIndex = this.gridToUFMapping[row][col];
        return this.unionFind.find(topRootUFIndex) == this.unionFind.find(currentSiteUFIndex);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.nrOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.unionFind.find(topRootUFIndex) == this.unionFind.find(bottomRootUFIndex);
    }

    // test client (optional)
    public static void main(String[] args) {

    }

    private void checkArguments(int row, int col) {
        if (row < 1 || row > gridSize)
            throw new IllegalArgumentException(
                    String.format("row must be between 1 and %s, when %s was provided", gridSize, row));
        if (col < 1 || col > gridSize)
            throw new IllegalArgumentException(
                    String.format("col must be between 1 and %s, when %s was provided", gridSize, col));
    }
}
