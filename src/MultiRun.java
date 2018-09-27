
public class MultiRun {

	public static void main(String[] args) {
		TotalDistanceSolver TDS = new TotalDistanceSolver();
		RandomPuzzle rP = new RandomPuzzle();
		Puzzle p = new Puzzle(rP.generatePuzzle(10));
		System.out.println(p);
		SearchNode result = TDS.solve(p);
		System.out.println(result.puzzle);
		System.out.println("___________________________________________");
		TDS.printPath(result);
		System.out.println(TDS.numNodes);
		
		BuildDatabase bd = new BuildDatabase();
		bd.buildDatabase();
		
		DisjointPatternSolver DPS = new DisjointPatternSolver();
		System.out.println(p);
		DisjointPatternSolver dps = new DisjointPatternSolver();
		SearchNode result2 = dps.solve(p);
		System.out.println(result.puzzle);
		System.out.println("___________________________________________");
		DPS.printPath(result);
		System.out.println(DPS.numNodes);

	}

}
