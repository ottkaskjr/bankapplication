package ee.bcs.valiit.lessons;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.io.File;

public class Lesson2 {

    /*
    public static void main(String[] args) {
        exercise7();
    }

     */

    public static void exercise1() throws InputMismatchException {
        // TODO loo 10 elemendile täisarvude massiv
        // TODO loe sisse konsoolist 10 täisarvu
        // TODO trüki arvud välja vastupidises järiekorras
        int[] whole = new int[10];
        Scanner scanner = new Scanner(System.in);
        for(int i = 0; i < 10; i++){
            try{
                int num = scanner.nextInt();
                whole[i] = num;
            } catch (InputMismatchException e){
                String num = scanner.nextLine();
                System.out.println("Sisesta number!");
                //whole[i] = ;
            }


        }
        for(int i = whole.length-1; i >= 0; i--){
            System.out.println(whole[i]);
        }

    }

    public static void exercise2(int x) {
        // TODO prindi välja x esimest paaris arvu
        int count = 0;
        int number = 1;
        while(count < x){
            if(number % 2 == 0){
                System.out.print(number);
                count++;
            }
            number++;
        }
        // Näide:
        // Sisend 5
        // Väljund 2 4 6 8 10
    }

    public static void exercise3(int x, int y) {
        // TODO trüki välja korrutustabel mis on x ühikut lai ja y ühikut kõrge
        // TODO näiteks x = 3 y = 3
        // TODO väljund
        int[][] grid = new int[x][y];
        for(int i = 0; i < x; i++){
            String col = "";
            for(int j = 0; j < grid[i].length; j++){
                grid[i][j] = (i+1) * (j+1);
                col += grid[i][j] + " ";
            }
            System.out.println(col);
        }
        // 1 2 3
        // 2 4 6
        // 3 6 9
    }

    public static int fibonacci(int n) {
        // TODO
        // Fibonacci jada on fib(n) = fib(n-1) + fib(n-2);
        // 0, 1, 1, 2, 3, 5, 8, 13, 21
        // Tagasta fibonacci jada n element
        int[] Fib = new int[n];
        Fib[0] = 0;
        Fib[1] = 1;
        for(int i = 2; i < n; i++){

            Fib[i] = Fib[i-1] + Fib[i-2];
            //System.out.println(Fib[i]);
        }

        return Fib[n-1];
    }

    public static void exercise5(int i, int j) {
        // https://onlinejudge.org/index.php?option=onlinejudge&Itemid=8&page=show_problem&problem=36
        /*
        Problems in Computer Science are often classified as belonging to a certain class of problems (e.g.,
        NP, Unsolvable, Recursive). In this problem you will be analyzing a property of an algorithm whose
        classification is not known for all possible inputs.
        Consider the following algorithm:
        1. input n
        2. print n
        3. if n = 1 then STOP
        4. if n is odd then n ←− 3n + 1
        5. else n ←− n/2
        6. GOTO 2
        Given the input 22, the following sequence of numbers will be printed
        22 11 34 17 52 26 13 40 20 10 5 16 8 4 2 1
        It is conjectured that the algorithm above will terminate (when a 1 is printed) for any integral input
        value. Despite the simplicity of the algorithm, it is unknown whether this conjecture is true. It has
        been verified, however, for all integers n such that 0 < n < 1, 000, 000 (and, in fact, for many more
        numbers than this.)
        Given an input n, it is possible to determine the number of numbers printed before and including
        the 1 is printed. For a given n this is called the cycle-length of n. In the example above, the cycle
        length of 22 is 16.
        For any two numbers i and j you are to determine the maximum cycle length over all numbers
        between and including both i and j.
        Input
        The input will consist of a series of pairs of integers i and j, one pair of integers per line. All integers
        will be less than 10,000 and greater than 0.
        You should process all pairs of integers and for each pair determine the maximum cycle length over
        all integers between and including i and j.
        You can assume that no operation overflows a 32-bit integer.
        Output
        For each pair of input integers i and j you should output i, j, and the maximum cycle length for
        integers between and including i and j. These three numbers should be separated by at least one space
        with all three numbers on one line and with one line of output for each line of input. The integers i
        and j must appear in the output in the same order in which they appeared in the input and should be
        followed by the maximum cycle length (on the same line).
        Sample Input
        1 10
        100 200
        201 210
        900 1000
        Sample Output
        1 10 20
        100 200 125
        201 210 89
        900 1000 174
         */
        /*
        1. input n
        2. print n
        3. if n = 1 then STOP
        4. if n is odd then n ←− 3n + 1
        5. else n ←− n/2
        6. GOTO 2
        Given the input 22, the following sequence of numbers will be printed
        22 11 34 17 52 26 13 40 20 10 5 16 8 4 2 1
         */
        int maxCycle = 0;
        int smaller = i <= j ? i : j;
        int bigger = smaller == i ? j : i;
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
        System.out.println("GOT HERE");
        System.out.println(smaller + " " + bigger + " " + maxCycle);
    }


