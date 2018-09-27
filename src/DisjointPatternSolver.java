import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class DisjointPatternSolver extends Solver{

	public int[][] database;
	
	public DisjointPatternSolver() {
		//Builds the database
		BuildDatabase dB = new BuildDatabase();
		database = dB.database;
	}

	@Override
	public SearchNode solve(Puzzle p) {
		//takes the puzzle and calls A* on it to find a solution
		SearchNode startPuzzle = new SearchNode(p,0, 0,null,0);
		startPuzzle.estimate = heuristic(startPuzzle);
		return AStar(startPuzzle);
	}

	public void printPath(SearchNode node) {
		//recursively prints the path from a node to its root
		if(node.parent != null) {
			printPath(node.parent);
			System.out.println(node.puzzle);
		}
	}

	private int heuristic(SearchNode sn) {
		// finds the heuristic of the node in the database by its hashed value
		int total = 0;
		int hashValue = 0;
		int index2 = 0;
		for(int i = 1; i < 16; i+=3) {

			hashValue = findLocation(sn, i)*256 + findLocation(sn, i+1)*16 + findLocation(sn, i+2);
			index2 = (i/3);
			total += database[hashValue][index2];
		}
		return total;
	}

	private int findLocation(SearchNode sn, int tile) {
		//returns the absolute location of a node in as if stored in an array
		for(int i =0;i<4;i++) {
			for(int j = 0; j <4;j++) {
				if(sn.puzzle.grid[i][j] == tile) {
					return (i*4)+j;
				}
			}
		}
		return 0;
	}

	public SearchNode AStar(SearchNode sn) {
		//Typical A* algorithm
		numNodes = 0;
		PriorityQueue <SearchNode> pq = new PriorityQueue<SearchNode>();
		pq.add(sn);
		HashMap<String,SearchNode> table = new HashMap<>();
		table.put(sn.puzzle.toString(), sn);
		while(!pq.isEmpty()) {
			SearchNode current = pq.poll();
			if(current.estimate == 0)
				return current;
			ArrayList <SearchNode> possibleMoves  = possibleMoves(current); 
			for(SearchNode s: possibleMoves) {
				s.estimate = heuristic(s);
				if(!table.containsKey(s.puzzle.toString())) {
					numNodes+=1;
					pq.add(s);
				}

			}
		}
		return null;
	}

	public static ArrayList<SearchNode> possibleMoves(SearchNode sn){
		//Makes an array list of all possible moves using the canMakeMove given to us
		ArrayList<SearchNode> moves = new ArrayList<SearchNode>();

		if(sn.puzzle.canMakeMove(UP)) {
			Puzzle newP = new Puzzle(sn.puzzle);
			newP.makeMove(UP);
			moves.add(new SearchNode(newP,sn.pathCost+1,0,sn,UP));
		}
		if(sn.puzzle.canMakeMove(DOWN)) {
			Puzzle newP = new Puzzle(sn.puzzle);
			newP.makeMove(DOWN);
			moves.add(new SearchNode(newP,sn.pathCost+1,0,sn,DOWN));
		}
		if(sn.puzzle.canMakeMove(LEFT)) {
			Puzzle newP = new Puzzle(sn.puzzle);
			newP.makeMove(LEFT);
			moves.add(new SearchNode(newP,sn.pathCost+1,0,sn,LEFT));
		}
		if(sn.puzzle.canMakeMove(RIGHT)) {
			Puzzle newP = new Puzzle(sn.puzzle);
			newP.makeMove(RIGHT);
			moves.add(new SearchNode(newP,sn.pathCost+1,0,sn,RIGHT));
		}

		return moves;
	}
}
