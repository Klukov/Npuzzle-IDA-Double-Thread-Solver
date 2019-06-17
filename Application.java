import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

/**
 * Main application class
 * as input should be given path to file with puzzles
 */
public class Application {

    // time properties
    private static final int MILLIS_IN_SECOND = 1000;
    private static final int MILLIS_IN_MINUTE = 60 * MILLIS_IN_SECOND;
    private static final int MILLIS_IN_HOUR = 60 * MILLIS_IN_MINUTE;

    // Program run properties
    public static final Heuristics HEURISTICS = Heuristics.HAMMING;
    private static final boolean RUN_SOLVER = true;
    private static final boolean RUN_SPEED_TESTS = false;
    private static final int SPEED_TEST_ITERATIONS = 100;


    /**
     * Main application function
     * @param args external input - path to file with the board
     */
    public static void main(String[] args) {

        // File reading
        Scanner input;
        Board initial;
        File file = new File(args[0]);
        try {
            input = new Scanner(new BufferedInputStream(new FileInputStream(file)), "UTF-8");
            int n = input.nextInt();
            int[][] blocks = new int[n][n];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    blocks[i][j] = input.nextInt();
            initial = new Board(blocks);
        } catch (Exception e) {
            System.out.println("Something gone wrong with file reading: " + e);
            return;
        }

        // solve the problem and time measure
        if (RUN_SOLVER) { problemSolver(initial); }

        // time speed tests
        if (RUN_SPEED_TESTS) { timeSpeedTests(initial); }
    }

    /**
     * Function which solving the problem and prints the result to console
     * @param board initial board - input to the program
     */
    private static void problemSolver(Board board) {
        Instant start = Instant.now();
        Solver solver = new Solver(board, HEURISTICS);
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        printSolvingTime(timeElapsed);

        // print number of created nodes
        solver.printNumberOfCreatedNodes();

        if (!solver.isSolvable())
            System.out.println("No solution possible");
        else {
            System.out.println("Minimum number of moves = " + solver.getMoves() + "\n");
            for (Board puzzles : solver.getSolution())
                System.out.println(puzzles);
        }
    }

    /**
     * Function which realize time speed test
     * @param board initial board - input to the program
     */
    private static void timeSpeedTests(Board board) {
        long sumTime = 0;
        long sumCreatedNodesOriginal = 0;
        long sumCreatedNodesTwin = 0;
        for (int i = 0; i < SPEED_TEST_ITERATIONS; i++) {
            System.out.println("Iteration " + (i+1) + " started");
            Instant start = Instant.now();
            Solver solver = new Solver(board, HEURISTICS);
            Instant finish = Instant.now();
            sumTime += Duration.between(start, finish).toMillis();
            sumCreatedNodesOriginal += solver.createdNodesOriginal();
            sumCreatedNodesTwin += solver.createdNodesTwin();
        }
        System.out.println();
        sumTime = sumTime/SPEED_TEST_ITERATIONS;
        sumCreatedNodesOriginal = sumCreatedNodesOriginal/SPEED_TEST_ITERATIONS;
        sumCreatedNodesTwin = sumCreatedNodesTwin/SPEED_TEST_ITERATIONS;
        System.out.println("Average Time: " + sumTime);
        System.out.println("Average created nodes from original board: " + sumCreatedNodesOriginal);
        System.out.println("Average created nodes from twin board: " + sumCreatedNodesTwin);
    }

    /**
     * Function prints time in milliseconds in good looking format
     * @param time in milliseconds
     */
    private static void printSolvingTime(long time) {
        System.out.print("Problem solving time: ");
        if (time > MILLIS_IN_HOUR) {
            long hours = time / MILLIS_IN_HOUR;
            long minutes = ( time % MILLIS_IN_HOUR ) / MILLIS_IN_MINUTE;
            long seconds = ( time % MILLIS_IN_HOUR % MILLIS_IN_MINUTE ) / MILLIS_IN_SECOND;
            long milliseconds =  time % MILLIS_IN_HOUR % MILLIS_IN_MINUTE % MILLIS_IN_SECOND;
            System.out.println(hours + " hours, " + minutes + " minutes, " + seconds + " seconds, " + milliseconds + " milliseconds");
        }
        else if (time > MILLIS_IN_MINUTE) {
            long minutes = time / MILLIS_IN_MINUTE;
            long seconds = ( time % MILLIS_IN_MINUTE ) / MILLIS_IN_SECOND;
            long milliseconds =  time % MILLIS_IN_MINUTE % MILLIS_IN_SECOND;
            System.out.println(minutes + " minutes, " + seconds + " seconds, " + milliseconds + " milliseconds");
        }
        else if (time > MILLIS_IN_SECOND) {
            long seconds = time / MILLIS_IN_SECOND;
            long milliseconds =  time % MILLIS_IN_SECOND;
            System.out.println(seconds + " seconds, " + milliseconds + " milliseconds");
        }
        else {
            System.out.println(time + " milliseconds");
        }
    }
}
