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

public class Day17 {

	private static int input = 304;

	public static void main(String[] args) {
		partOne();
		partTwo();
	}

	private static void partOne() {
		List<Integer> l = new ArrayList<Integer>();
		l.add(0);
		int index = 0;
		for (int i = 1; i < 2018; i++) {
			index = (index + input) % l.size();
			l.add(index + 1, i);
			index += 1;
		}
		System.out.println("Part One:   " + l.get((index + 1) % l.size()));
	}
	
	private static void partTwo() {
		int index = 0;
		int valAfterZero = 0;
		int size = 1;
		for (int i = 1; i < 50000000; i++) {
			index = (index + input) % size;
			if (index == 0)
				valAfterZero = i;
			index += 1;
			size += 1;
		}

		System.out.println("Part Two:   " + valAfterZero);
	}
}
