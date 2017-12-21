import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Day20 {

	public static void main(String[] args) {
        List<Particle> particles = getParticles();
        partOne(particles);
        partTwo(particles);
	}

    private static List<Particle> getParticles() {
        List<Particle> pList = new ArrayList<Particle>();
        int i = 0;
        try {
			Scanner sc = new Scanner(new BufferedReader(new FileReader("../inputs/input20.txt")));
            while (sc.hasNextLine()) {
                String l = sc.nextLine();
                Particle p = new Particle(l);
                pList.add(p);
            }
                
		} catch (FileNotFoundException e) {
			e.printStackTrace();
        }
        return pList;
    }

    public static void partOne(List<Particle> pList) {
        int closestParticle = 0;
        int minAcc = pList.get(0).getManhattanAcceleration();
        for (int i = 0; i < pList.size(); i++) {
            Particle p = pList.get(i);
            int acc = p.getManhattanAcceleration();
            if (acc < minAcc) {
                closestParticle = i;
                minAcc = acc;
            }
        }
        System.out.println("Part One:   " + closestParticle);

    }

    public static void partTwo(List<Particle> pList) {
        Set<Integer> remaining = new HashSet<Integer>();
        for (int x = 0; x < pList.size(); x++)
            remaining.add(x);
        for (int i = 0; i < pList.size() - 1; i++) {
            Particle p0 = pList.get(i);
            int tCollision = -1;
            List<Integer> collidedParticles = new ArrayList<Integer>();
            for (int j = i + 1; j < pList.size(); j++) {
                Particle p1 = pList.get(j);
                if (p1.removed)
                    continue;
                int t = 0;
                boolean divergent = false;
                boolean collided = false;
                while (!collided && !divergent) {
                    double oldDist = p0.getDist(p1, t);
                    if (oldDist == 0) {
                        collided = true;
                        continue;
                    }
                    double newDist = p0.getDist(p1, t + 1);
                    if (p0.isSpeedingUp(t) && p1.isSpeedingUp(t) && newDist > oldDist) {
                        divergent = true;
                        continue;
                    }
                    t += 1;
                }
                if (collided) {
                    System.out.println(i + " " + j + " " + t);
                    if (tCollision == -1 || t < tCollision) {
                        tCollision = t;
                        collidedParticles = new ArrayList<Integer>();
                        collidedParticles.add(j);
                    }
                    else if (t == tCollision) {
                        collidedParticles.add(j);
                    }
                }
            }
            if (!collidedParticles.isEmpty()) {
                p0.removed = true;
                remaining.remove(i);
                for (Integer y: collidedParticles) {
                    Particle p = pList.get(y);
                    p.removed = true;
                    remaining.remove(y);
                }
            }
        }
        System.out.println("Part Two:   " + remaining.size());
    }

public static void partTwoRetry(List<Particle> pList) {
    int remaining = 1000;
    int minAccParticle = 0;
    int smallestAcc = pList.get(0).getManhattanAcceleration();
    for (int i = 1; i < pList.size(); i++) {
        int acc = pList.get(i).getManhattanAcceleration();
        if (acc < smallestAcc) {
            smallestAcc = acc;
            minAccParticle = i;
        }
    }
    boolean allMovingAwayFromOrigin = false;
    boolean minAccParticleClosestToOrigin = false;
    int time = 0;
    Map<Integer, Integer> currentDistances = new HashMap<Integer, Integer>();
    for (int i = 0; i < pList.size(); i++) {
        currentDistances.put(i, pList.get(i).getManhattanDistance(time));
    }
    while (!allMovingAwayFromOrigin || !minAccParticleClosestToOrigin) {
        for (int i = 0; i < pList.size() - 1; i++) {
            Particle p0 = pList.get(i);
            if (p0.removed) continue;
            Set<Integer> removed = new HashSet<Integer>();
            for (int j = i + 1; j < pList.size(); j++) {
                Particle p1 = pList.get(j);
                if (p1.removed) continue;
                if (p0.getDist(p1, time) == 0) {
                    if (!p0.removed) {
                        p0.removed = true;
                        remaining--;
                    }
                    p1.removed = true;
                    remaining--;
                }
            }
        }
        time += 1;
        allMovingAwayFromOrigin = true;
        for (Particle p : pList) {
            int originalDist = p.getManhattanDistance(0);
            int currentDist = p.getManhattanDistance(time);
            if (originalDist > currentDist) {
                allMovingAwayFromOrigin = false;
                break;
            }
        }

        minAccParticleClosestToOrigin = true;
        int d = pList.get(minAccParticle).getManhattanDistance(time);
        for (int i = 0; i < pList.size(); i++) {
            if (i != minAccParticle) {
                if (d >= pList.get(i).getManhattanDistance(time)) {
                    minAccParticleClosestToOrigin = false;
                    break;
                }
            }
        }
    }
    System.out.println("Part Two:   " + remaining + " " + time);
    }      

    public static void partTwoRetryTwo(List<Particle> pList) {
        int remaining = pList.size();
        for (int i = 0; i < pList.size() - 1; i++) {
            Particle p0 = pList.get(i);
            int tCollision = -1;
            List<Integer> collidedParticles = new ArrayList<Integer>();
            for (int j = i + 1; j < pList.size(); j++) {
                Particle p1 = pList.get(j);
                if (p1.removed)
                    continue;
                double a = .5 * (p0.accX - p1.accX);
                double b = (a + (p0.velX - p1.velX));
                double c = p0.posX - p1.posX;
                List<Integer> roots = quadraticEquation(a, b, c);
                for (int r: roots) {
                    if (p0.areCollided(p1, r)) {//p0.getPosY(r) == p1.getPosY(r) && p0.getPosZ(r) == p1.getPosZ(r)) {
                        if (r == tCollision)
                            collidedParticles.add(j);
                        else if (r < tCollision || tCollision == -1) {
                            tCollision = r;
                            collidedParticles = new ArrayList<Integer>();
                            collidedParticles.add(j);
                        }
                    }
                }
            }
            if (!collidedParticles.isEmpty()) {
                p0.removed = true;
                remaining -= 1;
                for (Integer y: collidedParticles) {
                    Particle p = pList.get(y);
                    p.removed = true;
                    remaining -= 1;
                }
            }
        }
        System.out.println("Part Two:   " + remaining);
    }

    public static List<Integer> quadraticEquation(double a, double b, double c) {
        //Only returns positive integer roots
        List<Integer> roots = new ArrayList<Integer>();
        double d = b * b - 4 * a * c;
        if (d > 0) {
            double root1 = (-1 * b + Math.sqrt(d)) / (2 * a);
            double root2 = (-1 * b - Math.sqrt(d)) / (2 * a);
            if (root1 > 0 && (root1 == Math.floor(root1)))
                roots.add( (int) root1);
            if (root2 > 0 && (root2 == Math.floor(root2)))
                roots.add( (int) root2);
        }
        return roots;
    }
}

