package ee.bcs.valiit;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

public class Programs {
    public static String listMethods(){
        return "min-max-fibonacci-abs-iseven-wholenumbers-multitable-algorithm-randomgame-readfile-morsecode";
    }
    public static String getMethod(String methodName, String input, String input2){
        String response = "UNKNOWN METHOD";
        if (methodName.equals("fibonacci")){
            response = fibonacci(input);
        }
        if (methodName.equals("min")){
            response = min(input, input2);
        }
        if (methodName.equals("max")){
            response = max(input, input2);
        }
        if (methodName.equals("abs")){
            response = abs(input);
        }
        if (methodName.equals("iseven")){
            response = iseven(input);
        }
        if (methodName.equals("wholenumbers")){
            response = wholenumbers(input);
        }
        if (methodName.equals("multitable")){
            response = multitable(input, input2);
        }
        if (methodName.equals("algorithm")){
            response = algorithm(input, input2);
        }
        if (methodName.equals("readfile")){
            response = readfile(input);
        }
        if (methodName.equals("randomgame")){
            response = randomgame(input, input2);
        }
        if (methodName.equals("morsecode")){
            response = morsecode(input);
        }


        return response;
    }
    public static String min(String a, String b) {
        if (Integer.parseInt(a) <= Integer.parseInt(b)) {
            return "Väiksem arv on " + a;
        } else return "Väiksem arv on " + b;
    }
    public static String max(String a, String b) {
        if (Integer.parseInt(a) >= Integer.parseInt(b)) {
            return "Suurem arv on " + a;
        } else return "Suurem arv on " + b;
    }
    public static String abs(String a) {
        int A = Integer.parseInt(a);
        if(A < 0){
            return "Absoluutarv arvust " + a + " on " + -A;
        }
        return "Absoluutarv arvust " + a + " on " + A;
        //return Math.abs(a);
    }
    public static String iseven(String a) {
        // tagasta false kui a on paaritu arv
        return Integer.parseInt(a) % 2 == 0 ? a.concat(" on paarisarv") : a.concat(" on paaritu arv");
    }
    public static String fibonacci(String n) {
        // Fibonacci jada on fib(n) = fib(n-1) + fib(n-2);
        // 0, 1, 1, 2, 3, 5, 8, 13, 21
        // Tagasta fibonacci jada n element
        int N = Integer.parseInt(n);

        int[] Fib = new int[N];
        Fib[0] = 0;
        Fib[1] = 1;
        for(int i = 2; i < N; i++){

            Fib[i] = Fib[i-1] + Fib[i-2];
            //System.out.println(Fib[i]);
        }
        return "Fibonacci jada " + n + ". arv on " + Fib[N-1];
    }
    public static String wholenumbers(String x) {
        // TODO prindi välja x esimest paaris arvu
        int count = 0;
        int number = 1;
        String response = "";
        List<Integer> resultList = new ArrayList<>();
        for(int i = 1; i <= Integer.parseInt(x); i++){
            resultList.add(i*2);
        }
        /*
        while(count <= Integer.parseInt(x)){
            if(number % 2 == 0){
                response = response.concat(number + "-");
                count++;
            }
            number++;
        }*/
        return Arrays.toString(resultList.toArray());
    }
    public static String multitable(String x, String y) {
        // TODO trüki välja korrutustabel mis on x ühikut lai ja y ühikut kõrge
        // TODO näiteks x = 3 y = 3
        // TODO väljund
        int X = Integer.parseInt(x);
        int Y = Integer.parseInt(y);
        int[][] grid = new int[X][Y];
        String response = "";
        for(int i = 0; i < X; i++){
            //String col = "";
            for(int j = 0; j < grid[i].length; j++){
                grid[i][j] = (i+1) * (j+1);
                //col += grid[i][j] + " ";
                response = response.concat(Integer.toString(grid[i][j]));
            }
            //System.out.println(col);
            response = response.concat("</br>");
        }
        // 1 2 3
        // 2 4 6
        // 3 6 9
        return response;
    }
    public static String algorithm(String i, String j) {
        int num1 = Integer.parseInt(i);
        int num2 = Integer.parseInt(j);
        int maxCycle = 0;
        int smaller = num1 <= num2 ? num1 : num2;
        int bigger = smaller == num1 ? num2 : num1;
        for (int loop = smaller; loop <= bigger; loop++){
            int number = loop;
            int numOfCycles = 1;
            while(number > 1){
                numOfCycles++;
                if(number % 2 == 1){
                    number = 3*number + 1;
                } else {
                    number /= 2;

                }
                if(numOfCycles > maxCycle){
                    maxCycle = numOfCycles;
                }
            }

        }
        return "smallest(" + smaller + ") biggest(" + bigger + ") maxCycle(" + maxCycle+ ")";
    }
    public static String readfile(String fileName) {
        /*
        Failis nums.txt on üksteise all 150 60-kohalist numbrit.
        Kirjuta programm, mis loeks antud numbrid failist sisse ja liidaks need arvud kokku ning kuvaks ekraanil summa.
        Faili nimi tuleb programmile ette anda käsurea parameetrina.
        VASTUS:
        Õige summa: 77378062799264987173249634924670947389130820063105651135266574
         */
        String path = "C:\\Users\\Ott Kask\\Desktop\\demo\\" + fileName + ".txt";

        fileName = fileName.concat(".txt");
        System.out.println(path);
        try {
            File file = new File(path);
            Scanner fileScanner = new Scanner(file);
            if(file.exists()){

                BigDecimal sum = new BigDecimal("0");
                while (fileScanner.hasNextLine()) {
                    //System.out.println(fileScanner.nextLine());
                    BigDecimal num = new BigDecimal(fileScanner.nextLine());
                    sum = sum.add(num);
                }
                System.out.println("====== SUM ======");
                System.out.println(sum);
                return sum.toString();
            } else {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e){
            return "File not found";
        }
    }
    public static String randomgame(String guess, String answer){
        // TODO kirjuta mäng mis võtab suvalise numbri 0-100, mille kasutaja peab ära arvama
        // iga kord pärast kasutaja sisestatud täis arvu peab programm ütlema kas number oli suurem või väiksem
        // ja kasutaja peab saama uuesti arvata
        // numbri ära aramise korral peab programm välja trükkima mitu katset läks numbri ära arvamiseks
        //Random random = new Random();
        //int i = random.nextInt(100);
        //int attempts = 0;
        //System.out.println("Palun paku number!");
        //Scanner scanner = new Scanner(System.in);
        //int guess = scanner.nextInt();
        String response = "";

        int Guess = Integer.parseInt(guess);
        int Answer = Integer.parseInt(answer);
            if(Guess > Answer){
                if (Guess > 100){
                    response = "Palun paku number vahemikus 0-100";
                } else {
                    response = "Liiga suur";
                }

            } else if (Guess < Answer) {
                if (Guess < 0){
                    response = "Palun paku number vahemikus 0-100";
                } else {
                    response = "Liiga väike";
                }

            } else {
                response = "success";
            }

        return response;
    }
    public static String morsecode(String text){
        // TODO kirjuta programm, mis tagastab sisestatud teksti morse koodis (https://en.wikipedia.org/wiki/Morse_code)
        // Kasuta sümboleid . ja -
        String[] morse = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--..", ".----", "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.", "-----"};
        String alphaB = "abcdefghijklmnopqrstuvwxyz1234567890";
        String invalid = ",.-öäüõ<> -*/!#¤%&/()=?`@£$€{[]}\"'";
        text = text.toLowerCase();
        System.out.println(alphaB.length());
        String response = "";
        for(int i = 0; i < text.length(); i++){
            if (!invalid.contains(String.valueOf(text.charAt(i)))){
                response.concat(morse[alphaB.indexOf(String.valueOf(text.charAt(i)))] + "|");
            }

        }
        return response;
    }

}
