/*
 * abstract 15-puzzle solver
 */
public abstract class Solver {
	
	public long numNodes = 0;			// number of nodes added to priority queue
	
	public static final int BLANK = 0;
	public static final int FAILURE = -2;
	
	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;

	public long getNumNodes()
	/*
	 * return current number of nodes genereated
	 */
	{
		return numNodes;
	}
	
	public void printSolution(SearchNode n)
	/*
	 * outputs solution path
	 */
	{
		if (n.parent != null)
			printSolution(n.parent);
		System.out.println(n.puzzle);
	}
	
	public abstract SearchNode solve(Puzzle p);
	/*
	 * solve the given 15-puzzle
	 */
}
