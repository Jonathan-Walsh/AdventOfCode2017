
public class Day14 {

	public static void main(String[] args) {
		String input = "hxtvlmkl";
		solve(input);
	}

	private static void solve(String in) {
		int[][] grid = new int[128][128];
		for (int i = 0; i < 128; i++) {
			String rowIn = in + "-" + i;
			String hex = getHex(rowIn.toCharArray());
			for (int j = 0; j < 32; j++) {
				String binary = hexToBinary(Character.toString(hex.charAt(j)));
				for (int k = 0; k < 4; k++)
					grid[i][j*4 + k] = Character.getNumericValue(binary.charAt(k)); 
			}	
		}
		
		int used = 0;
		for (int[] row: grid)
			for (int bit: row)
				if (bit == 1)
					used += 1;
		System.out.println("Part One:   " + used);
		partTwo(grid);
	}

	private static void partTwo(int[][] g) {
		int groups = 0;
		for (int i = 0; i < g.length; i++) {
			for (int j = 0; j < g[0].length; j++) {
				if (g[i][j] == 1) {
					groups += 1;
					removeGroup(g, i, j);
				}
			}
		}
		System.out.println("Part Two:   " + groups);
	}
	
	private static void removeGroup(int[][] g, int i, int j) {
		g[i][j] = 0;
		if (i > 0 && g[i-1][j] == 1)
			removeGroup(g, i-1, j);
		if (j > 0 && g[i][j-1] == 1)
			removeGroup(g, i, j-1);
		if (i < g.length - 1 && g[i+1][j] == 1)
			removeGroup(g, i+1, j);
		if (j < g[0].length - 1 && g[i][j+1] == 1)
			removeGroup(g, i, j+1);
	}
	
	private static String getHex(char[] lengthChars) {
		int[] endOfLengths = {17, 31, 73, 47, 23};
		int[] lengths = new int[lengthChars.length + 5];
		int skip = 0;
		int pos = 0;
		for (int i = 0; i < lengths.length; i++) {
			if (i < lengthChars.length) 
				lengths[i] = (int) lengthChars[i];
			else
				lengths[i] = endOfLengths[i - lengthChars.length];
		}
		int[] list = new int[256];
		for (int i = 0; i < list.length; i++)
			list[i] = i;
		for (int j = 0; j < 64; j++) {
			for (int l: lengths) {
				reverse(list, pos, l);
				//for (int i: list)
				//	System.out.print(i + " ");
				//System.out.println();
				pos = (pos + l + skip) % list.length;
				skip += 1;
			}
		}
		int[] denseHash = createDenseHash(list);
		return hashToHex(denseHash);
	}
	
	private static void reverse(int[] list, int pos, int l) {
		for (int i = 0; i < l / 2; i++) {
			int pos1 = (pos + i) % list.length;
			int pos2 = (pos + (l - 1) - i) % list.length;
			int temp = list[pos1];
			list[pos1] = list[pos2];
			list[pos2] = temp;
		}
	}
	
	
	private static int[] createDenseHash(int[] list) {
		int[] denseHash = new int[list.length / 16];
		int j = 0;
		for (int i = 0; i < denseHash.length; i++) {
			int element = list[j];
			j += 1;
			while(j % 16 != 0) {
				element = element ^ list[j];
				j += 1;
			}
			denseHash[i] = element;
		}
		return denseHash;
	}
	
	private static String hashToHex(int[] hash) {
		StringBuilder sb = new StringBuilder();
		for (int i: hash) {
			String x = Integer.toHexString(i);
			if (x.length() == 1)
				x = '0' + x;
			sb.append(x);
		}
		return sb.toString();
	}
	
	static String hexToBinary(String hex) {
	    int i = Integer.valueOf(hex, 16);
	    String bin = Integer.toBinaryString(i);
	    int len = bin.length();
	    for (int j = 0; j < 4 - len; j++)
	    	bin = '0' + bin;
	    return bin;
	}
}
