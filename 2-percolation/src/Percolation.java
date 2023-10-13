import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF unionFind;
    private WeightedQuickUnionUF full;
    private boolean[][] grid;
    private int openSites;
    private int n;
    private int virtualTop;
    private int virtualBottom;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) throw new IllegalArgumentException();
        this.n = n;
        virtualBottom = n*n;
        virtualTop = virtualBottom + 1;
        unionFind = new WeightedQuickUnionUF((n*n) + 2);
        full = new WeightedQuickUnionUF((n*n) + 2);
        grid = new boolean[n][n];
    }

    // opens the site (row, col){} if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) throw new IllegalArgumentException();
        if (!isOpen(row, col)) {
            grid[row-1][col-1] = true;
            openSites++;
            openNeighbors(row, col);
        }
    }

    private void openNeighbors(int row, int col) {
        int ufPosition = ((row-1) * n) + col-1;
        // opens top
        if ((row != 1)
            && isOpen(row-1, col)) {
            unionFind.union(ufPosition, ufPosition-n);
            full.union(ufPosition, ufPosition-n);
        } else if (row == 1) {
            unionFind.union(ufPosition, virtualTop);
            full.union(ufPosition, virtualTop);
        }
        // open left
        if ((col != 1)
                && isOpen(row, col-1)) {
            unionFind.union(ufPosition, ufPosition-1);
            full.union(ufPosition, ufPosition-1);
        }
        // open right
        if ((col != n)
                && isOpen(row, col+1)) {
            unionFind.union(ufPosition, ufPosition+1);
            full.union(ufPosition, ufPosition+1);
        }
        // open lower
        if ((row != n)
                && isOpen(row+1, col)) {
            unionFind.union(ufPosition, ufPosition+n);
            full.union(ufPosition, ufPosition+n);
        } else if (row == n) {
            unionFind.union(ufPosition, virtualBottom);
        }
    }
    // is the site (row, col){} open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) throw new IllegalArgumentException();
        return grid[row-1][col-1];
    }

    // is the site (row, col){} full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) throw new IllegalArgumentException();
        return isOpen(row, col) && full.find(virtualTop) == full.find(((row-1) * n) + col-1);
    }

    // returns the number of open sites
    public int numberOfOpenSites() { return openSites; }

    // does the system percolate?
    public boolean percolates() {
        return unionFind.find(virtualTop) == unionFind.find(virtualBottom);
    }

    // test client (optional){}
    public static void main(String[] args) {
        Percolation p = new Percolation(6);
        p.open(1, 6);
        System.out.println(p.isFull(1, 6));
        System.out.println(p.isOpen(1, 6));
    }
}