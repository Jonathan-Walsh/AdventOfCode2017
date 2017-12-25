import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public class Day25 {

    
    
	public static void main(String[] args) {
		partOne();
		partTwo();
	}

	private static void partOne() {
        TMachine t = new TMachine(0, 'A');
        for (int i = 0; i < 12261543; i++) {
            t.step();
        }
		System.out.println("Part One: " + t.getChecksum());
    }
    
	private static void partTwo() {
		System.out.println("Part Two: ");
	}
}

class TMachine {
    int cursor;
    Set<Integer> ones;
    char state;

    public TMachine(int c, char s) {
        cursor = c;
        ones = new HashSet<Integer>();
        state = 'A';
    }

    public void step() {
        boolean isZero = !ones.contains(cursor);
        if (state == 'A') {
            if (isZero) {
                ones.add(cursor);
                cursor += 1;
                state = 'B';
            }
            else {
                ones.remove(cursor);
                cursor -= 1;
                state = 'C';
            }
        }
        else if (state == 'B') {
            if (isZero) {
                ones.add(cursor);
                cursor -= 1;
                state = 'A';
            }
            else {
                cursor += 1;
                state = 'C';
            }
        }
        else if (state == 'C') {
            if (isZero) {
                ones.add(cursor);
                cursor += 1;
                state = 'A';
            }
            else {
                ones.remove(cursor);
                cursor -= 1;
                state = 'D';
            }
        }
        else if (state == 'D') {
            if (isZero) {
                ones.add(cursor);
                cursor -= 1;
                state = 'E';
            }
            else {
                cursor -= 1;
                state = 'C';
            }
        }
        else if (state == 'E') {
            if (isZero) {
                ones.add(cursor);
                cursor += 1;
                state = 'F';
            }
            else {
                cursor += 1;
                state = 'A';
            }
        }
        else {
            if (isZero) {
                ones.add(cursor);
                cursor += 1;
                state = 'A';
            }
            else {
                cursor += 1;
                state = 'E';
            }
        }
    }

    public int getChecksum() {
        return ones.size();
    }
}
