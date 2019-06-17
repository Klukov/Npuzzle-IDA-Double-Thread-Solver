import java.util.ArrayList;
import java.util.Random;

public final class Board {
    private static final Random random = new Random();
    private final int n;
    private final int m;
    private final int[] board;
    private int hamming;
    private int manhattan;
    private int zeroPosition;
    private Board twin;

    /**
     * Construct a board from an n-by-n array of blocks
     * @param blocks int 2D array with puzzles values
     */
    public Board(int[][] blocks) {
        // (where blocks[i][j] = block in row i, column j)
        this.n = blocks.length;
        this.m = this.n*this.n;
        this.board = new int [this.m];
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                this.board[j+i*n] = blocks[i][j];
            }
        }
        setupBoardProperties();
    }

    /**
     * Create a board from 1D array, used only in class
     * @param blocks int 1D array with puzzles values
     */
    private Board (int[] blocks) {
        this.m = blocks.length;
        this.n = (int) Math.sqrt((double)m);
        this.board = new int [this.m];
        for (int i=0; i<this.m; i++) {
            this.board[i] = blocks[i];
        }
        setupBoardProperties();
    }

    /**
     * Function which calculates Board properties:
     * Hamming, Manhattan and Zero Position
     */
    private void setupBoardProperties() {
        int hamming = 0;
        int manhattan = 0;
        int zeroPosition = this.m-1;
        for (int i=0; i<this.m; i++) {
            //finding zero
            if (this.board[i] == 0) { zeroPosition = i; }
            else {
                // Hamming
                if ((i+1) != this.board[i]) { hamming++; }
                // Manhattan
                // where the point is
                int x1 = (this.board[i]-1)/n;
                int y1 = (this.board[i]-1)%n;
                // where it should be
                int x2 = i/n;
                int y2 = i%n;
                manhattan += Math.abs(x1-x2) + Math.abs(y1-y2);
            }
        }
        this.hamming = hamming;
        this.manhattan = manhattan;
        this.zeroPosition = zeroPosition;
    }

    /**
     * @return board dimension n
     */
    public int dimension() {
        return n;
    }

    /**
     * @return number of blocks out of place
     */
    public int hamming() {
        return this.hamming;
    }

    /**
     * @return sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        return this.manhattan;
    }

    /**
     * @param heuristics given heuristics
     * @return distance from solution in given heuristics
     */
    public int distanceFromSolution(Heuristics heuristics) {
        if (heuristics.equals(Heuristics.HAMMING)) {
            return this.hamming;
        }
        if (heuristics.equals(Heuristics.MANHATTAN)) {
            return this.manhattan;
        }
        return -1;
    }

    /**
     * @return is this board the goal board?
     */
    public boolean isGoal() {
        return this.hamming == 0;
    }

    /**
     * function which create new twin Board and return it
     * @return a board that is obtained by exchanging any pair of blocks
     */
    public Board twin() {
        if (this.twin == null) {
            int x = 0;
            int y = 0;
            while (this.board[x]==this.board[y] || this.board[x]==0 || this.board[y]==0) {
                x = random.nextInt(this.m);
                y = random.nextInt(this.m);
            }
            int[] newBoard = this.board.clone();
            int temp = newBoard[x];
            newBoard[x] = newBoard[y];
            newBoard[y] = temp;
            this.twin = new Board(newBoard);
        }
        return this.twin;
    }

    /**
     * Function checking if Boards are the same
     * @param object of type Board to compare with this Board
     * @return true if Boards are the same
     */
    public boolean equals(Object object) {
        if (object == this) {return true;}
        if (object == null) {return false;}
        if (object.getClass() != this.getClass()) {return false;}
        Board other = (Board)object;
        if (other.dimension() != this.dimension()) {return false;}
        if (other.hamming() != this.hamming()) {return false;}
        if (other.manhattan() != this.manhattan()) {return false;}
        for (int i=0; i < this.m; i++) {
            if (other.board[i] != this.board[i]) {return false;}
        }
        return true;
    }

    /**
     * Function which creates neighbouring boards and return them
     * @return all neighboring boards
     */
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();
        int x0 = (this.zeroPosition)/n;
        int y0 = (this.zeroPosition)%n;
        if (y0 != 0) {
            int[] newBoard = copyBoard();
            newBoard[this.zeroPosition] = newBoard[this.zeroPosition-1];
            newBoard[this.zeroPosition-1] = 0;
            neighbors.add(new Board(newBoard));
        }
        if (y0 != this.n-1) {
            int[] newBoard = copyBoard();
            newBoard[this.zeroPosition] = newBoard[this.zeroPosition+1];
            newBoard[this.zeroPosition+1] = 0;
            neighbors.add(new Board(newBoard));
        }
        if (x0 != 0) {
            int[] newBoard = copyBoard();
            newBoard[this.zeroPosition] = newBoard[this.zeroPosition-this.n];
            newBoard[this.zeroPosition-this.n] = 0;
            neighbors.add(new Board(newBoard));
        }
        if (x0 != this.n-1) {
            int[] newBoard = copyBoard();
            newBoard[this.zeroPosition] = newBoard[this.zeroPosition+this.n];
            newBoard[this.zeroPosition+this.n] = 0;
            neighbors.add(new Board(newBoard));
        }
        return neighbors;
    }

    /**
     * @return 1D copy of board
     */
    private int[] copyBoard() {
        int[] newBoard = new int[this.m];
        for (int i=0; i<this.m; i++) {
            newBoard[i] = this.board[i];
        }
        return newBoard;
    }

    /**
     * @return string representation of this board (in the output format specified below)
     */
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(this.n + "\n");
        for (int i=0; i < this.n; i++ ) {
            for (int j=0; j<this.n; j++) {
                output.append(String.format("%2d ", this.board[j+i*this.n]));
            }
            output.append("\n");
        }
        return output.toString();
    }
}
