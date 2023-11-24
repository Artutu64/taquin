package cytech.artutu.ia.taquin;

import java.util.Random;

public class Main {
	
	public static void main(String[] args) {
		
		boolean accepted = false;
		boolean random = false;
		
		if(args.length < 10) {
			
			if(args.length >= 2) {
				if(args[1].equalsIgnoreCase("random")) {
					accepted = true;
					random = true;
				}
			}
		} else {
			accepted = true;
			if(args[1].equalsIgnoreCase("random")) {
				random = true;
			}
		}
		
		if(accepted == false) {
			System.out.println(" Format de la commande: java -jar <nomFichier>.jar <distance> <a> <b> <c> <d> <e> <f> <g> <h> <i>");
			System.out.println("");
			System.out.println(" distance <==> 0 = 'distance de Hamming' / 1 = 'distance de manhatan'");
			System.out.println(" a <==> 'random' randomise la grille de départ");
			System.out.println("");
			System.out.println(" Matrice de départ");
			System.out.println("");
			System.out.println("  a b c");
			System.out.println("  d e f");
			System.out.println("  g h i");
			System.out.println(" ");
			System.out.println(" Veuillez noter qu'il faut mettre un 0 a la place vide !");
			System.out.println(" ");
			return ;
		}
		
		 int[][] initialPuzzle;
		 
		 if(random) {
			 initialPuzzle = generateRandomPuzzle();
		 } else {
			 int[] argss = new int[args.length-1];
				
				for(int i = 1; i < args.length; i++) {
					String val = args[i];
					try {
						argss[i-1] = Integer.parseInt(val);
					} catch(Exception e) {
						System.out.println("Un paramêtre n'est pas valide !");
						return ;
					}
				}
			
			boolean soluble = true;
			
			for(int j = 0; j < 9; j++) {
				boolean present = false;
				for(int i = 0; i < argss.length; i++) {
					int val = argss[i];
					if(val == j) {
						present = true;
					}
				}
				if(present == false) {
					soluble = false;
				}
			}
			
			if(soluble == false) {
				System.out.println("Le puzzle saisit ne peut pas être résolu !");
				return ;
			}
			 
			 initialPuzzle = new int[][] {
		                {argss[0], argss[1], argss[2]},
		                {argss[3], argss[4], argss[5]},
		                {argss[6], argss[7], argss[8]}
		        };
		 }
		 
		 Heuristique heuristique = Heuristique.DISTANCE_HAMMING;
		 if(args[0].equals("1")) {
			 heuristique = Heuristique.DISTANCE_MANHATAN;
		 }
		 
	     AStarSolver solver = new AStarSolver(heuristique);
	     
	     System.out.println("Voici le puzzle de départ:");
	     solver.printPuzzle(initialPuzzle);
	     System.out.println("\nDébut de la résolution !\n");
	     
	     solver.solvePuzzle(initialPuzzle);
	}
	
	public static int[][] generateRandomPuzzle(){
		int[][] puzzle = new int[][] {
			{1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
		};
		int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
		Random random = new Random();
		int x = 2;
		int y = 2;
		for(int _it = 0; _it < 100; _it++) {
			int value = random.nextInt(Integer.MAX_VALUE) % directions.length;
			int[] dir = directions[value];
			int xx = x + dir[0];
			int yy = y + dir[1];
			if(xx >= 0 && xx <= 2 && yy >= 0 && yy <= 2) {
				int val = puzzle[xx][yy];
				puzzle[xx][yy] = 0;
				puzzle[x][y] = val;
				x = xx;
				y = yy;
			}
		}
		
		return puzzle;
	}
}
