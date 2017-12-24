import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Day22 {

	public static void main(String[] args) {
        List<char[]> input = getInput();
        Set<String> infected = getInfectedTiles(input);
        partOne(new HashSet<String>(infected));
        partTwo(new HashSet<String>(infected));
	}

    private static List<char[]> getInput() {
        List<char[]> i = new ArrayList<char[]>();
        try {
			Scanner sc = new Scanner(new BufferedReader(new FileReader("../inputs/input22.txt")));
            while (sc.hasNextLine()) {
                String l = sc.nextLine();
                char[] row = l.toCharArray();
                i.add(row);
            } 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
        }
        return i;
    }

    private static Set<String> getInfectedTiles(List<char[]> input) {
        Set<String> infected = new HashSet<String>();
        for (int i = 0; i < input.size(); i++) {
            char[] row = input.get(i);
            for (int j = 0; j < row.length; j++) {
                if (row[j] == '#') {
                    infected.add(getCoordStr(j - 12, 12 - i));
                }
            }
        }
        return infected;
    }

    private static String getCoordStr(int row, int col) {
        StringBuilder sb = new StringBuilder();
        sb.append(row); 
        sb.append(','); 
        sb.append(col);
        return sb.toString();
    }

    public static void partOne(Set<String> infected) {
        int x = 0;
        int y = 0;
        int dir = 0; //0 = N, 1 = E, 2 = S, 3 = W
        int numInfected = 0;
        for (int i = 0; i < 10000; i++) {
            String coords = getCoordStr(x, y);
            if (infected.contains(coords)) {
                dir = (dir + 1) % 4;
                infected.remove(coords);
            }
            else {
                dir = (dir - 1 + 4) % 4;
                infected.add(coords);
                numInfected += 1;
            }

            if (dir == 0)
                y += 1;
            else if (dir == 1)
                x += 1;
            else if (dir == 2)
                y -= 1;
            else if (dir == 3)
                x -= 1;
        }
        System.out.println("Part One:   " + numInfected);
    }

    public static void partTwo(Set<String> infected) {
        Map<String,Integer> states = new HashMap<String, Integer>();    //1 = weakened , 2 = infected, 3 = flagged
        for (String s: infected) 
            states.put(s, 2);
        int x = 0;
        int y = 0;
        int dir = 0; //0 = N, 1 = E, 2 = S, 3 = W
        int numInfected = 0;
        for (int i = 0; i < 10000000; i++) {
            String coords = getCoordStr(x, y);
            if (!states.keySet().contains(coords)) {
                dir = (dir + 3) % 4;
                states.put(coords, 1);
            }
            else {
                int state = states.get(coords);
                if (state == 1) {
                    states.put(coords, 2);
                    numInfected += 1;
                }
                else if (state == 2) {
                    dir = (dir + 1) % 4;
                    states.put(coords, 3);
                }
                else {
                    dir = (dir + 2) % 4;
                    states.remove(coords, 3);
                }
            }

            if (dir == 0)
                y += 1;
            else if (dir == 1)
                x += 1;
            else if (dir == 2)
                y -= 1;
            else if (dir == 3)
                x -= 1;
        }
        System.out.println("Part Two:   " + numInfected);
    }
}