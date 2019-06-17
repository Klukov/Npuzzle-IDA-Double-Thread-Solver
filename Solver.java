import java.util.ArrayList;


public class Solver {

    private SearchResult searchResult = new SearchResult();

    /**
     * Solver for finding a solution to the initial board
     * using the IDA* algorithm
     * @param initialBoard Board from which algorithm should start
     * @param heuristics chosen heuristics for Boards
     */
    public Solver(Board initialBoard, Heuristics heuristics) {

        // check if input variables are correct
        if (initialBoard == null || heuristics == null) {
            throw new IllegalArgumentException();
        }

        // create twin Board to check if it is solvable
        Board twinBoard = initialBoard.twin();

        Step initialOriginalStep = new Step(initialBoard);
        Step initialTwinStep = new Step(twinBoard);

        Thread originalBoardThread = new DeepFirstSearch(initialOriginalStep, searchResult, true);
        Thread twinBoardThread = new DeepFirstSearch(initialTwinStep, searchResult, false);

        originalBoardThread.start();
        twinBoardThread.start();

        try {
            originalBoardThread.join();
            twinBoardThread.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    /**
     * how many moves are from the initial Board to the final one
     * if board is not solvable then return number of moves from twin Board
     * @return number of moves
     */
    public int getMoves() {
        return this.searchResult.getMoves();
    }

    /**
     * @return path from the initial Board to the final one
     */
    public ArrayList<Board> getSolution() {
        return this.searchResult.getSolution();
    }

    /**
     * @return true if the initial Board is solvable
     */
    public boolean isSolvable() {
        return this.searchResult.IsSolvablePuzzles();
    }

    /**
     * Function prints information about created nodes during searching
     */
    public void printNumberOfCreatedNodes() {
        System.out.println("Created nodes from original board: " + this.searchResult.getCreatedOriginalBoardNodes());
        System.out.println("Created nodes from twin board: " + this.searchResult.getCreatedTwinBoardNodes());
    }

    public long createdNodesOriginal() {
        return this.searchResult.getCreatedOriginalBoardNodes();
    }

    public long createdNodesTwin() {
        return this.searchResult.getCreatedTwinBoardNodes();
    }

}
