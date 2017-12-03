import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day3 {

	public static void main(String[] args) {
		//partOne(361527);
		partTwo(361527);
	}

	
	private static void partOne(int loc) {
		int x = 0;
		int y = 0;
		int num = 1;
		int farthestX = 0;
		int farthestY = 0;
		int stepsInCurrentDir = 0;
		Map<Character, Character> nextDir = new HashMap<Character, Character>();
		nextDir.put('e', 'n');
		nextDir.put('n', 'w');
		nextDir.put('w', 's');
		nextDir.put('s', 'e');
		char dir = 'e';
		while (num != loc) {
			if (dir == 'e')
				x += 1;
			else if (dir == 'n')
				y += 1;
			else if (dir == 'w')
				x -= 1;
			else if (dir == 's')
				y -= 1;
			stepsInCurrentDir += 1;
			if ((dir == 'n' || dir == 's') && stepsInCurrentDir > farthestY) {
				farthestY = stepsInCurrentDir;
				stepsInCurrentDir = 0;
				dir = nextDir.get(dir);
			}
			else if ((dir == 'e' || dir == 'w') && stepsInCurrentDir > farthestX) {
				farthestX = stepsInCurrentDir;
				stepsInCurrentDir = 0;
				dir = nextDir.get(dir);
			}
			num += 1;
		}
		System.out.println("Part One: " + (Math.abs(x) + Math.abs(y)));
	}
	
	private static void partTwo(int loc) {
		int x = 0;
		int y = 0;
		int num = 1;
		int farthestX = 0;
		int farthestY = 0;
		int stepsInCurrentDir = 0;
		Map<Character, Character> nextDir = new HashMap<Character, Character>();
		nextDir.put('e', 'n');
		nextDir.put('n', 'w');
		nextDir.put('w', 's');
		nextDir.put('s', 'e');
		Map<Integer, Map<Integer, Integer>> values = new HashMap<Integer, Map<Integer, Integer>>();
		storeNum(values, x, y, num);
		char dir = 'e';
		while (num < loc) {
			if (dir == 'e')
				x += 1;
			else if (dir == 'n')
				y += 1;
			else if (dir == 'w')
				x -= 1;
			else if (dir == 's')
				y -= 1;
			stepsInCurrentDir += 1;
			if ((dir == 'n' || dir == 's') && stepsInCurrentDir > farthestY) {
				farthestY = stepsInCurrentDir;
				stepsInCurrentDir = 0;
				dir = nextDir.get(dir);
			}
			else if ((dir == 'e' || dir == 'w') && stepsInCurrentDir > farthestX) {
				farthestX = stepsInCurrentDir;
				stepsInCurrentDir = 0;
				dir = nextDir.get(dir);
			}
			num = getNum(values, x, y);
			storeNum(values, x, y, num);
		}
		System.out.println("Part Two: " + num);
	}
	
	private static int getNum(Map<Integer, Map<Integer, Integer>> values, int x, int y) {
		int num = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (values.containsKey(x + i)) {
					Map<Integer, Integer> vals2 = values.get(x + i);
					if (vals2.containsKey(y + j))
						num += vals2.get(y + j);
				}
			}
		}
		return num;
	}
	
	private static void storeNum(Map<Integer, Map<Integer, Integer>> vals, int x, int y, int num) {
		if (!vals.containsKey(x))
			vals.put(x, new HashMap<Integer, Integer>());
		Map<Integer, Integer> vals2 = vals.get(x);
		vals2.put(y, num);
	}
}
