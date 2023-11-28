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

    /*
     * Patch concernant la vitesse d'éxécution
    */
    @Override
    public boolean equals(Object object) {
    	if(object instanceof PuzzleNode) {
    		PuzzleNode node = (PuzzleNode) object;
    		return Arrays.deepEquals(this.puzzle, node.puzzle);
    	}
    	return false;
    }

    @Override
    public int compareTo(PuzzleNode other) {
        return Integer.compare(cost + heuristic, other.cost + other.heuristic);
    }
}
