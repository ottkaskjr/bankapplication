package ee.bcs.valiit.lessons;

import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class PlayerClone {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int nbFloors = in.nextInt(); // number of floors
        int width = in.nextInt(); // width of the area
        int nbRounds = in.nextInt(); // maximum number of rounds
        int exitFloor = in.nextInt(); // floor on which the exit is found
        int exitPos = in.nextInt(); // position of the exit on its floor
        int nbTotalClones = in.nextInt(); // number of generated clones
        int nbAdditionalElevators = in.nextInt(); // number of additional elevators that you can build
        int nbElevators = in.nextInt(); // number of elevators


        List<int[]> elevators = new ArrayList<>();
        List<int[]> floorsAndElevators = new ArrayList<>();

        System.err.println("======================");
        System.err.println("======================");
        /// 1. LOO OBJEKT, LUUBI exitFloor korda ja lisa iga korruse peale object Floor listi
        List<Floor> floors = new ArrayList<>();
        for(int i = 0; i <= exitFloor; i++){
            Floor floor = new Floor(i, exitFloor, exitPos);
            floor.floorNum = i;
            floor.floorWitdh = width;
            floors.add(floor);
        }

        // 2. ADD ALL ELEVATORS TO FLOOR OBJECTS
        for (int i = 0; i < nbElevators; i++) {
            int elevatorFloor = in.nextInt(); // floor on which this elevator is found
            int elevatorPos = in.nextInt(); // position of the elevator on its floor
            //floors.get(elevatorFloor).elevators.add(elevatorPos);
            floors.get(elevatorFloor).addElevator(elevatorPos);


            //elevators.add(new int[]{elevatorFloor, elevatorPos});
            //System.err.println(Arrays.toString(elevators.get(elevators.size()-1)));
        }

        // 3. SORT ELEVATORS IN FLOORS && COUNT EMPTY FLOORS
        int emptyFloors = 0;
        for(int i = 0; i < floors.size(); i++){
            if (floors.get(i).elevators.isEmpty()){
                emptyFloors++;
            } else {
                Collections.sort(floors.get(i).elevators);
            }

        }

        // CREATE VARIABLES OBJECT TO PASS ON EMPTYFLOORS AND ELEVATORSLEFT
        Variables vars = new Variables(emptyFloors, nbAdditionalElevators);
        System.err.println("VARIABLES:{emptyFloors: " +  vars.emptyFloors + ", elevatorsLeft: " + vars.elevatorsLeft + "}");
        /// TEST ////
        for(int i = 0; i < floors.size(); i++){
            floors.get(i).sort();
            System.err.println("Floor " + i + ": " + floors.get(i).elevators.toString());
            System.err.println(Arrays.toString(floors.get(i).trapElevators));
            //System.err.println("Floor " + i + ": " + floors.get(i).hasExit + " - " + floors.get(i).exitPos);
            //System.err.println(floors.get(i).toString());
        }
        System.err.println("======================");
        System.err.println("======================");


        int elevatorsLeft = nbAdditionalElevators;

        // game loop
        while (true) {
            int cloneFloor = in.nextInt(); // floor of the leading clone
            int clonePos = in.nextInt(); // position of the leading clone on its floor
            String direction = in.next(); // direction of the leading clone: LEFT or RIGHT


            // action: WAIT or BLOCK
            // IF NO CLONES PRESENT cloneFloor == -1
            String action = "WAIT";

            if(cloneFloor != -1){
                Floor thisFloor = floors.get(cloneFloor);

                ///// 1 IF NEXT FLOOR HAS EXIT (OR THIS FLOOR IS BEFORE EXIT FLOOR)
                if(exitFloor - 1 == cloneFloor){
                    // YES
                    Floor ExitFloor = floors.get(exitFloor);
                    // IF EXIT FLOOR HAS TRAPS
                    if(ExitFloor.trap){
                        // YES, HAS A TRAP
                        action = thisFloor.buildElevator(exitPos, clonePos, direction, vars);
                    } else {
                        // NO TRAP
                        // IF FLOOR HAS NO ELEVATORS
                        if(thisFloor.elevators.isEmpty()){
                            // BUILD ELEVATOR
                            action = thisFloor.buildElevator(clonePos, clonePos, direction, vars);
                        } else {
                            // GO TO CLOSEST ELEVATOR
                            action = thisFloor.getDirection(clonePos, direction);
                        }
                    }
                } else {
                    // NEXT FLOOR IS NOT EXIT FLOOR
                    // IF THIS FLOOR HAS ELEVATORS
                    if(thisFloor.elevators.isEmpty()){
                        // IF EXIT FLOOR
                        if(thisFloor.hasExit){
                            action = thisFloor.getDirection(clonePos, direction);
                        } else {
                            // BUILD ELEVATOR
                            action = thisFloor.buildElevator(clonePos, clonePos, direction, vars);
                        }

                    } else {
                        // THIS FLOOR IS NOT EMPTY
                        // IF THERE ARE MORE BUILDABLE ELEVATORS THAN EMPTY FLOORS
                        System.err.println("THIS FLOOR IS NOT EMPTY");
                        if(vars.elevatorsLeft > vars.emptyFloors){
                            System.err.println("THERE ARE MORE BUILDABLE ELEVATORS THAN EMPTY FLOORS");
                            // IF DISTANCE TO NEAREST ELEVATOR ON THIS FLOOR > DISTANCE TO NEAREST ELEVATOR ON NEXT FLOOR AND THERE ARE NO BUILT ELEVATORS
                            int thisFLoorClosestElevator = thisFloor.getClosestElevator(clonePos);
                            int nextFloorClosestElevator = floors.get(cloneFloor+1).getClosestElevator(clonePos);
                            System.err.println("THISFLOOR CLOSEST ELEVATOR: " + thisFLoorClosestElevator + " NEXT FLOOR CLOSEST ELEVATOR: " + nextFloorClosestElevator);
                            if(thisFLoorClosestElevator > nextFloorClosestElevator && !thisFloor.elevatorBuilt){
                                // IF DISTANCE TO THE NEAREST ELEVATOR ON THIS FLOOR IS MORE THAN HALF THE WIDTH
                                if (Math.abs(clonePos - thisFLoorClosestElevator) >= thisFloor.floorWitdh/6){
                                    //YES, BUILD ELEVATOR
                                    System.err.println("DISTANCE TO NEAREST ELEVATOR ON THIS FLOOR > DISTANCE TO NEAREST ELEVATOR ON NEXT FLOOR");
                                    action = thisFloor.buildElevator(clonePos, clonePos, direction, vars);
                                } else {
                                    // NO, GO TO CLOSEST ELEVATOR
                                    action = thisFloor.getDirection(clonePos, direction);
                                }

                            } else {
                                // NO GO TO CLOSEST ELEVATOR
                                System.err.println("NO GO TO CLOSEST ELEVATOR");
                                action = thisFloor.getDirection(clonePos, direction);
                            }
                        } else {
                            // GO TO CLOSEST ELEVATOR
                            System.err.println("GO TO CLOSEST ELEVATOR");
                            action = thisFloor.getDirection(clonePos, direction);
                        }
                    }
                }

            }
            System.err.println("FINALACTION-" + action);
            System.err.println("VARIABLES:{emptyFloors: " +  vars.emptyFloors + ", elevatorsLeft: " + vars.elevatorsLeft + "}");
            System.out.println(action);
        }

    }

}
class Variables {
    int emptyFloors;
    int elevatorsLeft;
    Variables(int emptyFloors, int elevatorsLeft){
        this.emptyFloors = emptyFloors;
        this.elevatorsLeft = elevatorsLeft;
    }
}
class Floor {
    List<Integer> elevators = new ArrayList<>();
    int floorNum;
    boolean hasExit;
    int exitPos;
    boolean trap;
    boolean elevatorBuilt = false;
    int floorWitdh;
    int[] trapElevators = new int[2];
    public void sort(){
        Collections.sort(elevators);
    }

