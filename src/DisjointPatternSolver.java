import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class DisjointPatternSolver extends Solver{

	public static int totalNodes;
	public static int[][] database;
	public static void main(String[] args) {
		totalNodes = 0;
		database = readDatabase();
		RandomPuzzle rP = new RandomPuzzle();
		Puzzle p = new Puzzle(rP.generatePuzzle(10));
		System.out.println(p);
		DisjointPatternSolver dps = new DisjointPatternSolver();
		SearchNode result = dps.solve(p);
		System.out.println(result.puzzle);
		System.out.println("___________________________________________");
		printPath(result);
		System.out.println(totalNodes);
	}

	@Override
	public SearchNode solve(Puzzle p) {
		SearchNode test = new SearchNode(p,0, 0,null,0);
		test.estimate = hRistic(test);
		return AStar(test);
	}

//	public static int calculateHeuristic(Puzzle p) {
		
//		int[] index = hasher(new SearchNode(p, 0, 0, null, 0));
//		int index2 = 0;
//		for(int i = 0; i < 5;i++)
//			index2+= database[index[0]][i];
//		System.out.println(index2);
//		return index2;
	
	public static void printPath(SearchNode node) {
		if(node.parent != null) {
			printPath(node.parent);
			System.out.println(node.puzzle);
		}
	}
	public static int[] hasher(SearchNode sn) {
		int ret[] = new int[2];
		int index1 = 0;
		int index2 = 0;
		for(int i = 1; i < 16; i+=3) {
			if(puzzleContains(sn, i)) {
			index1 = findLocation(sn, i)*256 + findLocation(sn, i+1)*16 + findLocation(sn, i+2);
			index2 = (i/3);
			}
			if(index1 !=0)
				ret[0] = index1;
			ret[1] = index2;
		}
		return ret;
	}
	
	
	public static int hRistic(SearchNode sn) {
		int total = 0;
		int index1 = 0;
		int index2 = 0;
		for(int i = 1; i < 16; i+=3) {
	
			index1 = findLocation(sn, i)*256 + findLocation(sn, i+1)*16 + findLocation(sn, i+2);
			index2 = (i/3);
			total += database[index1][index2];
			}
		System.out.println(total);	
		return total;
	}
	
	
	public static int findLocation(SearchNode sn, int tile) {
		for(int i =0;i<4;i++) {
			for(int j = 0; j <4;j++) {
				if(sn.puzzle.grid[i][j] == tile) {
					return (i*4)+j;
				}
			}
		}
		//System.out.println("Couldn't find " + tile);
		return 0;
	}
	
	public static boolean puzzleContains(SearchNode sn, int n) {
		for(int i =0;i<4;i++) {
			for(int j = 0; j <4;j++) {
				if(sn.puzzle.grid[i][j] == n) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static SearchNode AStar(SearchNode sn) {

		PriorityQueue <SearchNode> pq = new PriorityQueue<SearchNode>();
		pq.add(sn);
		HashMap<String,SearchNode> table = new HashMap<>();
		table.put(sn.puzzle.toString(), sn);
		while(!pq.isEmpty()) {
			SearchNode current = pq.poll();
			//System.out.println(current.puzzle);
			if(current.estimate == 0)
				return current;
			ArrayList <SearchNode> possibleMoves  = possibleMoves(current); 
			for(SearchNode s: possibleMoves) {
				s.estimate = hRistic(s);
				if(!table.containsKey(s.puzzle.toString())) {
					totalNodes+=1;
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

	public static int[][] readDatabase() {
		 int[][] database;
	      try {
	          FileInputStream fileIn = new FileInputStream("database.txt");
	          ObjectInputStream in = new ObjectInputStream(fileIn);
	          database = (int[][]) in.readObject();
	          in.close();
	          fileIn.close();
	       } catch (IOException i) {
	          i.printStackTrace();
	          return null;
	       } catch (ClassNotFoundException c) {
	          System.out.println("Array? class not found");
	          c.printStackTrace();
	          return null;
	       }
	      return database;
		
	}

}
