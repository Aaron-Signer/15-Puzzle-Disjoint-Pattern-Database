import java.util.*;


public class TotalDistanceSolver extends Solver{
	
	@Override
	public SearchNode solve(Puzzle p) {
		SearchNode startPuzzle = new SearchNode(p,0, calculateHeuristic(p),null,0);
		return AStar(startPuzzle);
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
					totalHeuristic += (Math.abs((p.grid[r][c]-1)/4 - r)) + (Math.abs((p.grid[r][c]-1)%4 - c));
				}
			}
		}
		return totalHeuristic;
	}

	public SearchNode AStar(SearchNode sn) {

		numNodes = 0;
		PriorityQueue <SearchNode> pq = new PriorityQueue<SearchNode>();
		pq.add(sn);
		HashMap<String,SearchNode> table = new HashMap<>();
		table.put(sn.puzzle.toString(), sn);
		while(!pq.isEmpty()) {
			SearchNode current = pq.poll();
			if(current.estimate == 0)
				return current;
			ArrayList <SearchNode> possibleMoves  = generatePossibleMoves(current); 
			for(SearchNode s: possibleMoves) {
				if(!table.containsKey(s.puzzle.toString())) {
					numNodes+=1;
					pq.add(s);
				}
			}
		}


		return null;
	}

	public ArrayList<SearchNode> generatePossibleMoves(SearchNode sn){
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
		System.out.println("Done");
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
}