    //public static void exercise6(String fileName)/* throws FileNotFoundException*/{
        //try {
            // PAREM VARIANT
            //String a = "2018-01-13, 434";
            //String[] b = a.split(" ");

            //List<Visit> visits = new ArrayList<>();
            /*
            visits.sort(new Comparator<Visit>() {
                @Override
                public int compare(Visit o1, Visit o2) {
                    return 0;
                }
            });*/
            /*
            File file = new File(fileName);
            Scanner fileScanner = new Scanner(file);
            List<Visit> visits = new ArrayList<>();
            if(file.exists()){
                while (fileScanner.hasNextLine()) {
                    //System.out.println(fileScanner.nextLine());
                    String line = fileScanner.nextLine();
                    String[] split = line.split(" ");

                    Visit visit = new Visit(split[0], Integer.parseInt(split[1]));
                    visits.add(visit);
                }


                visits.sort(new Comparator<Visit>() {
                    @Override
                    public int compare(Visit o1, Visit o2) {
                        // PEAB TAGASTMA NUMBRILISE VÄÄRTUSE (üldiselt -1, 0, 1)
                        return o1.getVisits() - o2.getVisits();
                        // Kui tulemus on positiivne siis liigutatakse esimene element jadas edasi(või vahetab teisega kohad?)
                    }
                });

                // UURI VEEL COMPARABLE interface kohta
                // if(b1.compareTo(b2) < 0){ System.out.println("true")};

                // test
                for(Visit line: visits){
                    System.out.println(line.getDate() + " " + line.getVisits());
                }
            } else {
                throw new FileNotFoundException();
            }*/

            /*
            NÄITED
                class Sortbyroll implements Comparator<Student>
                {
                    // Used for sorting in ascending order of
                    // roll number
                    public int compare(Student a, Student b)
                    {
                        return a.rollno - b.rollno;
                    }
                }

                class Sortbyname implements Comparator<Student>
                {
                    // Used for sorting in ascending order of
                    // roll name
                    public int compare(Student a, Student b)
                    {
                        return a.name.compareTo(b.name);
                    }
                }
             */
            //
            /*
            File file = new File(fileName);
            Scanner fileScanner = new Scanner(file);
            if(file.exists()){

                ArrayList<Integer> visits = new ArrayList<>();
                ArrayList<String> dates = new ArrayList<>();
                while (fileScanner.hasNextLine()) {
                    //System.out.println(fileScanner.nextLine());
                    String line = fileScanner.nextLine();
                    visits.add(Integer.parseInt(line.substring(12)));
                    dates.add(line);
                }
                Collections.sort(visits);

                System.out.println(visits.size());

                ArrayList<String> sortedList = new ArrayList<>();
                for(int num : visits){
                    for(String date : dates){
                        if (date.contains(Integer.toString(num))){
                            sortedList.add(date);
                        }
                    }
                }
                System.out.println("====== a ======");
                for(int i = 0; i < sortedList.size(); i++){
                    System.out.println(sortedList.get(i));
                }
                System.out.println("====== b ======");
                System.out.println("Suurim külastajte arv: " + visits.get(visits.size()-1));
            } else {
                throw new FileNotFoundException();
            }*/
            /*
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        }*/
        /*
            Kirjutada Java programm, mis loeb failist visits.txt sisse looduspargi külastajad erinevatel jaanuari päevadel ning
            a) sorteerib külastuspäevad külastajate arvu järgi kasvavalt ning prindib tulemuse konsoolile;
            b) prindib konsoolile päeva, mil külastajaid oli kõige rohkem.
            Faili asukoht tuleb programmile ette anda käsurea parameetrina.
         */

