package cytech.artutu.ia.taquin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class AStarSolver {
	
	private Heuristique heuristiqueType;
	
	public AStarSolver(Heuristique heuristiqueType) {
		this.setHeuristiqueType(heuristiqueType);
	}
	
	public void solvePuzzle(int[][] initial) {
		try {
			solverPuzzle(initial);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void solverPuzzle(int[][] initial) throws MatriceSizeException {
		
		 int[][] goal = {
	                {1, 2, 3},
	                {4, 5, 6},
	                {7, 8, 0}
	        };
		
        PuzzleNode initialNode = new PuzzleNode(initial, 0, distancePuzzle(initial, goal), null);

        PriorityQueue<PuzzleNode> openSet = new PriorityQueue<>();
        openSet.add(initialNode);

        Set<PuzzleNode> closedSet = new HashSet<>();

        int state = 1;
        while (!openSet.isEmpty()) {
            PuzzleNode current = openSet.poll();

            if (Arrays.deepEquals(current.puzzle, goal)) {
            	/*
            	 *  Ici le contenu du puzzle le plus proche est égale au contenu final ! Ce qui revient a être a la fin
            	 */
                printSolution(current);
                return;
            }
            if(state % 100000 == 0) {
            	System.out.println("Etape#" + state + " atteinte... Le calcul est toujours en cours...");
            }
            state++;
            closedSet.add(current);

            List<PuzzleNode> neighbors = generateNeighbors(current, goal);

            for (PuzzleNode neighbor : neighbors) {
                if (!closedSet.contains(neighbor)) {
                    if (openSet.contains(neighbor)) {
                        PuzzleNode existingNeighbor = openSet.stream()
                                .filter(n -> Arrays.deepEquals(n.puzzle, neighbor.puzzle))
                                .findFirst().orElse(null);

                        if (existingNeighbor != null && existingNeighbor.cost > neighbor.cost) {
                            openSet.remove(existingNeighbor);
                            openSet.add(neighbor);
                        }
                    } else {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        System.out.println("Aucune solution n'a été trouvée !");
    }
	
	private List<PuzzleNode> generateNeighbors(PuzzleNode node, int[][] goal) throws MatriceSizeException {
        List<PuzzleNode> neighbors = new ArrayList<>();

        // On cherche l'emplacement du zéro dans le puzzle
        int emptyRow = -1, emptyCol = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (node.puzzle[i][j] == 0) {
                    emptyRow = i;
                    emptyCol = j;
                }
            }
        }

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            int newRow = emptyRow + dir[0];
            int newCol = emptyCol + dir[1];

            if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
                int[][] newPuzzle = copyPuzzle(node.puzzle);
                int temp = newPuzzle[emptyRow][emptyCol];
                newPuzzle[emptyRow][emptyCol] = newPuzzle[newRow][newCol];
                newPuzzle[newRow][newCol] = temp;

                PuzzleNode neighbor = new PuzzleNode(newPuzzle,
                        node.cost + 1,
                        distancePuzzle(newPuzzle, goal),
                        node);

                neighbors.add(neighbor);
            }
        }

        return neighbors;
    }

    private int[][] copyPuzzle(int[][] puzzle) {
        int[][] copy = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
            	copy[i][j] = puzzle[i][j];
            }
        }
        return copy;
    }
    
    public static class MatriceSizeException  extends Exception {

		private static final long serialVersionUID = 1L;
		
		public MatriceSizeException(String message) {
			super(message);
		}
    	
    }

    private int distancePuzzle(int[][] puzzle, int[][] goal) throws MatriceSizeException {
    	
    	boolean sameSize = true;
    	if(puzzle.length != goal.length) {
    		sameSize = false;
    	}
    	for(int i = 0; i < puzzle.length; i++) {
    		if(puzzle[i].length != goal[i].length) {
    			sameSize = false;
    		}
    	}
    	
    	if(sameSize == false) {
    		throw new MatriceSizeException("Les matrices comparées dans la distance ne sont pas de même dimension");
    	}
    	
    	if(heuristiqueType.equals(Heuristique.DISTANCE_HAMMING)) {
    		
    		int distance = 0;
    		for(int i = 0; i < puzzle.length; i++) {
    			for(int j = 0; j < puzzle[i].length; j++) {
    				if(puzzle[i][j] != goal[i][j]) {
    					distance++;
    				}
    			}
    		}
    		return distance;
    		
    	} else if(heuristiqueType.equals(Heuristique.DISTANCE_MANHATAN)) {
    		
    		int distance = 0;
    		for(int i = 0; i < puzzle.length; i++) {
    			for(int j = 0; j < puzzle[i].length; j++) {
    				int value = puzzle[i][j];
    				int x = 0;
    				int y = 0;
    				for(int dx = 0; dx < puzzle.length; dx++) {
    					for(int dy = 0; dy < puzzle[i].length; dy++) {
        					int vvalue = goal[dx][dy];
        					if(vvalue == value) {
        						x = dx;
        						y = dy;
        					}
        				}
    				}
    				int dx = i - x;
    				if(dx < 0) {
    					dx = -1 * dx;
    				}
    				int dy = j - y;
    				if(dy < 0) {
    					dy = -1 * dy;
    				}
    				distance += dx + dy;
    			}
    		}
    		
    		return distance;
    		
    	} else {
    		return 0;
    	}
    }

    private void printSolution(PuzzleNode node) {
        List<PuzzleNode> path = new ArrayList<>();
        while (node != null) {
            path.add(node);
            node = node.parent;
        }

        for (int i = path.size() - 1; i >= 0; i--) {
        	System.out.println("Etape n°" + (path.size()-i));
            printPuzzle(path.get(i).puzzle);
            System.out.println();
        }
    }

    public void printPuzzle(int[][] puzzle) {
    	System.out.println();
        for (int i = 0; i < 3; i++) {
        	System.out.print("  ");
            for (int j = 0; j < 3; j++) {
                System.out.print(puzzle[i][j] + " ");
            }
            System.out.println();
        }
    }

	public Heuristique getHeuristiqueType() {
		return heuristiqueType;
	}

	public void setHeuristiqueType(Heuristique heuristiqueType) {
		this.heuristiqueType = heuristiqueType;
	}

}
