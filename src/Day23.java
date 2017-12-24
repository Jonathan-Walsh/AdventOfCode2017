import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Day23 {

	public static void main(String[] args) {
        List<Instruction> iList = getInstructions();
        partOne(iList);
        partTwo(iList);
	}

    private static List<Instruction> getInstructions() {
        List<Instruction> iList = new ArrayList<Instruction>();
        try {
			Scanner sc = new Scanner(new BufferedReader(new FileReader("../inputs/input23.txt")));
            while (sc.hasNextLine()) {
                String i = sc.nextLine();
                iList.add(new Instruction(i));
            }
                
		} catch (FileNotFoundException e) {
			e.printStackTrace();
        }
        return iList;
    }

    public static void partOne(List<Instruction> iList) {
        Map<Character, Long> registers = new HashMap<Character, Long>();
        char[] arr = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        for (char c: arr)
            registers.put(c, (long) 0);
        int pc = 0;
        int numMults = 0;
        int z = 0;
        while (pc >= 0 && pc < iList.size()) {
            z += 1;
            Instruction i = iList.get(pc);
            String op = i.op;
            if (op.equals("set")) {
                registers.put(i.val1, i.getVal2(registers));
            }
            else if (i.op.equals("sub")) {
                long x = registers.get(i.val1);
                long y = i.getVal2(registers);
                registers.put(i.val1, x - y);
            }
            else if (i.op.equals("mul")) {
                long x = registers.get(i.val1);
                long y = i.getVal2(registers);
                registers.put(i.val1, x * y);
                numMults += 1;
            }
            else if (i.op.equals("jnz")) {
                long x = i.getVal1(registers);
                if (x != 0)
                    pc += i.getVal2(registers) - 1;
            }
            pc += 1;
        }
        System.out.println("Part One:   " + numMults);
    }

    public static void partTwo(List<Instruction> iList) {
        //See misc/Day23.pdf for the work I did by hand to optimize the instruction list
        int h = 0;
        for (int i = 107900; i <= 124900; i+= 17) {
            if (!isPrime(i))
                h += 1;
        }
        System.out.println("Part Two:   " + h);
    }

    public static boolean isPrime(int n) {
        int i;
        for (i = 2; i <= n / 2; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}

class Instruction {
    String op;
    char val1;
    String val2;

    public Instruction(String i) {
        String[] iTokens = i.split(" ");
        op = iTokens[0];
        val1 = iTokens[1].charAt(0);
        if (iTokens.length > 2)
            val2 = iTokens[2];
    }

    public long getVal1(Map<Character, Long> regs) {
        String s = String.valueOf(val1);
        if (isNumeric(s))
            return Long.parseLong(s);
        else
            return regs.get(val1);
    }

    public long getVal2(Map<Character, Long> regs) {
        if (isNumeric(val2))
            return Long.parseLong(val2);
        else
            return regs.get(val2.charAt(0));
    }

    private boolean isNumeric(String str)
    {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.   
    }
}