import edu.princeton.cs.algs4.In;

import java.time.Duration;
import java.time.Instant;

/**
 * Main application class
 * as input should be given path to file with puzzles
 */
public class Application {

    public static Heuristics HEURISTICS = Heuristics.HAMMING;

    /**
     * Main application function
     * @param args external input - path to file with the board
     */
    public static void main(String[] args) {

        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the problem and time measure
        Instant start = Instant.now();
        Solver solver = new Solver(initial, HEURISTICS);
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println("Problem solving time " + timeElapsed + " ms");

        // print number of created nodes
        solver.printNumberOfCreatedNodes();

        if (!solver.isSolvable())
            System.out.println("No solution possible");
        else {
            System.out.println("Minimum number of moves = " + solver.getMoves() + "\n");
            for (Board board : solver.getSolution())
                System.out.println(board);

        }
    }
}
