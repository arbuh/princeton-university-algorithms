import java.util.Arrays;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private int[][] tiles;
    private int[][] positions;
    private int size;

    private int hamming;
    private int manhattan;

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

        initBoardFromTiles(givenTiles);
        computeHamming();
        computeManhattan();
    }

    // string representation of this board
    public String toString() {
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

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        return hamming;
    }

    private void computeHamming() {
        hamming = 0;
        int tile = 0;

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tile++;

                if (tiles[i][j] != tile && tile != size) {
                    hamming++;
                }
            }
        }
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan;
    }

    private void computeManhattan() {
        manhattan = 0;
        int tile = 0;

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tile++;

                if (tile != size) {
                    int[] position = positions[tile];
                    int tileDistance = Math.abs(i - position[0]) + Math.abs(j - position[1]);
                    manhattan += tileDistance;
                }
            }
        }
    }

    // is this board the goal board?
    public boolean isGoal() {
        int tile = 0;

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tile++;

                if (tiles[i][j] != tile && tile != size) {
                    return false;
                }
            }
        }

        return true;
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
        int y = positions[0][0];
        int x = positions[0][1];

        Queue<Board> neighbors = new Queue<Board>();

        int up = y - 1;
        if (up >= 0) {
            int[][] copy = copyArray(tiles);
            copy[y][x] = copy[up][x];
            copy[up][x] = 0;
            neighbors.enqueue(new Board(copy));
        }

        int down = y + 1;
        if (down < tiles.length) {
            int[][] copy = copyArray(tiles);
            copy[y][x] = copy[down][x];
            copy[down][x] = 0;
            neighbors.enqueue(new Board(copy));
        }

        int left = x - 1;
        if (left >= 0) {
            int[][] copy = copyArray(tiles);
            copy[y][x] = copy[y][left];
            copy[y][left] = 0;
            neighbors.enqueue(new Board(copy));
        }

        int right = x + 1;
        if (right < tiles[y].length) {
            int[][] copy = copyArray(tiles);
            copy[y][x] = copy[y][right];
            copy[y][right] = 0;
            neighbors.enqueue(new Board(copy));
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int tile1 = 0, tile2 = 0, y = 0, x = 0;

        while (tile1 == 0 || tile2 == 0) {
            y = StdRandom.uniformInt(tiles.length);
            x = StdRandom.uniformInt(tiles.length - 1);
            tile1 = tiles[y][x];
            tile2 = tiles[y][x + 1];
        }

        int[][] copy = copyArray(tiles);
        copy[y][x] = tile2;
        copy[y][x + 1] = tile1;
        return new Board(copy);
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

    private void initBoardFromTiles(int[][] arr) {
        tiles = new int[arr.length][];
        positions = new int[arr.length * arr.length][];
        size = arr.length * arr.length;

        for (int i = 0; i < arr.length; i++) {
            tiles[i] = new int[arr[i].length];

            for (int j = 0; j < arr[i].length; j++) {
                int tile = arr[i][j];
                tiles[i][j] = tile;
                positions[tile] = new int[] { i, j };
            }
        }
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

    private int[][] copyArray(int[][] arr) {
        int[][] newArr = new int[arr.length][];
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = Arrays.copyOf(arr[i], arr[i].length);
        }
        return newArr;
    }
}
