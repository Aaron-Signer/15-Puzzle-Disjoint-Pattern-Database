import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class tester {

	public static void main(String[] args) {
		int[][] t = readDatabase();
		for(int i = 0; i < 4095; i++) {
			System.out.print(i+":");
			for(int j = 0; j < 5; j++) {
				System.out.print(t[i][j]+" ");
			}
			System.out.println();
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
