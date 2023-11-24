package cytech.artutu.ia.taquin;

public class PuzzleNode implements Comparable<PuzzleNode> {
	
    int[][] puzzle;
    int cost;
    int heuristic;
    PuzzleNode parent;

    public PuzzleNode(int[][] puzzle, int cost, int heuristic, PuzzleNode parent) {
        this.puzzle = puzzle;
        this.cost = cost;
        this.heuristic = heuristic;
        this.parent = parent;
    }

    @Override
    public int compareTo(PuzzleNode other) {
        return Integer.compare(cost + heuristic, other.cost + other.heuristic);
    }
}
