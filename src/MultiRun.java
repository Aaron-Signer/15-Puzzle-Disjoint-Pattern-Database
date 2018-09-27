import java.util.ArrayList;

/*
* Aaron Signer, Dan Jackson, Sam Hockenberry
* AI Lab 1
*/

public class MultiRun {

	public static void main(String[] args) {
		TotalDistanceSolver TDS = new TotalDistanceSolver();
		DisjointPatternSolver DPS = new DisjointPatternSolver();
		MultiRun mR = new MultiRun();
//		System.out.println(mR.calculateAverageNumNodes(TDS, 1, 10));
//		ArrayList<Double> d =  mR.calculateAverageNumNodes(DPS, 1, 30);
		ArrayList<Double> a =  mR.calculateAverageBranchingFactor(DPS, 10, 30);

//		for(int i = 0; i < d.size(); i++)
//			System.out.println(i + " - " + d.get(i));
//		System.out.println("--------------------");
//		
//		for(int i = 0; i < a.size(); i++)
//			System.out.println(i + " - " + a.get(i));
//		
//		DisjointPatternSolver djP = new DisjointPatternSolver();
//		d =  mR.calculateAverageNumNodes(djP, 100, 10);
//		a =  mR.calculateAverageBranchingFactor(djP, 100, 10);
//
//		for(int i = 0; i < d.size(); i++)
//			System.out.println(i + " - " + d.get(i));
//		
//		System.out.println("--------------------");
//		
//		for(int i = 0; i < a.size(); i++)
//			System.out.println(i + " - " + a.get(i));

	}
	
	public  ArrayList<Double> calculateAverageNumNodes(Solver s, double numRuns, int n)
	{
		ArrayList<ArrayList<Double>> vals = new ArrayList<ArrayList<Double>>();
		int [] counts = new int [n+1];
		
		for(double i = 0; i < n+1; i++)
		{
			vals.add(new ArrayList<Double>(0));
		}
		
		RandomPuzzle rP = new RandomPuzzle();
		double totalNumNodes = 0;
		
		for(int i = 0; i < numRuns; i++)
		{
			System.out.println(i);
			Puzzle p = new Puzzle(rP.generatePuzzle(n));
			SearchNode sN = s.solve(p);
			vals.get(sN.pathCost).add((double)s.numNodes);
			counts[sN.pathCost] += 1;
		}
		
		ArrayList<Double> averages = new ArrayList<Double>();
		
		for(ArrayList<Double> cur: vals)
		{
			int count = 0;
			double total = 0;
			for(double d: cur)
			{
				total += d;
				count++;
			}
			averages.add(total/cur.size());
		}
		
		for(int i = 0; i < averages.size(); i++)
			System.out.println(i + " - " + counts[i] + " - " + averages.get(i) + " - " + vals.get(i));
		
		return averages;
	}
	
	public  ArrayList<Double> calculateAverageBranchingFactor(Solver s, double numRuns, int n)
	{
		ArrayList<ArrayList<Double>> vals = new ArrayList<ArrayList<Double>>();
		int [] counts = new int [n+1];
		
		for(double i = 0; i < n+1; i++)
		{
			vals.add(new ArrayList<Double>(0));
		}
		
		RandomPuzzle rP = new RandomPuzzle();
		double totalNumNodes = 0;
		
		for(int i = 0; i < numRuns; i++)
		{
			System.out.println(i);
			Puzzle p = new Puzzle(rP.generatePuzzle(n));
			SearchNode sN = s.solve(p);
			vals.get(sN.pathCost).add(Math.pow((double)s.numNodes, (1.0/sN.pathCost)));
			counts[sN.pathCost] += 1;
		}
		
		ArrayList<Double> averages = new ArrayList<Double>();
		
		for(ArrayList<Double> cur: vals)
		{
			int count = 0;
			double total = 0;
			for(double d: cur)
			{
				total += d;
				count++;
			}
			averages.add(total/cur.size());
		}
		for(int i = 0; i < averages.size(); i++)
			System.out.println(i + " - " + counts[i] + " - " + averages.get(i) + " - " + vals.get(i));
		return averages;
	}

}
