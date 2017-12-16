import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Day16 {

	public static void main(String[] args) {
		Scanner sc;
		String[] list = new String[0];
		try {
			sc = new Scanner(new BufferedReader(new FileReader("inputs/input16.txt")));
			String s = sc.nextLine();
			list = s.split(",");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		partOne(list);
		partTwo(list);
	}

	private static void partOne(String[] list) {
		char[] arr = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p' };
		arr = dance(arr, list);
		System.out.println("Part One:   ");
		for (char c: arr)
			System.out.print(c);
		System.out.println();
	}
	
	private static char[] dance(char[] arr, String[] list) {
		for (String s: list) {
			if (s.startsWith("s")) {
				int i = Integer.parseInt(s.substring(1));
				char[] temp = new char[arr.length];
				for (int j = 0; j < arr.length; j++)
					temp[(i + j) % arr.length] = arr[j];
				arr = temp;
			}
			else if (s.startsWith("x")) {
				int i = Integer.parseInt(s.substring(1, s.indexOf("/")));
				int j = Integer.parseInt(s.substring(s.indexOf("/") + 1));
				char c = arr[i];
				arr[i] = arr[j];
				arr[j] = c;
			}
			else if (s.startsWith("p")) {
				char x = s.charAt(1);
				char y = s.charAt(3);
				int xi = 0;
				int yi = 0;
				for (int i = 0; i < arr.length; i++) {
					if (arr[i] == x)
						xi = i;
					if (arr[i] == y)
						yi = i;
				}
				char c = arr[xi];
				arr[xi] = arr[yi];
				arr[yi] = c;
			}
		}
		return arr;
	}
	
	private static void partTwo(String[] list) {
		List<String> s = new ArrayList<String>();
		char[] arr = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p' };
		boolean done = false;
		int i = 0;
		while (!done) {
			arr = dance(arr, list);
			if (s.contains(String.valueOf(arr)))
				done = true;
			else 
				s.add(String.valueOf(arr));
		}
		int index = 1000000000 % s.size();
		System.out.println("Part Two:   " + s.get(index - 1));
	}
}
