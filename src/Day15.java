
public class Day15 {

	public static void main(String[] args) {
		//partOne();
		partTwo();
	}

	private static void partOne() {
		long a = 591;
		long b = 393;
		int matches = 0;
		for (int i = 0; i < 40000000; i++) {
			a = (a * 16807) % 2147483647;
			b = (b * 48271) % 2147483647;
			if (match(a, b))
				matches += 1;
		}
		System.out.println("Part One:   " + matches);
	}
	
	private static void partTwo() {
		long a = 591;
		long b = 393;
		int matches = 0;
		for (int i = 0; i < 5000000; i++) {
			do {
				a = (a * 16807) % 2147483647;
			} while (a % 4 != 0);
			do {
				b = (b * 48271) % 2147483647;
			} while (b % 8 != 0);
			if (match(a, b))
				matches += 1;
		}
		System.out.println("Part Two:   " + matches);
	}
	
	private static boolean match(long a, long b) {
		String binA = Long.toBinaryString(a);
		while (binA.length() < 16)
			binA = '0' + binA;
		
		String binB = Long.toBinaryString(b);
		while (binB.length() < 16)
			binB = '0' + binB;
		
		binA = binA.substring(binA.length() - 16, binA.length());
		binB = binB.substring(binB.length() - 16, binB.length());
		return binA.equals(binB);
	}
}
