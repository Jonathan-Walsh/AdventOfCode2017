import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day6 {

	public static void main(String[] args) {
		//int[] memBanks = {0, 2, 7, 0};
		int[] memBanks = {4, 10, 4, 1, 8, 4, 9, 14, 5, 1, 14, 15, 0, 15, 3, 5};
		solve(memBanks);
	}
	
	private static void solve(int[] memBanks) {
		int numSteps = 0;
		Map<String, Integer> configs = new HashMap<String, Integer>();
		boolean configSeenTwice = false;
		int oldNumSteps = 0;
		while (!configSeenTwice) {
			numSteps += 1;
			int i = chooseI(memBanks);
			redistribute(memBanks, i);
			String config = getConfig(memBanks);
			if (configs.containsKey(config)) {
				configSeenTwice = true;
				oldNumSteps = configs.get(config);
			}
			else
				configs.put(config, numSteps);				
		}
		
		System.out.println("Part One:   " + numSteps);
		System.out.println("Part Two:   " + (numSteps - oldNumSteps));
	}

	private static int chooseI(int[] memBanks) {
		int i = 0;
		for (int j = 0; j < memBanks.length; j++) {
			if (memBanks[i] < memBanks[j])
				i = j;
		}
		return i;
	}
	
	private static void redistribute(int[] memBanks, int i) {
		int blocks = memBanks[i];
		memBanks[i] = 0;
		int j = i;
		while (blocks > 0) {
			j = (j + 1) % memBanks.length;
			memBanks[j] += 1;
			blocks -= 1;
		}
	}
	
	private static String getConfig(int[] memBanks) {
		StringBuilder configSB = new StringBuilder();
		for (int i = 0; i < memBanks.length; i++) {
			configSB.append(memBanks[i]);
			if (i < memBanks.length - 1)
				configSB.append(',');
		}
		return configSB.toString();
	}
	
}
