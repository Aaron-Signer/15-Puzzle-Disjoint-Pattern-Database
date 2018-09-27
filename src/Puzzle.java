import java.util.*;
import java.text.*;

public class Puzzle {

	public static final int BLANK = 0;
	
	public static final int UP = 1;
	public static final int RIGHT = 2;
	public static final int LEFT = 3;
	public static final int DOWN = 4;
	
	public int[][] grid = new int[4][4];		// stores the tiles
	public int blankRow, blankCol;				// (row, col) of the blank tile
	
	public Puzzle(Puzzle p)
	/*
	 *  copy constructor
	 */
	{
		for(int i=0; i<grid.length; i++)
			for(int j=0; j<grid[0].length; j++)
				grid[i][j] = p.grid[i][j];
		blankRow = p.blankRow;
		blankCol = p.blankCol;
	}
	
	public Puzzle(Scanner in)
	/*
	 * reads a puzzle in from standard input
	 */
	{
		for(int i=0; i<grid.length; i++)
			for(int j=0; j<grid[0].length; j++) {
				grid[i][j] = in.nextInt();
				if (grid[i][j] == BLANK) {
					blankRow = i;
					blankCol = j;
				}
			}
	}
	
	public Puzzle(int [][] g)
	/*
	 * builds a Puzzle object around a grid representing the 15-puzzle
	 */
	{
		for(int i=0; i<grid.length; i++)
			for(int j=0; j<grid[0].length; j++) {
				grid[i][j] = g[i][j];
				if (grid[i][j] == BLANK) {
					blankRow = i;
					blankCol = j;
				}
			}		
	}
	
	public boolean canMakeMove(int move)
	/*
	 * checks if a given move can be made
	 */
	{
		if (move == UP)
			return (blankRow > 0);
		else if (move == DOWN)
			return (blankRow < grid.length-1);
		else if (move == LEFT)
			return (blankCol > 0);
		else if (move == RIGHT)
			return (blankCol < grid[0].length-1);
		else {
			System.out.println("Illegal Move");
			System.exit(-1);
		}
		return false;
	}
	
	public void makeMove(int move)
	/*
	 * makes the given move on the 15-puzzle.  This method assume canMakeMove(move) was called prior
	 */
	{
		if (move == UP) {
			grid[blankRow][blankCol] = grid[blankRow-1][blankCol];
			grid[blankRow-1][blankCol] = BLANK;
			blankRow--;
		}
		else if (move == DOWN) {
			grid[blankRow][blankCol] = grid[blankRow+1][blankCol];
			grid[blankRow+1][blankCol] = BLANK;
			blankRow++;
		}
		else if (move == LEFT) {
			grid[blankRow][blankCol] = grid[blankRow][blankCol-1];
			grid[blankRow][blankCol-1] = BLANK;
			blankCol--;
		}
		else if (move == RIGHT) {
			grid[blankRow][blankCol] = grid[blankRow][blankCol+1];
			grid[blankRow][blankCol+1] = BLANK;
			blankCol++;
		}
	}
	
	public void unMakeMove(int move)
	/*
	 * unmakes the given move on the 15-puzzle.  This method assume makeMove(move) was called prior
	 */
	{
		if (move == UP) {
			makeMove(DOWN);
		}
		else if (move == DOWN) {
			makeMove(UP);
		}
		else if (move == LEFT) {
			makeMove(RIGHT);
		}
		else if (move == RIGHT) {
			makeMove(LEFT);
		}
	}
	
	public String toString()
	/*
	 * returns string representation of the game
	 */
	{
		DecimalFormat format = new DecimalFormat(" 00");
		String ans = "";
		for(int i=0; i<grid.length; i++) {
			for(int j=0; j<grid[0].length; j++)
				if (grid[i][j] == BLANK)
					ans += " 00";
				else
					ans += format.format(grid[i][j]);
			ans += "\n";
		}
		return ans;
	}
}
