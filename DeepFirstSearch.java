import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class DeepFirstSearch extends Thread{
    private Step initialStep;
    private SearchResult searchResult;
    private boolean isOriginal;
    private long createdNodes;

    /**
     * DFS construtor - setup initial variables
     * @param initial initial step, from which program start searching
     * @param searchResult reference to search results object related to Solver
     * @param isOriginal - true if searching is from initial Board
     */
    DeepFirstSearch(Step initial, SearchResult searchResult, boolean isOriginal) {
        this.initialStep = initial;
        this.searchResult = searchResult;
        this.isOriginal = isOriginal;
        this.createdNodes = 1L;
    }

    /**
     * Main thread function - override thread's run
     */
    @Override
    public void run() {
        int threshold = -1;
        while(!this.searchResult.IsProblemSolved()) {
            threshold = deepFirstSearch(initialStep, threshold);
        }
        if (!this.isOriginal) {
            this.searchResult.setCreatedTwinBoardNodes(this.createdNodes);
            return;
        }
        this.searchResult.setCreatedOriginalBoardNodes(this.createdNodes);
    }

    /**
     * Function realising DFS with specific threshold
     * if solution is found new threshold is equal to -1
     * @param step which is the beginning of the search
     * @param threshold for step cost
     * @return new threshold value
     */
    private int deepFirstSearch(Step step, int threshold) {
        Stack<Step> stack = new Stack<>();
        stack.push(step);
        int newThreshold = Integer.MAX_VALUE;
        while (!stack.isEmpty()) {
            // check if problem has been already solved
            if (this.searchResult.IsProblemSolved()) {
                return -1;
            }
            // check if problem is solved
            Step s = stack.pop();
            if (s.getBoard().isGoal()) {
                saveSolution(s);
                return -1;
            }
            // add to stack children of steps
            for (Step i : s.nextSteps()) {
                if (i.getCost() <= threshold) {
                    stack.push(i);
                    this.createdNodes++;
                } else {
                    if (i.getCost() < newThreshold) {
                        newThreshold = i.getCost();
                    }
                }
            }
        }
        return newThreshold;
    }

    /**
     * save data when final step is found
     * @param finalStep step with final Board
     */
    private void saveSolution(Step finalStep) {
        this.searchResult.setProblemIsSolved();
        if (!this.isOriginal) {
            return;
        }
        Step parent = finalStep;
        ArrayList<Board> solution = new ArrayList<>();
        while (parent != null) {
            solution.add(parent.getBoard());
            parent = parent.getParent();
        }
        Collections.reverse(solution);
        this.searchResult.setSolution(solution);
        this.searchResult.setMoves(finalStep.getMoves());
        this.searchResult.setPuzzlesAreSolvable();
    }
}
