public class Board {
    private int[][] tiles;
    private int[][] goal;
    private int[][] goalPositions;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles.length < 2 || tiles.length <= 128) {
            throw new IllegalArgumentException("The size of the grid must be between 2 and 128");
        }

        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].length != tiles.length) {
                throw new IllegalArgumentException("The grid must be a square");
            }
        }

        tiles = copyArray(tiles);
        createGoal();
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
        int distance = 0;

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] != goal[i][j]) {
                    distance++;
                }
            }
        }

        return distance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int distance = 0;

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                int tile = tiles[i][j];
                int[] goalPosition = goalPositions[tile];
                int tileDistance = Math.abs(i - goalPosition[0]) + Math.abs(j - goalPosition[1]);
                distance =+ tileDistance;
            }
        }

        return distance;
    }

    // is this board the goal board?
    public boolean isGoal(){
        ???
    }

    // does this board equal y?
    public boolean equals(Object y){
        ???
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        ???
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){
        ???
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

    private int[] copyArray(int[][] arr) {
        int[][] copy = new int[arr.length][];
        for (int i = 0; i < arr.length; i++)
            copy[i] = copyArray(arr[i]);
        return copy;
    }

    private int[] copyArray(int[] arr) {
        int[] copy = new int[arr.length];
        for (int i = 0; i < arr.length; i++)
            copy[i] = arr[i];
        return copy;
    }

    private void createGoal() {
        int tile = 0;
        int size = tiles.length * tiles.length;

        for (int i = 0; i < tiles.length; i++) {
            tile++;

            for (int j = 0; j < tiles[i].length; j++) {
                // The last tile is the goal position is the empty tile
                if (tile == size) {
                    tile = 0;
                }

                goal[i][j] = tile;
                goalPositions[tile] = new int[] { i, j };
            }
        }
    }
}
