import java.util.HashSet;
import java.util.Random;

/*
 * generates random 15-puzzles
 */
public class RandomPuzzle {

	private static HashSet<String> hashtable = new HashSet<>();

	private static void scramble(Puzzle p, int n)
	/*
	 * generate n random moves on puzzle p
	 */
	{
		int count = 0;
		Random rnd = new Random();
		
		while (count < n) {
			int move = rnd.nextInt(4) + 1;
			if (p.canMakeMove(move)) {
				p.makeMove(move);
				if (hashtable.contains(p.toString())) {
					p.unMakeMove(move);
				}
				else {
					hashtable.add(p.toString());
					count++;
				}
			}
		}
	}
	
	public Puzzle generatePuzzle(int n)
	/*
	 * generate a 15-puzzle with solution path <= n
	 */
	{
		int [][] grid = new int[4][4];
	
		int k=1;
		for(int i=0; i<grid.length; i++) {
			for(int j=0; j<grid[0].length; j++)
				grid[i][j] = k++;
		}
		grid[3][3] = Puzzle.BLANK;
		
		Puzzle p = new Puzzle(grid);
		hashtable.clear();
		scramble(p, n);
		return p;
	}

}
