
public class Day10 {

	public static void main(String[] args) {
		int[] lengths = {183,0,31,146,254,240,223,150,2,206,161,1,255,232,199,88};
		String lengthsStr = "183,0,31,146,254,240,223,150,2,206,161,1,255,232,199,88";
		//partOne(lengths.clone());
		partTwo(lengthsStr.toCharArray());
	}

	private static void partOne(int[] lengths) {
		int skip = 0;
		int pos = 0;
		int[] list = new int[256];
		for (int i = 0; i < list.length; i++)
			list[i] = i;
		for (int l: lengths) {
			reverse(list, pos, l);
			//for (int i: list)
			//	System.out.print(i + " ");
			//System.out.println();
			pos = (pos + l + skip) % list.length;
			skip += 1;
		}
		System.out.println("Part One:   " + (list[0] * list[1]));
		
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
	
	private static void partTwo(char[] lengthChars) {
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
		System.out.println("Part Two:   " + hashToHex(denseHash));
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
}
