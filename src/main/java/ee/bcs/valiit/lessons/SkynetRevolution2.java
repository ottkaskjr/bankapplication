package ee.bcs.valiit.lessons;

import java.util.*;
import java.io.*;
import java.math.*;

    /**
     * Auto-generated code below aims at helping you parse
     * the standard input according to the problem statement.
     **/
    class Player {

        public static void main(String args[]) {
            Scanner in = new Scanner(System.in);
            int N = in.nextInt(); // the total number of nodes in the level, including the gateways
            int L = in.nextInt(); // the number of links
            int E = in.nextInt(); // the number of exit gateways

            // HASHMAP FOR NODES
            Map<Integer, Node> nodes = new HashMap<>();
            for (int i = 0; i < L; i++) {
                int N1 = in.nextInt(); // N1 and N2 defines a link between these nodes
                int N2 = in.nextInt();

                int[] twoNodes = new int[]{N1, N2};
                for(int Nnum: twoNodes){
                    int otherN = Nnum == N1 ? N2 : N1;
                    //ADD NODE/LINK TO HASHMAP
                    if(nodes.containsKey(Nnum)){
                        nodes.get(Nnum).links.add(otherN);
                    } else {
                        nodes.put(Nnum, new Node(Nnum));
                        nodes.get(Nnum).links.add(otherN);
                    }
                }

            }
            for (int i = 0; i < E; i++) {
                int EI = in.nextInt(); // the index of a gateway node
                // ADD EXIT BOOLEAN TO NODE OBJECTS
                nodes.get(EI).exit = true;
            }

            // FIX all nodes by sorting the normal and protected links
            nodes.forEach((key, value) -> {
                Node tempNode = nodes.get(key);
                if(!tempNode.exit){
                    // loop node links
                    for(int i = tempNode.links.size()-1; i >= 0; i--){
                        // if this nodelink !exit
                        if(!nodes.get(tempNode.links.get(i)).exit){
                            // add to offLinks
                            int nodeNum = tempNode.links.get(i);
                            tempNode.offLinks.add(nodeNum);
                            tempNode.links.remove(i);
                        }
                    }
                }
            });

            // TEST
            nodes.forEach((key, value) -> {
                System.err.println("{" + key + ": links:" + value.links.toString() + " offLinks: " + value.offLinks.toString() + " " + value.exit + " }");
            });



            // game loop
            while (true) {
                int SI = in.nextInt(); // The index of the node on which the Skynet agent is positioned this turn
                boolean danger = false;
                int first = 0;
                int second = 0;

                // KUI AGENT ON OTSE EXITI KÕRVAL
                System.err.println("AGENTPOS: " + SI);
                System.err.println("DEBUG1:" + nodes.get(1).links.toString());
                if(nodes.get(SI).hasLinkToExit()){
                    System.err.println("THIS NODE HAS DIRECT LINK TO EXIT");
                    first = SI;
                    System.err.println("first: " + first);
                    second = nodes.get(SI).getFirstLink();
                    System.err.println("second: " + second);
                    // REMOVE THIS NODE NUM FROM THE OTHER NODE
                    nodes.get(second).removeLink(SI);
                    // REMOVE OTHER NODE NUM FROM THIS NODE
                    nodes.get(SI).removeLink(second);
                    danger = true;
                }
                System.err.println("DEBUG2:" + nodes.get(1).links.toString());

                if(!danger){ // NO DIRECT DANGER
                    // GET NODE THAT'S NOT EXIT BUT HAS TWO LINKS
                    // 1 FIRST IF AGENT IS NEXT TO THE NODE THAT HAS TWO LINKS
                    for (Map.Entry<Integer, Node> node : nodes.entrySet()) {
                        int key = node.getKey();
                        Node thisNode = node.getValue();
                        if(!thisNode.exit && thisNode.links.size() > 1){
                            // IF AGENT IS INCLUDED IN THIS NODE OFFLINKS (GET FIRST)
                            if(thisNode.offLinks.contains(SI)){
                                System.err.println("1 - NODE THAT'S NOT EXIT BUT HAS TWO LINKS");
                                first = key;
                                second = thisNode.links.get(0);
                                // REMOVE THIS NODE NUM FROM THE OTHER NODE
                                nodes.get(second).removeLink(first);
                                // REMOVE OTHER NODE NUM FROM THIS NODE
                                nodes.get(first).removeLink(second);
                                danger = true;
                                break;
                            }
                        }
                    }
                    System.err.println("DEBUG3:" + nodes.get(1).links.toString());

                    //==============================================
                    //==============================================
                    // LISA EKSPERIMENTAAL SIIA
                    //System.err.println(nodes.get(SI).mostVulnerableExit(nodes));

                    //==============================================
                    //==============================================




                    // 2 ANY NODE THAT HAS TWO LINKS
                    if(!danger){
                        //int mostLinks = 0;
                        for (Map.Entry<Integer, Node> node : nodes.entrySet()) {

                            int key = node.getKey();
                            Node thisNode = node.getValue();
                            // LOOBI KÕIK LÄBI JA VÕTA NODE JA EXIT MILLEL ON KÕIGE ROHKEM LINKE
                            if(!thisNode.exit && thisNode.links.size() > 1){
                                System.err.println("2 - NODE THAT'S NOT EXIT BUT HAS TWO LINKS");


                                //mostLinks = thisNode.links.size();
                                first = key;
                                second = thisNode.links.get(0);
                                // REMOVE THIS NODE NUM FROM THE OTHER NODE
                                nodes.get(second).removeLink(first);
                                // REMOVE OTHER NODE NUM FROM THIS NODE
                                nodes.get(first).removeLink(second);
                                danger = true;
                                System.err.println(first);
                                System.err.println(second);


                                break;
                            }
                        }
                    }
                    System.err.println("DEBUG4:" + nodes.get(1).links.toString());
                }
                System.err.println("DEBUG5:" + nodes.get(1).links.toString());
                // ELSE REMOVE ANY LINK
                if(!danger){
                    for (Map.Entry<Integer, Node> node : nodes.entrySet()) {
                        int key = node.getKey();
                        Node thisNode = node.getValue();
                        if(thisNode.links.size() > 0){
                            System.err.println("NODE THAT HAS ANY LINK");
                            first = key;
                            second = thisNode.links.get(0);
                            // REMOVE THIS NODE NUM FROM THE OTHER NODE
                            nodes.get(second).removeLink(first);
                            // REMOVE OTHER NODE NUM FROM THIS NODE
                            nodes.get(first).removeLink(second);
                            danger = true;
                            break;
                        }
                    }
                }
                System.err.println("DEBUG6:" + nodes.get(1).links.toString());
                // REMOVE CONNECTIONS
                //nodes.get(SI).links.remove(second);

                // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");


                // Example: 3 4 are the indices of the nodes you wish to sever the link between
                System.out.println(first + " " + second);
            }
        }
    }
    class Node{
        public int nodeNum;
        public List<Integer> links = new ArrayList<>();
        public List<Integer> offLinks = new ArrayList<>(); // protected links
        public boolean exit;
        Node(int nodeNum){
            this.nodeNum = nodeNum;
            this.exit = false;
        }
        public boolean hasAgent(int node){// IF NODE HAS AGENT
            if (this.links.contains(node)){
                return true;
            }
            return false;
        }

        public boolean hasLinkToExit(){ // if this node has open link to exit
            return !this.links.isEmpty();
        }

        public int getFirstLink(){
            return this.links.get(0);
        }

        public void removeLink(int num){
            for(int i = this.links.size()-1; i >= 0; i--){
                if(this.links.get(i).equals(num)){
                    this.links.remove(i);
                    break;
                }
            }
        }

        // find most vulnerable exit
        public int mostVulnerableExit(Map<Integer, Node> nodes){
            for(int i = 0; i < this.offLinks.size(); i++){
                // exclude safe nodes(nodes without any direct exit)
                int nodeLink = this.offLinks.get(i);
                int steps = 0;
                int numberOfExitLinks = 0;
                Node node = nodes.get(nodeLink);
                if(node.links.size() > 0){
                    int currentPath = nodeLink;
                    while(checkPath(nodes, currentPath)){
                        System.err.println("next " + currentPath);
                        currentPath = getNextPath(nodes, currentPath);
                    }
                    System.err.println("PATH OVER");

                /*
                PathData pathData = new PathData(1, true);


                while(pathData.isConnectedToExit){
                    steps++;
                    numberOfExitLinks += pathData.links;

                }*/
                }
            }

            return 0;
        }



        public boolean checkPath(Map<Integer, Node> nodes, int nodeNum){
            if(nodes.get(nodeNum).links.size() > 0) {
                return true;
            }
            return false;
        }
        public int getNextPath(Map<Integer, Node> nodes, int current){
            int next = nodes.get(current).offLinks.get(0) != current ? nodes.get(current).offLinks.get(0) : nodes.get(current).offLinks.get(1);
            return next;
        }

        // FIND CLOSEST DOUBLE EXIT NODE THAT HAS THE LEAST NUMBER OF NO-LINK NODES
        public int findVulnerableDoubleExit(Map<Integer, Node> nodes){
        /*
        int shortestPath = 50;
        for (Map.Entry<Integer, Node> node : nodes.entrySet()) {
                    int key = node.getKey();
                    Node thisNode = node.getValue();
                    if(key != nodeNum){
                        if(thisNode.links.size() > 1 && !thisNode.exit){

                        }
                    }

                }*/
            // ALTERNATIIV
            // SAADA TAGASI NODE, MILLE IGAL NO-LINK NUMBRIL(VIST AINULT KAHEL) ON SUURIM ARV LINKE
            int result = 0;
            int mostLinks = 0;
            for (Map.Entry<Integer, Node> node : nodes.entrySet()) {
                int key = node.getKey();
                Node thisNode = node.getValue();
                int linkAmount = 0;
                if(key != nodeNum){

                    if(thisNode.links.size() > 1 && !thisNode.exit){
                        if(thisNode.offLinks.size() > 0){
                            for(int offLink: thisNode.offLinks){
                                linkAmount += nodes.get(offLink).links.size();
                            }
                        }
                    }

                }
                if(linkAmount > mostLinks){
                    mostLinks = linkAmount;
                    result = thisNode.nodeNum;
                }
            }
            System.err.println("Vulnerable exit ");
            return result;
        }


    }
    class PathData{
        boolean isConnectedToExit;
        int links;
        PathData(int links, boolean isConnectedToExit){
            this.links = links;
            this.isConnectedToExit = isConnectedToExit;
        }
    }



