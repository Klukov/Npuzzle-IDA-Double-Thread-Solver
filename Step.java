import java.util.ArrayList;
import java.util.List;

/**
 * Internal step class used to find the solution
 * It contain Board, number of moves, total cost and reference to parent Step
 */
class Step implements Comparable<Step>{
    private final Board board;
    private final Step parent;
    private final int moves;
    private final int cost;

    /**
     * Constructor for internal class
     * @param board - related Board
     * @param parent - parent step
     * @param move - number of moves from initial Board
     */
    Step(Board board, Step parent, int move) {
        this.board = board;
        this.parent = parent;
        this.moves = move;
        this.cost = board.distanceFromSolution(Application.HEURISTICS) + this.moves;
    }

    /**
     * Constructor for internal class, for initial Board
     * @param board - related Board
     */
    Step(Board board) {
        this(board, null, 0);
    }

    /**
     * @return Board in the step
     */
    Board getBoard() {
        return this.board;
    }

    /**
     * @return parent step
     */
    Step getParent() {
        return this.parent;
    }

    /**
     * @return number of moves from initial board
     */
    int getMoves() {
        return this.moves;
    }

    /**
     * @return total cost of the board
     */
    int getCost() {
        return this.cost;
    }

    /**
     * Function which generating next steps and return them
     * @return list of next step
     */
    List<Step> nextSteps() {
        List<Step> nextSteps = new ArrayList<>();
        Iterable<Board> possibleBoards = this.board.neighbors();
        for (Board possibleBoard : possibleBoards) {
            if (this.getParent() != null) {
                if (possibleBoard.equals(this.getParent().getBoard())) {
                    continue;
                }
            }
            nextSteps.add(new Step(possibleBoard, this, this.moves+1));
        }
        return nextSteps;
    }

    /**
     * Override of basic java function
     * @param other Step object
     * @return difference between the cost and that cost
     */
    @Override
    public int compareTo(Step other) {
        return (this.cost - other.cost);
    }
}