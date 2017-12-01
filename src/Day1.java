import java.util.Scanner;
import java.io.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;



public class Day1 {

	public static void main(String[] args) {
		File input = new File("inputs/input1.txt");
		Scanner sc;
		String line = "";

		try {
			sc = new Scanner(new BufferedReader(new FileReader("inputs/input1.txt")));
			line = sc.nextLine();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		char[] cA = line.toCharArray();
		System.out.println("Part One: " + getSum(cA, 1));
		System.out.println("Part Two " + getSum(cA, cA.length / 2));
	}

	private static int getSum(char[] nums, int yDist) {
		int sum = 0;
		for (int i = 0; i < nums.length; i++) {
			int x = Character.getNumericValue(nums[i]);
			int y = Character.getNumericValue(nums[(i + yDist) % nums.length]);
			if (x == y)
				sum += x;
		}
		return sum;
	}
	
}
