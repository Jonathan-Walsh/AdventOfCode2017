import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Day24 {

	public static void main(String[] args) {
        List<Pipe> pipes = getPipes();
        partOne(pipes);
        partTwo(pipes);
	}

    private static List<Pipe> getPipes() {
        List<Pipe> pipes = new ArrayList<Pipe>();
        try {
			Scanner sc = new Scanner(new BufferedReader(new FileReader("../inputs/input24.txt")));
            while (sc.hasNextLine()) {
                String l = sc.nextLine();
                String[] arr = l.split("/");
                int p1 = Integer.parseInt(arr[0]);
                int p2 = Integer.parseInt(arr[1]);
                pipes.add(new Pipe(p1, p2));
            } 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
        }
        return pipes;
    }

    public static void partOne(List<Pipe> pipes) {
        boolean[] used = new boolean[pipes.size()];
        for (int i = 0; i < used.length; i++)
            used[i] = false;
        int strongestBridge = 0;
        for (int i = 0; i < pipes.size(); i++) {
            Pipe p = pipes.get(i);
            if (p.port1 == 0 || p.port2 == 0) {
                List<Pipe> pipesCopy = new ArrayList<Pipe>(pipes);
                if (p.port1 == 0)
                    pipesCopy.get(i).isPort1Used = true;
                else
                    pipesCopy.get(i).isPort2Used = true;
                boolean[] usedCopy = used.clone();
                usedCopy[i] = true;
                int s = findStrongestBridge(pipesCopy, usedCopy, p); 
                if (strongestBridge < s)
                    strongestBridge = s;
            }
        }
        System.out.println("Part One:   " + strongestBridge);
    }

    public static void partTwo(List<Pipe> pipes) {
        boolean[] used = new boolean[pipes.size()];
        for (int i = 0; i < used.length; i++)
            used[i] = false;
        Score s = new Score(0,0);
        for (int i = 0; i < pipes.size(); i++) {
            Pipe p = pipes.get(i);
            if (p.port1 == 0 || p.port2 == 0) {
                List<Pipe> pipesCopy = new ArrayList<Pipe>(pipes);
                if (p.port1 == 0)
                    pipesCopy.get(i).isPort1Used = true;
                else
                    pipesCopy.get(i).isPort2Used = true;
                boolean[] usedCopy = used.clone();
                usedCopy[i] = true;
                s = findStrongestBridge2(pipesCopy, usedCopy, p); 
            }
        }
        System.out.println("Part Two:   " + s.score);
    }

    private static int findStrongestBridge(List<Pipe> pipes, boolean[] used, Pipe lastP) {
        int s = score(pipes, used);
        int port;
        if (lastP.isPort1Used)
            port = lastP.port2;
        else
            port = lastP.port1;
        for (int i = 0; i < pipes.size(); i++) {
            if (!used[i]) {
                Pipe p = pipes.get(i);
                if (p.port1 == port) {
                    List<Pipe> pipesCopy = new ArrayList<Pipe>(pipes);
                    pipesCopy.get(i).isPort1Used = true;
                    boolean[] usedCopy = used.clone();
                    usedCopy[i] = true;
                    int s2 = findStrongestBridge(pipesCopy, usedCopy, p); 
                    if (s2 > s)
                        s = s2;
                    p.isPort1Used = false;
                }
                else if (p.port2 == port) {
                    List<Pipe> pipesCopy = new ArrayList<Pipe>(pipes);
                    pipesCopy.get(i).isPort2Used = true;
                    boolean[] usedCopy = used.clone();
                    usedCopy[i] = true;
                    int s2 = findStrongestBridge(pipesCopy, usedCopy, p); 
                    if (s2 > s)
                        s = s2;
                    p.isPort2Used = false;
                }
            }
        }
        return s;
    }

    private static Score findStrongestBridge2(List<Pipe> pipes, boolean[] used, Pipe lastP) {
        int np = 0;
        for (int i = 0; i < used.length; i++) {
            if (used[i])
                np += 1;
        }
        int score = score(pipes, used);
        Score x = new Score(score, np);
        int port;
        if (lastP.isPort1Used)
            port = lastP.port2;
        else
            port = lastP.port1;
        for (int i = 0; i < pipes.size(); i++) {
            if (!used[i]) {
                Pipe p = pipes.get(i);
                if (p.port1 == port) {
                    List<Pipe> pipesCopy = new ArrayList<Pipe>(pipes);
                    pipesCopy.get(i).isPort1Used = true;
                    boolean[] usedCopy = used.clone();
                    usedCopy[i] = true;
                    Score x2 = findStrongestBridge2(pipesCopy, usedCopy, p); 
                    if (x2.numPieces >= x.numPieces) {
                        if (x2.numPieces > x.numPieces || x2.score > x.score)
                            x.score = x2.score;
                        x.numPieces = x2.numPieces;
                    }
                    p.isPort1Used = false;
                }
                else if (p.port2 == port) {
                    List<Pipe> pipesCopy = new ArrayList<Pipe>(pipes);
                    pipesCopy.get(i).isPort2Used = true;
                    boolean[] usedCopy = used.clone();
                    usedCopy[i] = true;
                    Score x2 = findStrongestBridge2(pipesCopy, usedCopy, p); 
                    if (x2.numPieces >= x.numPieces) {
                        if (x2.numPieces > x.numPieces || x2.score > x.score)
                            x.score = x2.score;
                        x.numPieces = x2.numPieces;
                    }
                    p.isPort2Used = false;
                }
            }
        }
        return x;
    }

    private static int score(List<Pipe> pipes, boolean[] used) {
        int s = 0;
        for (int i = 0; i < pipes.size(); i++) {
            if (used[i]) {
                s += pipes.get(i).port1 + pipes.get(i).port2;
            }
        }
        return s;
    }

    public static void partTwo() {
        System.out.println("Part Two:   ");
    }


}

class Pipe {
    int port1;
    int port2;
    boolean isPort1Used;
    boolean isPort2Used;

    public Pipe(int p1, int p2) {
        port1 = p1;
        port2 = p2;
        isPort1Used = false;
        isPort2Used = false;
    }
}

class Score {
    int score;
    int numPieces;

    public Score (int s, int np) {
        score = s;
        numPieces = np;
    }
}