/*
 * 
 */
package piDarts;

public class piDarts {
	private static int radius = 100; // radius of the circle used (default: 100)
	private static int maxDarts = 1000; // maximum darts thrown per estimate (default: 1000)
	
	public static double RandomRange(int min, int max) {
		/*
		 * Generate a random number between min and max
		 * Seems to estimate a little low most of the time, so there were last-second tweaks involved
		 */
		/* old: double rand = min + Math.floor(Math.random() * (1 + max - min)); */
		double rand = min + (Math.floor(Math.random() * (1 + (10000000 * (max - min)))) / 10000000);
		return rand;
	}
	
	public static int ThrowDarts() {
		/*
		 * throw darts in a square, and calculate how many hit inside the circle
		 */
		int in = 0;
		for (int i = 0; i < maxDarts; i++) {
			if (IsInCircle()) {
				in++;
			}
		}
		return in;
	}
	
	public static boolean IsInCircle() {
		/*
		 * determine whether a coordinate is inside the circle
		 */
		int min = -radius;
		int max = radius;
		double x = RandomRange(min, max);
		double y = RandomRange(min, max);
		double radSquare = Math.pow(radius, 2);
		double xSquare = Math.pow(x, 2);
		double ySquare = Math.pow(y, 2);
		return ((xSquare + ySquare) <= radSquare);
	}
	
	public static double MonteCarloCircle(boolean show) {
		/*
		 * calculate the value of pi based on how many darts landed inside the circle
		 */
		int darts = ThrowDarts();
		double circArea = 4 * (double)darts / maxDarts;
		if (show == true) {
			System.out.print("Estimate: ");
			System.out.println(circArea);
		}
		return circArea;
	}
	
	public static void MonteCarloLoop(int maxIter, boolean show) {
		/*
		 * run the Monte Carlo calculation maxIter times
		 */
		double circArea = (int)0;
		int iter;
		for (iter = 0; iter < maxIter; iter++) {
			circArea += MonteCarloCircle(show);
		}
		circArea = circArea / iter;
		System.out.print("Final Estimate: ");
		System.out.println(circArea);
	}
	
	public static void main(String[] args) {
		int maxIter = 10; // number of estimations to average (default: 10)
		boolean show = false; // whether to show each estimation or not
		if (args.length > 0) {
			try {
				radius = Integer.parseInt(args[0]);
			} catch(NumberFormatException e) {
				radius = 100;
			}
		}
		if (args.length > 1) {
			try {
				maxDarts = Integer.parseInt(args[1]);
			} catch(NumberFormatException e) {
				maxDarts = 1000;
			}
		}
		if (args.length > 2) {
			if (args[2] == "true") {
				show = true;
			}
			if (args[2] == "false") {
				show = false;
			}
		}
		System.out.print("PI estimation after ");
		System.out.print(maxIter);
		System.out.println(" iterations: ");
		MonteCarloLoop(maxIter, show);
	}
	
}
