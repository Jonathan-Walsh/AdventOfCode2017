import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Day11 {

	public static void main(String[] args) {
		Scanner sc;
		String[] dirs = new String[0];
		try {
			sc = new Scanner(new BufferedReader(new FileReader("inputs/input11.txt")));
			dirs = sc.nextLine().split(",");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		solve(dirs);
	}

	private static void solve(String[] dirs) {
		double x = 0;
		double y = 0;
		int maxDist = 0;
		for (String dir: dirs) {
			double step = 1;
			if (dir.length() == 2)
				step = 0.5;
			if (dir.contains("n"))
				y += step;
			if (dir.contains("s"))
				y -= step;
			if (dir.contains("w"))
				x += step;
			if (dir.contains("e"))
				x -= step;
			
			int dist = (int) (Math.abs(x) + Math.abs(y));
			if (dist > maxDist)
				maxDist = dist;
		}
		System.out.println("Part One:   " + (int) (Math.abs(x) + Math.abs(y)));
		System.out.println("Part Two:   " + maxDist);
	}
}
