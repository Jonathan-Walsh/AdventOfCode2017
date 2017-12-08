import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Day8 {

	public static void main(String[] args) {
		solve();
	}

	private static List<Instruction> parseInput() {
		Scanner sc;
		List<Instruction> instructions = new ArrayList<Instruction>();
		try {
			sc = new Scanner(new BufferedReader(new FileReader("inputs/input8.txt")));
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] arr = line.split(" ");
				int addVal = Integer.parseInt(arr[2]);
				if (arr[1].equals("dec"))
					addVal *= -1;
				Instruction i = new Instruction(arr[0], addVal,
						arr[4], arr[5], Integer.parseInt(arr[6]));
				instructions.add(i);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return instructions;
	}
	
	private static void solve() {
		List<Instruction> iList = parseInput();
		Map<String, Integer> varSet = new HashMap<String, Integer>();
		for (Instruction i: iList) {
			varSet.put(i.addVar, 0);
			varSet.put(i.condVar, 0);
		}
		
		int max2 = 0;
		for (Instruction i: iList) {
			int newVal = i.run(varSet);
			if (newVal > max2)
				max2 = newVal;
		}
		
		int max = 0;
		for (String var : varSet.keySet()) {
			if (varSet.get(var) > max) 
				max = varSet.get(var);
		}
		
		System.out.println("Part One:   " + max);
		System.out.println("Part Two:   " + max2);
	}
	
	
}

class Instruction {
	String addVar;
	int addVal;
	String condVar;
	String condOp;
	int condVal;
	
	public Instruction(String aVar, int aVal, String cVar, String cOp, int cVal) {
		addVar = aVar;
		addVal = aVal;
		condVar = cVar;
		condOp = cOp;
		condVal = cVal;
	}
	
	public int run(Map<String, Integer> varSet) {
		int addVarVal = varSet.get(addVar);
		int condVarVal = varSet.get(condVar);
		boolean condTrue = false;
		if (condOp.equals("=="))
			condTrue = (condVarVal == condVal);
		else if (condOp.equals("!="))
			condTrue = (condVarVal != condVal);
		else if (condOp.equals("<="))
			condTrue = (condVarVal <= condVal);
		else if (condOp.equals(">="))
			condTrue = (condVarVal >= condVal);
		else if (condOp.equals("<"))
			condTrue = (condVarVal < condVal);
		else if (condOp.equals(">"))
			condTrue = (condVarVal > condVal);
		
		if (condTrue)
			addVarVal += addVal;
		
		varSet.put(addVar, addVarVal);
		return addVarVal;
	}
}