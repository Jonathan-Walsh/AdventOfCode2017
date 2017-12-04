import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day4 {

	public static void main(String[] args) {
		solve(true);
		solve(false);
	}

	private static void solve(boolean isPartOne) {
		Scanner sc;
		String line = "";
		int validPhrases = 0;
		try {
			sc = new Scanner(new BufferedReader(new FileReader("inputs/input4.txt")));
			while (sc.hasNextLine()) {
				Set<String> seenPhrases = new HashSet<String>();
				boolean valid = true;
				line = sc.nextLine();
				String[] phrases = line.split("\\s+");
				for (String p : phrases) {
					if (!isPartOne) {
						char[] cA = p.toCharArray();
						Arrays.sort(cA);
						p = new String(cA);
					}
					if (seenPhrases.contains(p)) {
						valid = false;
						break;
					}
					else {
						seenPhrases.add(p);
					}	
				}
				if (valid)
					validPhrases += 1;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (isPartOne)
			System.out.println("Part One:   " + validPhrases);
		else
			System.out.println("Part One:   " + validPhrases);
	}
	
}
