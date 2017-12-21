import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Day21 {

	public static void main(String[] args) {
        Map<String, String> rules = getRules();
        System.out.println("Part One:   " + solve(rules, 5));
        System.out.println("Part Two:   " + solve(rules, 18)); 
	}

    private static Map<String, String> getRules() {
        Map<String, String> rules = new HashMap<String, String>();
        try {
			Scanner sc = new Scanner(new BufferedReader(new FileReader("../inputs/input21.txt")));
            while (sc.hasNextLine()) {
                String l = sc.nextLine();
                String[] arr = l.split(" => ");
                rules.put(arr[0], arr[1]);
            }
                
		} catch (FileNotFoundException e) {
			e.printStackTrace();
        }
        return rules;
    }

    public static int solve(Map<String, String> rules, int numIterations) {
        char[][] pattern = patternFromString(".#./..#/###");
        for (int i = 0; i < numIterations; i++) {
            pattern = enhance(pattern, rules);
        }
        return getNumPixelsOn(pattern);
    }

    private static char[][] enhance(char[][] p, Map<String, String> rules) {
        char[][] nextPattern;
        int size = p.length;
        int inSize;
        int outSize;
        if (size % 2 == 0) {
            inSize = 2;
            outSize = 3;
        }
        else {
            inSize = 3;
            outSize = 4;
        }

        nextPattern = new char[size * outSize / inSize][size * outSize / inSize];
        for (int i = 0; i < size / inSize; i++) {
            for (int j = 0; j < size / inSize; j++) {
                char[][] inP = new char[inSize][inSize];
                for (int k = 0; k < inSize; k++) {
                    for (int l = 0; l < inSize; l++) {
                        inP[k][l] = p[i * inSize + k][j * inSize + l];
                    }
                }
                char[][] outP = getEnhancement(inP, rules);
                for (int k = 0; k < outSize; k++) {
                    for (int l = 0; l < outSize; l++) {
                        nextPattern[i * outSize + k][j * outSize + l] = outP[k][l];
                    }
                }
            }
        }
        return nextPattern;
    }

    private static char[][] getEnhancement(char[][] p, Map<String, String> rules) {
        for (int i = 0; i < 4; i++) {
            String pStr = patternToString(p);
            if (rules.keySet().contains(pStr))
                return patternFromString(rules.get(pStr));
            p = rotate(p);
        }
        p = flip(p);
        for (int i = 0; i < 4; i++) {
            String pStr = patternToString(p);
            if (rules.keySet().contains(pStr))
                return patternFromString(rules.get(pStr));
            p = rotate(p);
        }
        return null;
    }

    //Rotate 90 degrees left
    private static char[][] rotate(char[][] p) {
        int size = p.length;
        char[][] pRotated = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int row = size - j - 1;
                int col = i;
                pRotated[row][col] = p[i][j];
            }
        }
        return pRotated;
    }

    private static char[][] flip(char[][] p) {
        int size = p.length;
        char[][] pFlipped = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                pFlipped[i][j] = p[i][size - j - 1];
            }
        }
        return pFlipped;
    }

    private static String patternToString(char[][] p) {
        StringBuilder patternSB = new StringBuilder();
        for (int i = 0; i < p.length; i++) {
            char[] row = p[i];
            for (char c: row)
                patternSB.append(c);
            if (i < p.length - 1) 
                patternSB.append('/');
        }
        return patternSB.toString();
    }

    private static char[][] patternFromString(String pStr) {
        String[] arr = pStr.split("/");
        int size = arr.length;
        char[][] p = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                p[i][j] = arr[i].charAt(j);
            }
        }
        return p;
    }

    private static int getNumPixelsOn(char[][] p) {
        int numPixelsOn = 0;
        for (char[] arr: p) 
            for (char c: arr)
                if (c == '#')
                    numPixelsOn += 1;
        return numPixelsOn;
    }
}