import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private SearchNode solutionNode;
    private Iterable<Board> solution;

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

        solve(initial);
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solutionNode != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }

        return solutionNode.nrOfMove;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
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
        Comparator<SearchNode> priorityFunction = new ByManhattan();
        MinPQ<SearchNode> prioQueue = new MinPQ<SearchNode>(priorityFunction);
        MinPQ<SearchNode> twinQueue = new MinPQ<SearchNode>(priorityFunction);

        // We need a twin board to determine if the puzzle can be solved
        Board twin = initial.twin();

        boolean isMainSolved = false;
        boolean isTwinSolved = false;
        boolean isThereSolution = false;

        prioQueue.insert(new SearchNode(initial, 0, null));
        twinQueue.insert(new SearchNode(twin, 0, null));

        do {
            SearchNode mainSearchBoard = prioQueue.delMin();
            SearchNode twinSearchBoard = twinQueue.delMin();

            isMainSolved = mainSearchBoard.board.isGoal();
            isTwinSolved = twinSearchBoard.board.isGoal();

            isThereSolution = isMainSolved || isTwinSolved;

            if (!isThereSolution) {
                for (Board neighbor : mainSearchBoard.board.neighbors()) {
                    if (mainSearchBoard.prev == null || !neighbor.equals(mainSearchBoard.prev.board)) {
                        prioQueue.insert(new SearchNode(neighbor, mainSearchBoard.nrOfMove + 1, mainSearchBoard));
                    }
                }

                for (Board neighbor : twinSearchBoard.board.neighbors()) {
                    if (mainSearchBoard.prev == null || !neighbor.equals(twinSearchBoard.prev.board)) {
                        twinQueue.insert(new SearchNode(neighbor, mainSearchBoard.nrOfMove + 1, twinSearchBoard));
                    }
                }
            }

            if (isMainSolved) {
                solutionNode = mainSearchBoard;
                solution = calcSolution(mainSearchBoard);
            }
        } while (!isThereSolution);
    }

    private class ByManhattan implements Comparator<SearchNode> {
        public int compare(SearchNode node1, SearchNode node2) {
            double prio1 = node1.board.manhattan() + node1.nrOfMove;
            double prio2 = node2.board.manhattan() + node2.nrOfMove;
            return comparePriorities(prio1, prio2);
        }
    }

    private static int comparePriorities(double distance1, double distance2) {
        if (distance1 > distance2) {
            return 1;
        } else if (distance1 < distance2) {
            return -1;
        } else {
            return 0;
        }
    }

    private static Iterable<Board> calcSolution(SearchNode solutionNode) {
        Stack<Board> stack = new Stack<Board>();

        SearchNode current = solutionNode;
        while (current != null) {
            stack.push(current.board);
            current = current.prev;
        }

        return stack;
    }
}
