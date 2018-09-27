import java.util.*;


public class TotalDistanceSolver extends Solver{



    public static int [][] heuristicGrid = new int[4][4];
	@Override
	public SearchNode solve(Puzzle p) {
		SearchNode test = new SearchNode(p,0, calculateHeuristic(p),null,0);
		return AStar(test);
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
	
	public SearchNode AStar(SearchNode sn) {
		
		PriorityQueue <SearchNode> pq = new PriorityQueue<SearchNode>();
		pq.add(sn);
		HashMap<String,SearchNode> table = new HashMap<>();
		table.put(sn.puzzle.toString(), sn);
		while(!pq.isEmpty()) {
			SearchNode current = pq.poll();
			System.out.println(current.puzzle);
			if(current.estimate == 0)
				return current;
			ArrayList <SearchNode> possibleMoves  = possibleMoves(current); 
			for(SearchNode s: possibleMoves) {
				if(!table.containsKey(s.puzzle.toString())) {
					numNodes+=1;
					pq.add(s);
					
				}
				
			}
		}
		
		
		return null;
	}
	
	public static ArrayList<SearchNode> possibleMoves(SearchNode sn){
		ArrayList<SearchNode> moves = new ArrayList<SearchNode>();
		
		if(sn.puzzle.canMakeMove(UP)) {
			Puzzle newP = new Puzzle(sn.puzzle);
			newP.makeMove(UP);
			moves.add(new SearchNode(newP,sn.pathCost+1,calculateHeuristic(newP),sn,UP));
		}
		if(sn.puzzle.canMakeMove(DOWN)) {
			Puzzle newP = new Puzzle(sn.puzzle);
			newP.makeMove(DOWN);
			moves.add(new SearchNode(newP,sn.pathCost+1,calculateHeuristic(newP),sn,DOWN));
		}
		if(sn.puzzle.canMakeMove(LEFT)) {
			Puzzle newP = new Puzzle(sn.puzzle);
			newP.makeMove(LEFT);
			moves.add(new SearchNode(newP,sn.pathCost+1,calculateHeuristic(newP),sn,LEFT));
		}
		if(sn.puzzle.canMakeMove(RIGHT)) {
			Puzzle newP = new Puzzle(sn.puzzle);
			newP.makeMove(RIGHT);
			moves.add(new SearchNode(newP,sn.pathCost+1,calculateHeuristic(newP),sn,RIGHT));
		}
		
		return moves;
	}

	
	public void printPath(SearchNode node) {
		if(node.parent != null) {
			printPath(node.parent);
			System.out.println(node.puzzle);
		}
	}
	
	public static ArrayList<SearchNode> savePath(SearchNode node){
		SearchNode cur = node.parent;
		ArrayList<SearchNode> list = new ArrayList<SearchNode>();
		list.add(cur);
		if(cur.parent != null) {
			cur = cur.parent;
			list.add(cur);
		}
		return list;
	}
//	public static void main(String [] args)
//	{
//		
//		RandomPuzzle rP = new RandomPuzzle();
//		Puzzle p = new Puzzle(rP.generatePuzzle(30));
//		System.out.println(p);
//		TotalDistanceSolver tds = new TotalDistanceSolver();
//		SearchNode result = tds.solve(p);
//		System.out.println(result.puzzle);
//		System.out.println("___________________________________________");
//		tds.printPath(result);
//		System.out.println(tds.numNodes);
//		
//
//	}
}
