import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day9 {

	public static void main(String[] args) {
		Scanner sc;
		char[] input = new char[0];
		try {
			sc = new Scanner(new BufferedReader(new FileReader("inputs/input9.txt")));
			input = sc.nextLine().toCharArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		solve(input);
		
	}

	private static void solve(char[] input) {
		ReturnObj o = score(input, 0, 0);
		System.out.println("Part One:   " + o.totalScore);
		System.out.println("Part Two:   " + o.garbageCount);
	}

	private static ReturnObj score(char[] input, int i, int currentScore) {
		int score = currentScore;
		boolean groupFinished = false;
		boolean isGarbage = false;
		int garbageCount = 0;
		while (i < input.length && !groupFinished) {
			char c = input[i];
			if (isGarbage && c != '!' && c != '>')
				garbageCount += 1;
			
			if (isGarbage && c == '!')
				i++;
			else if (c == '<')
				isGarbage = true;
			else if (c == '>')
				isGarbage = false;
			else if (!isGarbage && c == '{') {
				ReturnObj o = score(input, i+1, currentScore + 1);
				i = o.indexOfEnd;
				score += o.totalScore;
				garbageCount += o.garbageCount;
			}
			else if (!isGarbage && c == '}')
				groupFinished = true;
				
			i++;
		}
		return new ReturnObj(score, i - 1, garbageCount);
	}
	
	
}

class ReturnObj {
	int totalScore;
	int indexOfEnd;
	int garbageCount;
	
	public ReturnObj(int ts, int ioe, int gc) {
		totalScore = ts;
		indexOfEnd = ioe;
		garbageCount = gc;
	}
}