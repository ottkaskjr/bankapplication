package ee.bcs.valiit.lessons;

import java.util.*;
import java.io.*;
import java.math.*;
/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
public class CloneGame {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int nbFloors = in.nextInt(); // number of floors
        int width = in.nextInt(); // width of the area
        int nbRounds = in.nextInt(); // maximum number of rounds
        int exitFloor = in.nextInt(); // floor on which the exit is found
        int exitPos = in.nextInt(); // position of the exit on its floor
        int nbTotalClones = in.nextInt(); // number of generated clones
        int nbAdditionalElevators = in.nextInt(); // ignore (always zero)
        int nbElevators = in.nextInt(); // number of elevators
        // TESTING
        System.err.println("INIT");
        System.err.println("nbFloors - " + nbFloors);
        System.err.println("width - " + width);
        System.err.println("nbRounds - " + nbRounds);
        System.err.println("exitFloor - " + exitFloor);
        System.err.println("exitPos - " + exitPos);
        System.err.println("nbTotalClones - " + nbTotalClones);
        System.err.println("nbAdditionalElevators - " + nbAdditionalElevators);
        System.err.println("nbElevators - " + nbElevators);
        System.err.println("====");

        List<int[]> elevators = new ArrayList<>();
        for (int i = 0; i < nbElevators; i++) {
            int elevatorFloor = in.nextInt(); // floor on which this elevator is found
            int elevatorPos = in.nextInt(); // position of the elevator on its floor
            elevators.add(new int[]{elevatorFloor, elevatorPos});
        }

        // game loop
        while (true) {
            int cloneFloor = in.nextInt(); // floor of the leading clone
            int clonePos = in.nextInt(); // position of the leading clone on its floor
            String direction = in.next(); // direction of the leading clone: LEFT or RIGHT

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            System.err.println("cloneFloor - " + cloneFloor);
            System.err.println("clonePos - " + clonePos);
            System.err.println("direction - " + direction);

            int elevatorPos = 0;
            for(int[] elev: elevators){
                if(elev[0] == cloneFloor){
                    elevatorPos = elev[1];
                }
            }
            System.out.println(getDirection(width, nbFloors, cloneFloor, clonePos, exitPos, direction, elevatorPos)); // action: WAIT or BLOCK
        }
    }
    public static String getDirection(int width, int nbFloors, int cloneFloor, int clonePos, int exitPos, String direction, int elevatorPos){
        String action = "WAIT";
        if (!direction.equals("NONE")){
            if(clonePos == 0 || clonePos == width-1) {
                action = "BLOCK";
            }
            // upperfloor
            if (cloneFloor == nbFloors-1){
                // exit position
                if(direction.equals("LEFT")){
                    if(clonePos <= exitPos){
                        action = "BLOCK";
                    }
                } else if(direction.equals("RIGHT")) {
                    if(clonePos >= exitPos){
                        action = "BLOCK";
                    }
                }
            } else {
                // elevator position
                if(direction.equals("LEFT")){
                    if(clonePos < elevatorPos){
                        action = "BLOCK";
                    }
                } else if(direction.equals("RIGHT")) {
                    System.err.println("GOT HERE!");
                    System.err.println("clonePos " + clonePos);
                    System.err.println("elevatorPos " + elevatorPos);
                    if(clonePos > elevatorPos){
                        System.err.println("CLONE POS >= elevatorPos!");
                        action = "BLOCK";
                    }
                }
            }
        }

        return action;
    }
}
