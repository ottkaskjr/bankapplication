package ee.bcs.valiit.lessons;

import java.util.Scanner;

public class Lesson1MathUtil {
    private String test;

    public Lesson1MathUtil(String test) {
        this.test = test;
    }


    public static void main(String[] args) {

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        Lesson1MathUtil lesson1MathUtil1 = new Lesson1MathUtil("test");
        Lesson1MathUtil lesson1MathUtil2 = new Lesson1MathUtil("test2");
        lesson1MathUtil1.test();
        lesson1MathUtil2.test();

        int[][] twoArray = new int[3][4];


    }

    public static void scan(){
        Scanner scanner = new Scanner(System.in);
        String functions = "min-max-abs-iseven";
        System.out.println("Sisesta funktsiooni nimi:");
        String funcName = scanner.nextLine();

        while(!functions.contains(funcName.toLowerCase())){
            System.out.println("Tundmatu funktsioon!");
            String newName = scanner.nextLine();
            funcName = newName;
        }

        if(funcName.toLowerCase().equals("min")){
            System.out.println("Sisesta esimene number");
            int num1 = scanner.nextInt();
            System.out.println("Sisesta teine number");
            int num2 = scanner.nextInt();
            System.out.println(min(num1, num2));
        } else if (funcName.toLowerCase().equals("max")){
            System.out.println("Sisesta esimene number");
            int num1 = scanner.nextInt();
            System.out.println("Sisesta teine number");
            int num2 = scanner.nextInt();
            System.out.println(max(num1, num2));
        } else if (funcName.toLowerCase().equals("abs")){
            System.out.println("Sisesta number");
            int num1 = scanner.nextInt();
            System.out.println(abs(num1));
        } else if (funcName.toLowerCase().equals("iseven")){
            System.out.println("Sisesta number");
            int num1 = scanner.nextInt();
            System.out.println(isEven(num1));
        }


    }

    public void test(){
        System.out.println(test);
    }

    public static int min(int a, int b) {
        // TODO tagasta a ja b väikseim väärtus
        if (a <= b) {
            return a;
        } else return b;
    }

    public static int max(int a, int b) {
        // TODO tagasta a ja b suurim väärtus
        if (a >= b) {
            return a;
        } else return b;
    }

    public static int abs(int a) {
        // TODO tagasta a absoluut arv
         if(a < 0){
         return -a;
        }
         return a;
        //return Math.abs(a);
    }

    public static boolean isEven(int a) {
        // TODO tagasta true, kui a on paaris arv
        // tagasta false kui a on paaritu arv
        return a % 2 == 0;
    }

    public static int min(int a, int b, int c) {
        // TODO tagasta a, b ja c väikseim väärtus
        int[] nums = {a, b, c};
        int smallest = a;
        for(int num: nums){
            if(num < smallest) smallest = num;
        }
        return smallest;
        // return min(min(a, b), c); sest funktsioon min on juba üleval defineeritud
    }

    public static int max(int a, int b, int c) {
        // TODO tagasta a, b ja c suurim väärtus

        int[] nums = {a, b, c};
        int biggest = a;
        for(int num: nums){
            if(num > biggest) biggest = num;
        }
        return biggest;
        // return max(min(a, b), c); sest funktsioon max on juba üleval defineeritud
    }

}
