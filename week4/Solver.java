import edu.princeton.cs.algs4.MinPQ;
import java.util.Comparator;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private MinPQ<SearchNode> prioQueue;
    private MinPQ<SearchNode> twinQueue;
    private SearchNode goal;
    private int nrOfMove;

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
        nrOfMove = 0;

        addToQueue(initial, null);
        solve();
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable()

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }

        return nrOfMove;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }

        Stack<Board> stack = new Stack<Board>();

        SearchNode current = goal;
        do {
            stack.push(current.board);
            current = goal.prev;
        } while (current.prev != null);

        return stack;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private void solve(Board initial) {
        boolean isSolved = false;

        do {
            SearchNode searchBoard = prioQueue.min();

            isSolved = searchBoard.board.isGoal();

            if (!isSolved) {
                nrOfMove++;

                for (Board neighbor : searchBoard.board.neighbors()) {
                    // Eliminate the previous board from the next steps
                    if (!neighbor.equal(searchBoard.prev)) {
                        addToQueue(neighbor, searchBoard);
                    }
                }
            } else {
                goal = searchBoard;
            }
        } while (!isSolved);
    }

    private void addToQueue(Board board, SearchNode prev) {
        SearchNode node = new SearchNode(board, nrOfMove, prev);
        prioQueue.insert(node);
    }

    private class ByHamming extends Comparator<SearchNode> {
        public int compare(SearchNode node1, SearchNode node2) {
            double prio1 = node1.board.hamming() + node1.nrOfMove;
            double prio2 = node2.board.hamming() + node2.nrOfMove;
            comparePriorities(prio1, prio2);
        }
    }

    private class ByManhattan extends Comparator<SearchNode> {
        public int compare(SearchNode node1, SearchNode node2) {
            double prio1 = node1.board.manhattan() + node1.nrOfMove;
            double prio2 = node2.board.manhattan() + node2.nrOfMove;
            comparePriorities(prio1, prio2);
        }
    }

    private int comparePriorities(double distance1, double distance2) {
        if (distance1 > distance2) {
            return 1;
        } else if (distance1 < distance2) {
            return -1;
        } else {
            return 0;
        }
    }
}
