import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Day19 {

	public static void main(String[] args) {
        List<char[]> diagram = getDiagram();
		solve(diagram);
	}

    private static List<char[]> getDiagram() {
        List<char[]> d = new ArrayList<char[]>();
        int i = 0;
        try {
			Scanner sc = new Scanner(new BufferedReader(new FileReader("../inputs/input19.txt")));
            while (sc.hasNextLine()) {
                String l = sc.nextLine();
                d.add(l.toCharArray());

            }
                
		} catch (FileNotFoundException e) {
			e.printStackTrace();
        }
        return d;
    }

    private static void solve(List<char[]> d) {
        int steps = 0;
        int x = 0;
        int y = 0;
        StringBuilder lettersSB = new StringBuilder();
        boolean done = false;
        int dir = 3; //1=N, 2=E, 3=S, 4=W
        char[] row = d.get(y);
        for (int i = 0; i < row.length; i++) {
            if (row[i] == '|')
                x = i;
        }

        while (!done) {
            steps += 1;
            if (dir == 1)
                y -= 1;
            else if (dir == 2)
                x += 1;
            else if (dir == 3)
                y += 1;
            else if (dir == 4)
                x -= 1;

            row = d.get(y);
            char c = row[x];

            if (c >= 'A' && c <= 'Z')
                lettersSB.append(c);
            else if (c == '+') {
                if (dir == 1 || dir == 3) {
                    c = row[x-1];
                    if (c != ' ')
                        dir = 4;
                    else 
                        dir = 2;
                }
                else {
                    row = d.get(y - 1);
                    c = row[x];
                    if (c != ' ')
                        dir = 1;
                    else
                        dir = 3;
                }
            }
            else if (c == ' ')
                done = true;
        }

        System.out.println("Part One:   " + lettersSB.toString());
        System.out.println("Part Two:   " + steps);
    }
}