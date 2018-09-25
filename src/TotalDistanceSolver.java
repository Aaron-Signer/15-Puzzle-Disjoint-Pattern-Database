import java.util.*;


public class TotalDistanceSolver extends Solver{

	//	int [][] distances = {{0,0}, {0,1}, {0,2}, {0,3},
	//						  {1,0}, {1,1}, {1,2}, {1,3},
	//			{1,0},
	//			{1,1},
	//			{1,2},
	//			{1,3}}


	//	public HashMap distances = new HashMap();
	//	distances.put(1, {0,0});

	public static int [][] heuristicGrid = new int[4][4];

	@Override
	public SearchNode solve(Puzzle p) {
		// TODO Auto-generated method stub
		return null;
	}

	public static int calculateHeuristic(Puzzle p)
	{
		int totalHeuristic = 0;

		for(int r = 0; r < 4; r++)
		{
			for(int c = 0; c < 4; c++)
			{
				if(p.grid[r][c] != 0)
				{
					//heuristicGrid[r][c] = (Math.abs((p.grid[r][c]-1)/4 - r)) + (Math.abs((p.grid[r][c]-1)%4 - c));
					totalHeuristic += (Math.abs((p.grid[r][c]-1)/4 - r)) + (Math.abs((p.grid[r][c]-1)%4 - c));
				}
			}
		}
		return totalHeuristic;
	}

	public static void printHeuristicGrid()
	{
		for(int r = 0; r < 4; r++)
		{
			for(int c = 0; c < 4; c++)
			{
				System.out.print(heuristicGrid[r][c] + " ");
			}
			System.out.println();
		}
	}

	public static void main(String [] args)
	{
		System.out.println("Hello");
		RandomPuzzle rP = new RandomPuzzle();
		Puzzle p = new Puzzle(rP.generatePuzzle(50));
		System.out.println(p.grid[0][0]);
		System.out.println(p.toString());
		System.out.println();
		System.out.println(calculateHeuristic(p));
	}
}
