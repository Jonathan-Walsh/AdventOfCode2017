import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Day18 {

	public static void main(String[] args) {
        List<Instruction> instructions = getInstructions();
		//partOne(instructions);
		partTwo(instructions);
	}

    private static List<Instruction> getInstructions() {
        List<Instruction> iList = new ArrayList<Instruction>();
        try {
			Scanner sc = new Scanner(new BufferedReader(new FileReader("../inputs/input18.txt")));
            while (sc.hasNextLine()) {
                String i = sc.nextLine();
                iList.add(new Instruction(i));

            }
                
		} catch (FileNotFoundException e) {
			e.printStackTrace();
        }
        return iList;
    }

	private static void partOne(List<Instruction> instructions) {
        boolean freqRecovered = false;
        Map<Character, Long> registers = new HashMap<Character, Long>();
        for (int i = 0; i < 26; i++)
            registers.put((char) ('a' + i), (long) 0);
        int pc = 0;
        long mostRecentFreq = -1;
        while (!freqRecovered) {
            Instruction i = instructions.get(pc);
            if (i.op.equals("set")) {
                registers.put(i.val1, i.getVal2(registers));
            }
            else if (i.op.equals("snd")) {
                mostRecentFreq = registers.get(i.val1);
            }
            else if (i.op.equals("add")) {
                long x = registers.get(i.val1);
                long y = i.getVal2(registers);
                registers.put(i.val1, x + y);
            }
            else if (i.op.equals("mul")) {
                long x = registers.get(i.val1);
                long y = i.getVal2(registers);
                registers.put(i.val1, x * y);
            }
            else if (i.op.equals("mod")) {
                long x = registers.get(i.val1);
                long y = i.getVal2(registers);
                registers.put(i.val1, x % y);
            }
            else if (i.op.equals("rcv")) {
                long x = registers.get(i.val1);
                if (x != 0)
                    freqRecovered = true;
            }
            else if (i.op.equals("jgz")) {
                long x = registers.get(i.val1);
                if (x > 0)
                    pc += i.getVal2(registers) - 1;
            }
            pc += 1;
        }
		System.out.println("Part One:   " + mostRecentFreq);
	}
	
	private static void partTwo(List<Instruction> instructions) {
        Program p0 = new Program(instructions, 0);
        Program p1 = new Program(instructions, 1);
        p0.setOther(p1);
        p1.setOther(p0);
        while (!(p0.isWaiting && p1.isWaiting) && !(p0.isTerminated && p1.isTerminated)) {
            Program p = p0;
            if (p0.isWaiting || p0.isTerminated)
                p = p1;
            p.step();
        }
		System.out.println("Part Two:   " + p1.numSent);
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

class Program {
    List<Instruction> instructions;
    int pc;
    Map<Character, Long> registers;
    Program other;
    boolean isTerminated;
    boolean isWaiting;
    Queue<Long> msgQueue;
    int numSent;

    public Program(List<Instruction> i, long p) {
        instructions = i;
        pc = 0;
        registers = initRegs();
        registers.put('p', p);
        isTerminated = false;
        isWaiting = false;
        msgQueue = new LinkedList<Long>();
        numSent = 0;
    }

    public void setOther(Program o) {
        other = o;
    }

    public void enqueueMsg(Long l) {
        msgQueue.add(l);
        isWaiting = false;
    }

    private Map<Character, Long> initRegs() {
        Map<Character, Long> registers = new HashMap<Character, Long>();
        for (int i = 0; i < 26; i++)
            registers.put((char) ('a' + i), (long) 0);
        return registers;
    }

    public void step() {
        Instruction i = instructions.get(pc);
        //System.out.println(i.op + " " + i.val1 + " " + i.val2);
            if (i.op.equals("set")) {
                registers.put(i.val1, i.getVal2(registers));
            }
            else if (i.op.equals("snd")) {
                other.enqueueMsg(registers.get(i.val1));
                numSent += 1;
            }
            else if (i.op.equals("add")) {
                long x = registers.get(i.val1);
                long y = i.getVal2(registers);
                registers.put(i.val1, x + y);
            }
            else if (i.op.equals("mul")) {
                long x = registers.get(i.val1);
                long y = i.getVal2(registers);
                registers.put(i.val1, x * y);
            }
            else if (i.op.equals("mod")) {
                long x = registers.get(i.val1);
                long y = i.getVal2(registers);
                registers.put(i.val1, x % y);
            }
            else if (i.op.equals("rcv")) {
                if (msgQueue.isEmpty()) {
                    isWaiting = true;
                    return;
                }
                long x = msgQueue.poll();
                registers.put(i.val1, x);
            }
            else if (i.op.equals("jgz")) {
                long x = i.getVal1(registers);
                if (x > 0)
                    pc += i.getVal2(registers) - 1;
            }
            pc += 1;
            if (pc < 0 || pc >= instructions.size())
                isTerminated = true;
    }
}