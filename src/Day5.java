import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Day5 {

	public static void main(String[] args) {
		Scanner sc;
		List<String> lines = new ArrayList<String>();
		try {
			sc = new Scanner(new BufferedReader(new FileReader("inputs/input5.txt")));
			while (sc.hasNextLine()) {
				lines.add(sc.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		List<Integer> instructions = new ArrayList<Integer>();
		for (String line : lines)
			instructions.add(Integer.parseInt(line));
		partOne(instructions);
		partTwo(instructions);
	}
	
	private static void partOne(List<Integer> l) {
		int i = 0;
		int numSteps = 0;
		while (i < l.size()) {
			int valAtI = l.get(i);
			int nextI = i + valAtI;
			l.set(i, valAtI + 1);
			i = nextI;
			numSteps += 1;
		}
		
		System.out.println("Part One:   " + numSteps);
	}
	
	private static void partTwo(List<Integer> l) {
		int i = 0;
		int numSteps = 0;
		while (i < l.size()) {
			int valAtI = l.get(i);
			int nextI = i + valAtI;
			if (valAtI >= 3)
				l.set(i, valAtI - 1);
			else
				l.set(i, valAtI + 1);
			i = nextI;
			numSteps += 1;
		}
		System.out.println("Part Two:   " + numSteps);
	}
	
}