    Floor(int thisFloor, int exitFloor , int exitPos){
        if (thisFloor == exitFloor){
            this.hasExit = true;
            this.exitPos = exitPos;
        } else {
            this.hasExit = false;
            this.exitPos = -1;
        }
    }
    public void addElevator(int position){
        this.elevators.add(position);

        // get positions of trap elevators if exitFloor
        if (this.hasExit){
            // right trap
            for(int i = 0; i < this.elevators.size(); i++){
                int elevator = this.elevators.get(i);
                if (elevator > this.exitPos){
                    this.trapElevators[1] = elevator;
                }
            }

            // left trap
            for(int i = this.elevators.size()-1; i >= 0; i--){
                int elevator = this.elevators.get(i);
                if (elevator < this.exitPos){
                    this.trapElevators[0] = elevator;
                }
            }
        }
    }
    public String buildElevator(int exitPos, int clonePos, String cloneDir, Variables vars){
        // SIIN KOODIS KUSKIL MINGI VIGA IKKAGI, EI HAKKA EHITAMA LIFTI ESSAL KORRUSEL
        String action = "WAIT";
        // CHECK CLONE DIRECTION
        // _____EXIT_______CLONE___
        if(clonePos > exitPos) {
            if (cloneDir.equals("RIGHT")){
                action = "BLOCK";
            }// otherwise "WAIT"
        }
        //___CLONE__________EXIT___
        if(clonePos < exitPos){
            if(cloneDir.equals("LEFT")){
                action = "BLOCK";
            }// otherwise "WAIT"
        }
        //_____EXIT_____
        //_____CLONE____
        if(clonePos == exitPos){
            // IF THIS POSITION DOES NOT HAVE AN EXIT OR ELEVATOR
            if(!this.posHasElevatorOrExit(exitPos)){
                if(vars.elevatorsLeft > 0){
                    action = "ELEVATOR";
                    // REMOVE FREE ELEVATOR
                    vars.elevatorsLeft -= 1;
                    // IF ROOM IS EMPTY REMOVE EMPTY ROOM
                    if (this.elevators.isEmpty()){
                        vars.emptyFloors -= 1;
                        this.elevators.add(clonePos);
                        this.elevatorBuilt = true;
                    } else {
                        // ELSE ADD ELEVATOR AND SORT
                        this.elevators.add(clonePos);
                        Collections.sort(this.elevators);
                        this.elevatorBuilt = true;
                    }

                }
            }


        }
        System.err.println(action);
        return action;
    }
    public String getDirection(int clonePos, String cloneDir){
        String action = "WAIT";

        int closestElevator = getClosestElevator(clonePos);
        System.err.println("CLOSESTELEVATOR-" + closestElevator + " clonePos-" + clonePos);
        // CHECK CLONE DIRECTION
        // _____ELEVATOR_______CLONE___
        if(clonePos > closestElevator) {
            if (cloneDir.equals("RIGHT")){
                //if(!this.posHasElevatorOrExit(clonePos)){
                //
                //}
                action = "BLOCK";
                System.err.println("ACTION-" + action);
            }// otherwise "WAIT"
        }
        //___CLONE__________ELEVATOR___
        if(clonePos < closestElevator){
            if(cloneDir.equals("LEFT")){
                action = "BLOCK";
                System.err.println("ACTION-" + action);
            }// otherwise "WAIT"
        }

        // CHECK CLONE DIRECTION
        /*
        // _____ELEVATOR_______CLONE___
        if(cloneDir == "RIGHT") {
            if (clonePos > closestElevator){
                action = "BLOCK";
                System.err.println("ACTION-" + action);
            }// otherwise "WAIT"
        }
        //___CLONE__________ELEVATOR___
        if(cloneDir == "LEFT"){
            if(clonePos <= closestElevator){
                action = "BLOCK";
                System.err.println("ACTION-" + action);
            }// otherwise "WAIT"
        }*/



        return action;
    }
    public int getClosestElevator(int clonePos){
        int closestElevator = 0;
        // SUBTRACT ALL ELEVATOR POSITIONS FROM CLONE POSITION AND GET THE SMALLEST ABSOLUTE VALUE
        System.err.println("==========================");
        if(!this.hasExit){
            int smallestDistance = 1000;
            for(int i = 0; i < this.elevators.size(); i++){
                int index = this.elevators.get(i);
                int thisDistance = Math.abs(clonePos - index);
                if(thisDistance < smallestDistance){
                    smallestDistance = thisDistance;
                    closestElevator = index;
                    System.err.println("NEW CLOSEST ELEVATOR IS " + index);
                }
            }
        } else {
            closestElevator = this.exitPos;
        }
        System.err.println("==========================");
        return closestElevator;
    }
    public boolean posHasElevatorOrExit(int pos){
        boolean result = false;
        for(int i = 0; i < this.elevators.size(); i++){
            if(this.elevators.get(i) == pos){
                result = true;
            }
        }
        if(!result){
            if (this.hasExit && this.exitPos == pos){
                result = true;
            }
        }

        return result;
    }
}