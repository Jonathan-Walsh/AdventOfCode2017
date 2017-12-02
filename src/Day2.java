import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day2 {

	
	
	public static void main(String[] args) {
		partOne();
		partTwo();
	}

	private static void partOne() {
		Scanner sc;
		String line = "";
		int sum = 0;
		try {
			sc = new Scanner(new BufferedReader(new FileReader("inputs/input2.txt")));
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				String[] nums = line.split("\\s+");
				int min = Integer.parseInt(nums[0]);
				int max = Integer.parseInt(nums[0]);
				for (String num : nums) {
					int i = Integer.parseInt(num);
					if (i < min)
						min = i;
					if (i > max)
						max = i;
				}
				sum += max - min;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println("Part One: " + sum);
	}
	
	private static void partTwo() {
		Scanner sc;
		String line = "";
		int sum = 0;
		try {
			sc = new Scanner(new BufferedReader(new FileReader("inputs/input2.txt")));
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				String[] nums = line.split("\\s+");
				List<Integer> ints = new ArrayList<Integer>();
				for (String num : nums) {
					ints.add(Integer.parseInt(num));
				}
				int div = 0;
				boolean divFound = false;
				for (int i = 0; i < ints.size(); i++) {
					for (int j = 0; j < ints.size(); j++) {
						if (i != j) {
							int x = ints.get(i);
							int y = ints.get(j);
							if (x >= y && x % y == 0) {
								div = x / y;
								divFound = true;
							}		
						}
					}
					if (divFound)
						break;
				}
				sum += div;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println("Part Two: " + sum);
	}
}
