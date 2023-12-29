import edu.princeton.cs.algs4.MinPQ;
import java.util.Comparator;

public class Solver {
    private MinPQ<SearchNode> prioQueue;

    private class SearchNode {
        private Board board;
        private int nrOfMove;
        private SearchNode prev;

        SearchNode(Board board, int nrOfMove, SearchNode prev) {
            this.board = board;
            this.nrOfMove = nrOfMove;
            this.prev = prev;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Initial board cannot be null");
        }

        prioQueue = new MinPQ<SearchNode>(new ByHamming());
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable()

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves()

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution()

    // test client (see below) 
    public static void main(String[] args) {

    }

    private class ByHamming extends Comparator<SearchNode> {
        public int compare(SearchNode node1, SearchNode node2) {
            double distance1 = node1.board.hamming();
            double distance2 = node2.board.hamming();
            compareDistances(distance1, distance2);
        }
    }

    private class ByManhattan extends Comparator<SearchNode> {
        public int compare(SearchNode node1, SearchNode node2) {
            double distance1 = node1.board.manhattan();
            double distance2 = node2.board.manhattan();
            compareDistances(distance1, distance2);
        }
    }

    private int compareDistances(double distance1, double distance2) {
        if (distance1 > distance2) {
            return 1;
        } else if (distance1 < distance2) {
            return -1;
        } else {
            return 0;
        }
    }
}
