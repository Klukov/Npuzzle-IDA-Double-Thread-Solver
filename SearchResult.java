import java.util.ArrayList;

/**
 * Container class which have all information about searching
 */
class SearchResult {
    private int moves;
    private ArrayList<Board> solution;
    private Boolean isSolvablePuzzles;
    private Boolean isProblemSolved;
    private long createdOriginalBoardNodes;
    private long createdTwinBoardNodes;

    /**
     * constructor which setting all variables to default
     */
    SearchResult() {
        this.moves = -1;
        this.solution = null;
        this.isProblemSolved = false;
        this.isSolvablePuzzles = false;
        this.createdOriginalBoardNodes = 1L;
        this.createdTwinBoardNodes = 1L;
    }

    /**
     * @return number of moves from original board to the solution
     */
    int getMoves() {
        return moves;
    }

    /**
     * Setter for moves, created to set this number when problem is solved
     * @param moves number of moves from original board to the solution
     */
    void setMoves(int moves) {
        this.moves = moves;
    }

    /**
     * @return arrayList with path from the beginning to the final board
     */
    ArrayList<Board> getSolution() {
        return this.solution;
    }

    /**
     * Setter for solution list, created to copy reference to the solution
     * @param solution arrayList with path from the beginning to the final board
     */
    void setSolution(ArrayList<Board> solution) {
        this.solution = solution;
    }

    /**
     * @return true if puzzles are solvable
     */
    Boolean IsSolvablePuzzles() {
        return this.isSolvablePuzzles;
    }

    /**
     * Setter for isSolvable boolean
     * Set to true if problem is solved by using original board
     */
    void setPuzzlesAreSolvable() {
        this.isSolvablePuzzles = true;
    }

    /**
     * @return true if problem is already solved
     */
    Boolean IsProblemSolved() {
        return this.isProblemSolved;
    }

    /**
     * Setter for boolean isProblem solved
     * Set to true if problem is already solved
     */
    void setProblemIsSolved() {
        this.isProblemSolved = true;
    }

    /**
     * @return number of created nodes during searching
     * when searching is going from original board
     */
    Long getCreatedOriginalBoardNodes() {
        return createdOriginalBoardNodes;
    }

    /**
     * Setter for number of original board nodes
     * @param createdOriginalBoardNodes number of created nodes
     *                                  in searching from Original Board
     */
    void setCreatedOriginalBoardNodes(Long createdOriginalBoardNodes) {
        this.createdOriginalBoardNodes = createdOriginalBoardNodes;
    }

    /**
     * @return number of created nodes during searching
     * when searching is going from twin board
     */
    Long getCreatedTwinBoardNodes() {
        return createdTwinBoardNodes;
    }

    /**
     * Setter for number of twin board nodes
     * @param createdTwinBoardNodes number of created nodes
     *                              in searching from Twin Board
     */
    void setCreatedTwinBoardNodes(Long createdTwinBoardNodes) {
        this.createdTwinBoardNodes = createdTwinBoardNodes;
    }
}
