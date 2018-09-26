import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class BuildDatabase {
	static int[][] database = new int[4095][6];
	public static void main(String[] args) {
		
		database[0][0] = 1;
		database[0][1] = 2;
		database[1][0] = 3;
		saveDatabase(database);
		
		int[][] database2 =readDatabase();
		System.out.println(database2[0][0]);
		System.out.println(database2[0][1]);
		System.out.println(database2[1][0]);
	}
	
	
	public static int hasher(SearchNode sn) {
		for(int i = 1; i < 16; i+=3) {
			int index1 = findLocation(sn, i)*256 + findLocation(sn, i+1)*16 + findLocation(sn, i+2);
			int index2 = (i/3)+1;
			database[index1][index2] = calculateHeuristic(sn,i,i+1,i+2);
		}
		return 0;
	}
	
	public static int calculateHeuristic(SearchNode sn,int num1,int num2,int num3) {
		
		
		
		return 0;
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