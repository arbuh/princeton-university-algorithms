import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public final class Board {
    private int[][] tiles;

    private Board twin = null;

    private int hamming = -1;
    private int manhattan = -1;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] givenTiles) {
        if (givenTiles.length < 2 || givenTiles.length >= 128) {
            throw new IllegalArgumentException("The size of the grid must be between 2 and 128");
        }

        for (int i = 0; i < givenTiles.length; i++) {
            if (givenTiles[i].length != givenTiles.length) {
                throw new IllegalArgumentException("The grid must be a square");
            }
        }

        tiles = copyArray(givenTiles);
    }

    // string representation of this board
    public String toString() {
        return tilesToString(tiles);
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        if (hamming == -1) {
            hamming = calcHamming(tiles);
        }

        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (manhattan == -1) {
            manhattan = calcManhattan(tiles);
        }

        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Board other = (Board) obj;

        if (tiles.length != other.tiles.length) {
            return false;
        }

        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].length != other.tiles[i].length) {
                return false;
            }
        }

        return areTilesEqual(tiles, other.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return calcNeighbors(tiles);
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (twin == null) {
            twin = calcTwin(tiles);
        }

        return twin;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = new int[][] { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
        Board board = new Board(tiles);
        Board goal = new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } });

        StdOut.println("The board:");
        StdOut.println(board.toString());
        StdOut.println("Dimension: " + board.dimension());
        StdOut.println("Hamming distance: " + board.hamming());
        StdOut.println("Manhattan distance: " + board.manhattan());
        StdOut.println("Is goal for a board: " + board.isGoal());
        StdOut.println("Is goal for the goal: " + goal.isGoal());
        StdOut.println("Is equal with the equal one: " + board.equals(new Board(tiles)));
        StdOut.println("Is equal with the goal: " + board.equals(goal));
        StdOut.println("Twin:");
        StdOut.println(board.twin());
        StdOut.println("Neighbors:");
        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor.toString());
        }

        StdOut.println("Neighbors of an almost complete board:");
        Board almostComplete = new Board(new int[][] { { 1, 0, 3 }, { 4, 2, 5 }, { 7, 8, 6 } });
        for (Board neighbor : almostComplete.neighbors()) {
            StdOut.println(neighbor.toString());
        }
    }

    public static String tilesToString(int[][] tiles) {
        StringBuilder str = new StringBuilder();

        str.append(tiles.length);

        for (int i = 0; i < tiles.length; i++) {
            str.append("\n");
            for (int j = 0; j < tiles[i].length; j++) {
                str.append("  " + tiles[i][j]);
            }
        }

        return str.toString();
    }

    private static boolean areTilesEqual(int[][] tiles1, int[][] tiles2) {
        for (int i = 0; i < tiles1.length; i++) {
            for (int j = 0; j < tiles1[i].length; j++) {
                if (tiles1[i][j] != tiles2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int[][] copyArray(int[][] arr) {
        int[][] newArr = new int[arr.length][];
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = arr[i].clone();
        }
        return newArr;
    }

    private static int calcHamming(int[][] tiles) {
        int tilesNumber = calcTilesNumber(tiles);
        int size = tiles.length;

        int hamming = 0;

        for (int i = 0; i < tilesNumber; i++) {
            int tile = i + 1;

            int raw = i / size;
            int column = i % size;
            int actualTile = tiles[raw][column];

            if (actualTile != tile) {
                hamming++;
            }
        }

        return hamming;
    }

    private static int calcManhattan(int[][] tiles) {
        int size = tiles.length;

        int manhattan = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int actualTile = tiles[i][j];

                if (actualTile != 0) {
                    int goalRaw = (actualTile - 1) / size;
                    int goalColumn = (actualTile - 1) % size;
                    int distance = Math.abs(i - goalRaw) + Math.abs(j - goalColumn);
                    manhattan += distance;
                }
            }
        }

        return manhattan;
    }

    private static int calcTilesNumber(int[][] tiles) {
        return tiles.length * tiles.length - 1;
    }

    private static Iterable<Board> calcNeighbors(int[][] tiles) {
        int blankRaw = 0, blankColumn = 0;

        outerloop: for (blankRaw = 0; blankRaw < tiles.length; blankRaw++) {
            for (blankColumn = 0; blankColumn < tiles.length; blankColumn++) {
                if (tiles[blankRaw][blankColumn] == 0) {
                    break outerloop;
                }
            }
        }

        Stack<Board> neighbors = new Stack<Board>();

        int up = blankRaw - 1;
        if (up >= 0) {
            int[][] copy = copyArray(tiles);
            copy[blankRaw][blankColumn] = copy[up][blankColumn];
            copy[up][blankColumn] = 0;
            neighbors.push(new Board(copy));
        }

        int down = blankRaw + 1;
        if (down < tiles.length) {
            int[][] copy = copyArray(tiles);
            copy[blankRaw][blankColumn] = copy[down][blankColumn];
            copy[down][blankColumn] = 0;
            neighbors.push(new Board(copy));
        }

        int left = blankColumn - 1;
        if (left >= 0) {
            int[][] copy = copyArray(tiles);
            copy[blankRaw][blankColumn] = copy[blankRaw][left];
            copy[blankRaw][left] = 0;
            neighbors.push(new Board(copy));
        }

        int right = blankColumn + 1;
        if (right < tiles[blankRaw].length) {
            int[][] copy = copyArray(tiles);
            copy[blankRaw][blankColumn] = copy[blankRaw][right];
            copy[blankRaw][right] = 0;
            neighbors.push(new Board(copy));
        }

        return neighbors;
    }

    private static Board calcTwin(int[][] tiles) {
        int tile1 = 0, tile2 = 0, column = 0, row = 0;

        while (tile1 == 0 || tile2 == 0) {
            row = StdRandom.uniformInt(tiles.length);
            column = StdRandom.uniformInt(tiles.length - 1);
            tile1 = tiles[row][column];
            tile2 = tiles[row][column + 1];
        }

        int[][] copy = copyArray(tiles);
        copy[row][column] = tile2;
        copy[row][column + 1] = tile1;

        return new Board(copy);
    }
}
