import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class Day12 {

	public static void main(String[] args) {
		Scanner sc;
		List<String> lines = new ArrayList<String>();
		try {
			sc = new Scanner(new BufferedReader(new FileReader("inputs/input12.txt")));
			while (sc.hasNextLine())
				lines.add(sc.nextLine());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		partOne(lines);
		partTwo(lines);
	}

	private static void partOne(List<String> lines) {
		Map<Integer, List<Integer>> adjMap = createAdjMap(lines);
		boolean[] visited = new boolean[adjMap.keySet().size()];
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(0);
		visited[0] = true;
		int numProgs = 1;
		while (!q.isEmpty()) {
			int i = q.remove();
			List<Integer> l = adjMap.get(i);
			for (int j: l) {
				if (!visited[j]) {
					q.add(j);
					numProgs += 1;
				}
				visited[j] = true;
			}
		}
		System.out.println("Part One:   " + numProgs);
	}
	
	private static void partTwo(List<String> lines) {
		Map<Integer, List<Integer>> adjMap = createAdjMap(lines);
		boolean[] visited = new boolean[adjMap.keySet().size()];
		int numProgs = 0;
		int numGroups = 0;
		while (numProgs < adjMap.keySet().size()) {
			Queue<Integer> q = new LinkedList<Integer>();
			int k = 0;
			while (visited[k])
				k += 1;
			q.add(k);
			visited[k] = true;
			numProgs += 1;
			numGroups += 1;
			while (!q.isEmpty()) {
				int i = q.remove();
				List<Integer> l = adjMap.get(i);
				for (int j: l) {
					if (!visited[j]) {
						q.add(j);
						numProgs += 1;
					}
					visited[j] = true;
				}
			}
		}
		System.out.println("Part Two:   " + numGroups);
		
	}
	
	private static Map<Integer, List<Integer>> createAdjMap(List<String> lines) {
		Map<Integer, List<Integer>> adjMap = new HashMap<Integer, List<Integer>>();
		for (String l: lines) {
			String[] arr = l.split(" <-> ");
			int i = Integer.parseInt(arr[0]);
			String[] adjValStrs = arr[1].split(", ");
			List<Integer> adjVals = new ArrayList<Integer>();
			for (String s: adjValStrs)
				adjVals.add(Integer.parseInt(s));
			adjMap.put(i, adjVals);
		}
		return adjMap;
		
	}
}