    //}

    public static void exercise7() {
        // TODO arvuta kasutades BigDecimali 1.89 * ((394486820340 / 15 ) - 4 )

        BigDecimal a = new BigDecimal("1.89");
        BigDecimal b = new BigDecimal("394486820345");
        BigDecimal c = new BigDecimal("15");
        BigDecimal d = new BigDecimal("4");



        // SHORT
        BigDecimal sum = b.divide(c, 4, RoundingMode.HALF_UP).subtract(d).multiply(a);
        System.out.println(sum);

        /*
        System.out.println(b.divide(c, 4, RoundingMode.HALF_UP));

        BigDecimal num = new BigDecimal("339598759387138751983751375135");
        BigDecimal num2 = new BigDecimal("339598752146416146144751375135");
        System.out.println(num.add(num2));*/
    }

    public static void exercise8(String fileName) {
        /*
        Failis nums.txt on üksteise all 150 60-kohalist numbrit.
        Kirjuta programm, mis loeks antud numbrid failist sisse ja liidaks need arvud kokku ning kuvaks ekraanil summa.
        Faili nimi tuleb programmile ette anda käsurea parameetrina.
        VASTUS:
        Õige summa: 77378062799264987173249634924670947389130820063105651135266574
         */
        try {
            File file = new File(fileName);
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
            } else {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        }
    }

    public static void exercise9(String fileName) {
        /* TODO
        Sama mis eelmises ülesandes aga ära kasuta BigInt ega BigDecimal klassi
        Õige summa: 77378062799264987173249634924670947389130820063105651135266574
         */
        try {
            File file = new File(fileName);
            Scanner fileScanner = new Scanner(file);
            if(file.exists()){

                //long sum = 0;
                ArrayList<String> numbers = new ArrayList<>();
                boolean firstBool = true;



                while (fileScanner.hasNextLine()) {
                    System.out.println("=================");
                    String num = fileScanner.nextLine();
                    String[] strings = num.split("");

                    if(firstBool) {
                        // FIRST LINE SCAN
                        //System.out.println("firstBool");
                        for (int j = 0; j < strings.length; j++) {
                            numbers.add(strings[j]);
                        }
                    } else {
                        // INCREMENT ALL INDEXES
                        for(int i = strings.length-1; i >=0 ; i--){
                            int Int = Integer.parseInt(strings[i]);

                            numbers.set(i, Integer.toString(Integer.parseInt(numbers.get(i)) + Int));
                        }


                        for(int i = strings.length-1; i >=0 ; i--){
                            // FIX INDEXES

                            if(Integer.parseInt(numbers.get(i)) >= 10){
                                int second = Integer.parseInt(numbers.get(i).substring(1));
                                int first = Integer.parseInt(String.valueOf(numbers.get(i).charAt(0)));
                                if (i > 0){
                                    numbers.set(i, Integer.toString(second));
                                    numbers.set(i-1, Integer.toString(Integer.parseInt(numbers.get(i-1)) + first));
                                }
                            }
                        }
                    }

                    firstBool = false;
                }
                System.out.println("====== SUM ======");
                String result = "";
                for(String num: numbers){
                    result += num;
                }
                System.out.println(result);
            } else {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        }
    }

}