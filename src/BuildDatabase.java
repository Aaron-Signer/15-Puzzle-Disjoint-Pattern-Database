import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class BuildDatabase {
	public int[][] database = new int[4095][5];

	public BuildDatabase()
	//Tries to load the database from a file. If it doesn't exist it builds it manually and saves it.
	{
		try {
			loadDatabase();
		}
		catch(Exception e)
		{
			buildDatabase();
			saveDatabase();
		}
		if(databaseIsEmpty() || database == null)
		{
			buildDatabase();
			saveDatabase();
		}
	}



	private boolean databaseIsEmpty()
	//Checks if the database is empty
	{
		for(int r = 0; r < database.length; r++)
		{
			for(int c = 0; c < database[0].length; c++)
				if(database[r][c] != 0)
					return false;
		}
		return true;
	}

	private void buildDatabase() {
		//fetches a list of all possible nodes, hashes them, calculates the heuristic, then saves this information to a database
		ArrayList<SearchNode> allNodes = makeAllNodes();
		for(SearchNode n: allNodes) {
			int[] temp = new int[2];
			temp = hasher(n);
			ArrayList<Integer> nums = findNums(n);
			int calc = calculateHeuristic(n, nums.get(0), nums.get(1), nums.get(2));
			database[temp[0]][temp[1]] = calc;
		}
	}

	private ArrayList<Integer> findNums(SearchNode sn) {
		//finds the location of the numbers on the partial puzzle
		ArrayList<Integer> nums = new ArrayList<>();
		for(int i = 0; i <4; i++) {
			for(int j = 0; j < 4;j++) {
				if(sn.puzzle.grid[i][j] != 0) {
					nums.add(sn.puzzle.grid[i][j]);
				}
			}
		}
		return nums;
	}

	private ArrayList<SearchNode> makeAllNodes(){
		//makes every possible combination of partial puzzles. Does not allow for illegal states
		ArrayList<SearchNode> allNodes = new ArrayList<>();
		for(int c = 0;c < 16; c++) {
			for(int b = 0; b < 16; b++) {
				for(int a = 0; a < 16; a++){
					if(a !=b && b!=c && a!=c) {
						Puzzle p = makePartialPuzzle(a, b, c,1,2,3);
						allNodes.add(new SearchNode(p,0,0,null,0));
						p = makePartialPuzzle(a, b, c,4,5,6);
						allNodes.add(new SearchNode(p,0,0,null,0));
						p = makePartialPuzzle(a, b, c,7,8,9);
						allNodes.add(new SearchNode(p,0,0,null,0));
						p = makePartialPuzzle(a, b, c,10,11,12);
						allNodes.add(new SearchNode(p,0,0,null,0));
						p = makePartialPuzzle(a, b, c,13,14,15);
						allNodes.add(new SearchNode(p,0,0,null,0));
					}
				}
			}
		}
		return allNodes;
	}

	private Puzzle makePartialPuzzle(int a, int b, int c, int valA, int valB, int valC){
		//Creates the partial puzzle based on the values and locations generated in makeAllNodes
		int x,y =0;
		int[][] grid = new int[4][4];
		for(int i = 0; i < 4;i++) 
			for(int j = 0;j < 4;j++)
				grid[i][j] = 0;

		x =a%4;
		y = a/4;
		grid[y][x] = valA;

		x =b%4;
		y = b/4;
		grid[y][x] = valB;

		x =c%4;
		y = c/4;
		grid[y][x] = valC;

		Puzzle p = new Puzzle(grid);
		return p;
	}

	private int[] hasher(SearchNode sn) {
		//finds the index and subset that a puzzle belongs in the database
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

	private boolean puzzleContains(SearchNode sn, int n) {
		//checks to see if a number is in a given puzzle
		for(int i =0;i<4;i++) {
			for(int j = 0; j <4;j++) {
				if(sn.puzzle.grid[i][j] == n) {
					return true;
				}
			}
		}
		return false;
	}

	private int calculateHeuristic(SearchNode sn,int num1,int num2,int num3) {
		//Calculates the Manhattan distance + 2 for any collisions it runs into
		int totalHeuristic =0;
		Puzzle p = sn.puzzle;
		for(int r = 0; r < 4; r++)
		{
			for(int c = 0; c < 4; c++)
			{
				if(p.grid[r][c] == num1 || p.grid[r][c] == num2 ||p.grid[r][c] == num3)
				{
					totalHeuristic += (Math.abs((p.grid[r][c]-1)/4 - r)) + (Math.abs((p.grid[r][c]-1)%4 - c));
					if(c<3 && p.grid[r][c] > p.grid[r][c+1] && (p.grid[r][c]-1)/4 == (p.grid[r][c+1]-1)/4  && (p.grid[r][c]-1)/4 ==r && p.grid[r][c+1] !=0) {
						//A collision happens when a tile is greater than a tile to its right and both tiles are supposed to end up in the same row.
						totalHeuristic +=2;
					}
				}
			}
		}
		return totalHeuristic;
	}

	private int findLocation(SearchNode sn, int tile) {
		//Finds absolute location of a number in a puzzle as if it was stored in an array.
		for(int i =0;i<4;i++) {
			for(int j = 0; j <4;j++) {
				if(sn.puzzle.grid[i][j] == tile) {
					return (i*4)+j;
				}
			}
		}
		return 0;
	}

	private void saveDatabase() {
		//saves the database to a file
		try {
			FileOutputStream fileOut =
					new FileOutputStream("database.txt");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(database);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	private void loadDatabase() throws Exception{
		//reads the database from a file
			FileInputStream fileIn = new FileInputStream("database.txt");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			database = (int[][]) in.readObject();
			in.close();
			fileIn.close();	
	}
}
