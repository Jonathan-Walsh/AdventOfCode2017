import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day13 {

	public static void main(String[] args) {
		Scanner sc;
		Map<Integer, Integer> scanners = new HashMap<Integer, Integer>();
		try {
			sc = new Scanner(new BufferedReader(new FileReader("inputs/input13.txt")));
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] tokens = line.split(": ");
				int x = Integer.parseInt(tokens[0]);
				int y = Integer.parseInt(tokens[1]);
				scanners.put(x, y);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		partOne(scanners);
		partTwo(scanners);
	}
	
	private static void partOne(Map<Integer, Integer> s) {
		int max = 0;
		for (int d: s.keySet()) {
			if (d > max)
				max = d;
		}
		int severity = 0;
		for (int i = 0; i <= max; i++) {
			if (s.keySet().contains(i)) {
				int r = s.get(i);
				if (r == 1)
					severity += i * r;
				else if (r > 1) {
					int loc = (i % (2 * r - 2));
					if (loc == 0) {
						severity += i * r;
						//System.out.println(i + " " + r);
					}
				}
			}
		}
		System.out.println("Part One:   " + severity);
	}

	private static void partTwo(Map<Integer, Integer> s) {
		int max = 0;
		for (int d: s.keySet()) {
			if (d > max)
				max = d;
		}
		int delay = 0;
		boolean done = false;
		while (!done) {
			for (int i = 0; i <= max; i++) {
				if (s.keySet().contains(i)) {
					int r = s.get(i);
					int loc = ((delay + i) % (2 * r - 2));
					if (loc == 0) {
						break;
					}
					if (i == max)
						done = true;
				}
			}
			delay += 1;
		}
		
		System.out.println("Part Two:   " + (delay - 1));
	}
	
}