class Particle {
    int posX; int posY; int posZ;
    int velX; int velY; int velZ;
    int accX; int accY; int accZ;
    boolean removed;

    public Particle(String line) {
        String[] info = line.split(", ");
        int[] pos = getVals(info[0]);
        int[] vel = getVals(info[1]);
        int[] acc = getVals(info[2]);
        posX = pos[0]; posY = pos[1]; posZ = pos[2];
        velX = vel[0]; velY = vel[1]; velZ = vel[2];
        accX = acc[0]; accY = acc[1]; accZ = acc[2];
        removed = false;
    }

    private int[] getVals(String valsStr) {
        valsStr = valsStr.substring(valsStr.indexOf('<') + 1, valsStr.indexOf('>'));
        String[] valStrs = valsStr.split(",");
        int[] vals = new int[3];
        for (int i = 0; i < 3; i++) {
            vals[i] = Integer.parseInt(valStrs[i]);
        }
        return vals;
    }

    public int getManhattanDistance(int time) {
        int x = posX; int vX = velX;
        int y = posY; int vY = velY;
        int z = posZ; int vZ = velZ;
        for (int i = 0; i < time; i++) {
            vX += accX; vY += accY; vZ += accZ;
            x += vX; y += vY; z += vZ;
        }
        int dist = Math.abs(x) + Math.abs(y) + Math.abs(z);
        return dist;
    }

    public int getManhattanAcceleration() {
        int acc = Math.abs(accX) + Math.abs(accY) + Math.abs(accZ);
        return acc;
    }

    public double getDist(Particle o, int t) {
        double[] thisPos = getPos(t);
        double[] oPos = o.getPos(t);
        double xDist = thisPos[0] - oPos[0];
        double yDist = thisPos[1] - oPos[1];
        double zDist = thisPos[2] - oPos[2];
        double dist = Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2) + Math.pow(zDist, 2));
        return dist;
    }

    public boolean areCollided(Particle o, int t) {
        double[] thisPos = getPos(t);
        double[] oPos = o.getPos(t);
        double xDist = thisPos[0] - oPos[0];
        double yDist = thisPos[1] - oPos[1];
        double zDist = thisPos[2] - oPos[2];
        boolean collided = (xDist == 0 && yDist == 0 && zDist == 0);
        return collided;
    }

    private double[] getPos(int t) {
        double x = getPos(posX, velX, accX, t);
        double y = getPos(posY, velY, accY, t);
        double z = getPos(posZ, velZ, accZ, t);
        double[] pos = {x, y, z};
        return pos;
    }

    public double getPos(int pos, int vel, int acc, int t) {
        return pos + vel * t + acc * .5 * (Math.pow(t, 2) + t);
    }

    public double getPosY(int t) {
        return posY + velY * t + accY * .5 * (Math.pow(t, 2) + t);
    }

    public double getPosZ(int t) {
        return posZ + velZ * t + accZ * .5 * (Math.pow(t, 2) + t);
    }

    public boolean isSpeedingUp(int t) {
        double v0 = getVelocity(t);
        double v1 = getVelocity(t + 1);
        return (v1 >= v0);
    }

    private double getVelocity(int t) {
        int vX = velX + accX * t;
        int vY = velY + accY * t;
        int vZ = velZ + accZ * t;
        double v = Math.sqrt(Math.pow(vX, 2) + Math.pow(vY, 2) + Math.pow(vZ, 2));
        return v;
    }
}