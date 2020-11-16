package ee.bcs.valiit.lessons;

import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/

/*
LISATUD IGAKS JUHUKS BACKUPINA
 */
public class SkynetRevolution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt(); // the total number of nodes in the level, including the gateways
        int L = in.nextInt(); // the number of links
        int E = in.nextInt(); // the number of exit gateways

        //ArrayList<int[]> connections = new ArrayList<>();
        ArrayList<ArrayList> connections = new ArrayList<>();

        for (int i = 0; i < L; i++) {
            int N1 = in.nextInt(); // N1 and N2 defines a link between these nodes
            int N2 = in.nextInt();
            //connections.add(new int[]{N1, N2});
            ArrayList<Integer> connection = new ArrayList<>();
            //connections.add(N1+"-"+N2);
            connection.add(N1);
            connection.add(N2);
            connections.add(connection);
        }
        ArrayList<Integer> exitNodes = new ArrayList<>();
        for (int i = 0; i < E; i++) {
            int EI = in.nextInt(); // the index of a gateway node
            exitNodes.add(EI);
        }
        /*
        System.err.println("=== EXITNODES ===");
        for(int num: exitNodes){

            System.err.println(num);
        }
        System.err.println("=== ====== ===");*/
        //int connection = 1;

        /*
        for(ArrayList arr: connections){

            System.err.println(arr);
        }*/

        // game loop
        while (true) {
            System.err.println("========== TURN ==========");
            int SI = in.nextInt(); // The index of the node on which the Skynet agent is positioned this turn
            int first = 0;
            int second = 1;
            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            // LOOP CONNECTIONS

            boolean priority = false;
            int indexToRemove = 0;
            // FIRST CONNECTIONS LOOPS (FIND VULNERABLE CONNECTION)
            for (int i = 0; i < connections.size(); i++){
                // PLAAN B
                // LOOP EXITNODES TWICE
                // FIRST FOR VULNERABLE CONNECTION

                System.err.println("===== exitNodes loop cycle " + i + " =====");
                for(int j = 0; j < exitNodes.size(); j++){
                    System.err.println("-----------");
                    System.err.println(connections.get(i).contains(SI) + " - " + connections.get(i));
                    System.err.println(connections.get(i).contains(Integer.parseInt(exitNodes.get(j).toString())));
                    System.err.println("-----------");
                    if(connections.get(i).contains(SI) && connections.get(i).contains(Integer.parseInt(exitNodes.get(j).toString()))){
                        System.err.println("FOUND VULNERABLE CONNECTION");
                        first = exitNodes.get(j);
                        second = SI;
                        priority = true;
                        break;
                    }

                }
                System.err.println("===============================");
                if(priority){
                    indexToRemove = i;
                    System.err.println("PRIORITY");
                    break;
                }

            }

            // SECOND CONNECTIONS LOOPS (FIND ANY CONNECTION CONTAINING THE AGENT)
            if (!priority){
                for (int i = 0; i < connections.size(); i++){
                    first = SI;
                    if(connections.get(i).contains(first)){
                        if(!connections.get(i).get(0).equals(first)){
                            //System.err.println("EQUALS");
                            second = Integer.parseInt(connections.get(i).get(0).toString());
                        } else {
                            //System.err.println("NOPE");
                            second = Integer.parseInt(connections.get(i).get(1).toString());
                        }
                        indexToRemove = i;
                        break;
                    }
                }
            }



            connections.remove(indexToRemove);

            System.err.println(first + " " + second);
            System.out.println(first + " " + second);
            // Example: 0 1 are the indices of the nodes you wish to sever the link between
            // TEST 1 & 2
            /*System.out.println("0 6");*/
        }
    }
}