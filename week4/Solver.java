import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
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

        Comparator<SearchNode> priorityFunction = new ByHamming();

        prioQueue = new MinPQ<SearchNode>(priorityFunction);
        prioQueue = new MinPQ<SearchNode>(priorityFunction);
        nrOfMove = 0;

        solve(initial);
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return goal != null;
    }

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
        while (current != null) {
            stack.push(current.board);
            current = goal.prev;
        }

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
        // We need a twin board to determine if the puzzle can be solved
        Board twin = initial.twin();

        boolean isMainSolved = false;
        boolean isTwinSolved = false;
        boolean isThereSolution = false;

        prioQueue.insert(new SearchNode(initial, nrOfMove, null));
        twinQueue.insert(new SearchNode(twin, nrOfMove, null));

        do {
            SearchNode mainSearchBoard = prioQueue.min();
            SearchNode twinSearchBoard = twinQueue.min();

            isMainSolved = mainSearchBoard.board.isGoal();
            isTwinSolved = twinSearchBoard.board.isGoal();

            isThereSolution = !(isMainSolved || isTwinSolved);

            if (!isThereSolution) {
                nrOfMove++;

                for (Board neighbor : mainSearchBoard.board.neighbors()) {
                    if (!neighbor.equals(mainSearchBoard.prev)) {
                        prioQueue.insert(new SearchNode(neighbor, nrOfMove, mainSearchBoard));
                    }
                }

                for (Board neighbor : twinSearchBoard.board.neighbors()) {
                    if (!neighbor.equals(twinSearchBoard.prev)) {
                        twinQueue.insert(new SearchNode(neighbor, nrOfMove, twinSearchBoard));
                    }
                }
            }

            if (isMainSolved) {
                goal = mainSearchBoard;
            }
        } while (!isThereSolution);
    }

    private class ByHamming implements Comparator<SearchNode> {
        public int compare(SearchNode node1, SearchNode node2) {
            double prio1 = node1.board.hamming() + node1.nrOfMove;
            double prio2 = node2.board.hamming() + node2.nrOfMove;
            return comparePriorities(prio1, prio2);
        }
    }

    private class ByManhattan implements Comparator<SearchNode> {
        public int compare(SearchNode node1, SearchNode node2) {
            double prio1 = node1.board.manhattan() + node1.nrOfMove;
            double prio2 = node2.board.manhattan() + node2.nrOfMove;
            return comparePriorities(prio1, prio2);
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
