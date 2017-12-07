import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Day7 {

	public static void main(String[] args) {
		Scanner sc;
		Map<String, List<String>> towerToChildren = new HashMap<String, List<String>>();
		Set<String> towers = new HashSet<String>();
		Map<String, Tower> nameToTower = new HashMap<String, Tower>();
		try {
			sc = new Scanner(new BufferedReader(new FileReader("inputs/input7.txt")));
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				parseLine(line, towerToChildren, towers, nameToTower);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		partOne(towerToChildren, towers);
		partTwo(nameToTower, getBase(towerToChildren, towers));
	}

	private static void parseLine(String line, Map<String, List<String>> towerToChildren, Set<String> towers, Map<String, Tower> nameToTower) {
		String tower = line.substring(0, line.indexOf(' '));
		List<String> children = new ArrayList<String>();
		int weight = 0;
		if (line.contains("->")) {
			String childrenStr = line.substring(line.indexOf(">") + 2);
			String[] childrenArr = childrenStr.split(", ");
			for (String c: childrenArr) {
				children.add(c);
			}			
		}
		int weightStart = line.indexOf('(') + 1;
		int weightEnd = line.indexOf(')');
		String weightStr = line.substring(weightStart, weightEnd);
		weight = Integer.parseInt(weightStr);
		towerToChildren.put(tower, children);
		towers.add(tower);
		nameToTower.put(tower, new Tower(tower, weight, children));
	}
	
	private static void partOne(Map<String, List<String>> map, Set<String> towers) {
		System.out.println("Part One:   " + getBase(map, towers));
	}
	
	private static String getBase(Map<String, List<String>> map, Set<String> towers) {
		Set<String> towerSet = new HashSet<String>();
		towerSet.addAll(towers);
		for (String t : towers) {
			List<String> children = map.get(t);
			for (String c : children)
				towerSet.remove(c);
		}
		return towerSet.iterator().next();
	}
	
	private static void partTwo(Map<String, Tower> nameToTower, String base) {
		System.out.println("Part Two:   ");
		isBalanced(nameToTower, base);
	}
	
	private static boolean isBalanced(Map<String, Tower> nameToTower, String base) {
		Tower t = nameToTower.get(base);
		Set<Integer> weights = new HashSet<Integer>();
		int i;
		for (String c: t.children) 
			weights.add(getWeight(nameToTower, c));
			
		if (weights.size() == 1)
			return true;
		
		System.out.println(base);
		for (String c2: t.children) {
			System.out.println("    " + c2 + " " + getWeight(nameToTower, c2));
			
		}
		
		for (String c3: t.children) {
			isBalanced(nameToTower, c3);
		}
		
		return false;
	}
	

private static int getWeight(Map<String, Tower> nameToTower, String base) {
		Tower t = nameToTower.get(base);
		int weight = t.weight;
		for (String c: t.children) {
			weight += getWeight(nameToTower, c);
		}
		return weight;
	}
}

class Tower {
	String name;
	int weight;
	List<String> children;
	
	public Tower(String n, int w, List<String> c) {
		name = n;
		weight = w;
		children = c;
	}
}
