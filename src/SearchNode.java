/*
 * Search node class for solving the 15-puzzle
 */

class SearchNode implements Comparable<SearchNode>
{
	public Puzzle puzzle;			// the puzzle state
	public int pathCost;			// current path cost to this state
	public int estimate;			// estimate of # of moves to goal state (used only by informed search methods)
	public SearchNode parent;		// state which preceded this state
	public int action;				// action which produced this state
	
	public SearchNode(Puzzle puz, int p, int e, SearchNode par, int a)
	{
		puzzle = new Puzzle(puz);
		pathCost = p;
		estimate = e;
		parent = par;
		action = a;
	}
	
	public int compareTo(SearchNode other)
	{
		return pathCost + estimate - (other.pathCost + other.estimate);
	}
	
	public boolean equals(Object o)
	{
		SearchNode other = (SearchNode)o;
		for(int i=0; i<puzzle.grid.length; i++)
			for(int j=0; j<puzzle.grid[0].length; j++)
				if(puzzle.grid[i][j] != other.puzzle.grid[i][j])
					return false;
		return true;
	}
	
}