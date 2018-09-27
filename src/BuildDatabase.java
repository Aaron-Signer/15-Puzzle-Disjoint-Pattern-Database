import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class BuildDatabase {
	static int[][] database = new int[4095][6];
	public static void main(String[] args) {
		
		RandomPuzzle rP = new RandomPuzzle();
		Puzzle p = new Puzzle(rP.generatePuzzle(3));
		SearchNode node = new SearchNode(p, 1, 0, null, 0);
		System.out.println(p);
		hasher(node);
		ArrayList<SearchNode> allNodes = makeAllNodes();
		for(SearchNode n: allNodes) {
			System.out.println(n.puzzle);
		}
		System.out.println(allNodes.size());
	}
	
	
	public static ArrayList<SearchNode> makeAllNodes(){
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
	
	public static Puzzle makePartialPuzzle(int a, int b, int c, int valA, int valB, int valC){
		int x,y =0;
		int[][] grid = new int[4][4];
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
	
	public static int[] hasher(SearchNode sn) {
		int ret[] = new int[2];
		for(int i = 1; i < 16; i+=3) {
			int index1 = findLocation(sn, i)*256 + findLocation(sn, i+1)*16 + findLocation(sn, i+2);
			int index2 = (i/3)+1;
			//database[index1][index2] = calculateHeuristic(sn,i,i+1,i+2);
			System.out.println(index1 + ","+index2);
			System.out.println(calculateHeuristic(sn,i,i+1,i+2));
			ret[0] = index1;
			ret[1] = index2;
		}
		return ret;
	}
	
	public static int calculateHeuristic(SearchNode sn,int num1,int num2,int num3) {
		int totalHeuristic =0;
		Puzzle p = sn.puzzle;
		
		for(int r = 0; r < 4; r++)
		{
			for(int c = 0; c < 4; c++)
			{
				if(p.grid[r][c] == num1 || p.grid[r][c] == num2 ||p.grid[r][c] == num3)
				{
					totalHeuristic += (Math.abs((p.grid[r][c]-1)/4 - r)) + (Math.abs((p.grid[r][c]-1)%4 - c));
				}
			}
		}
		return totalHeuristic;
	}
	
	public static int findLocation(SearchNode sn, int tile) {
		for(int i =0;i<4;i++) {
			for(int j = 0; j <4;j++) {
				if(sn.puzzle.grid[i][j] == tile) {
					return (i*4)+j;
				}
			}
		}
		return 0;
	}
	
	public static void saveDatabase(int[][] m) {
		 try {
	         FileOutputStream fileOut =
	         new FileOutputStream("database.txt");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(m);
	         out.close();
	         fileOut.close();
	      } catch (IOException i) {
	         i.printStackTrace();
	      }
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
