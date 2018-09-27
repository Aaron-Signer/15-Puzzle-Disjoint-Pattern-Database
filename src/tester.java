import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.text.MaskFormatter;

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
	
	public static Puzzle makePartialPuzzle(int a, int b, int c, int valA, int valB, int valC){
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
