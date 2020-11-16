package ee.bcs.valiit.lessons;

import java.util.Random;
import java.util.Scanner;

// Enne kui seda tegema hakkad tee ära Lesson 2 (välja arvatud ülesanded 6, 8, 9)
public class Lesson3Hard {
    public static void main(String[] args) {

    }

    public static int evenFibonacci(int x){
        // TODO liida kokku kõik paaris fibonacci arvud kuni numbrini x
        // Fibonacci jada on fib(n) = fib(n-1) + fib(n-2);
        // 0, 1, 1, 2, 3, 5, 8, 13, 21
        // Tagasta fibonacci jada n element
        int[] Fib = new int[x];
        Fib[0] = 0;
        Fib[1] = 1;
        int evenFibonacciSum = 0;
        //int totalTest = 1;
        for(int i = 2; i < x; i++){

            Fib[i] = Fib[i-1] + Fib[i-2];
            if(Fib[i] % 2 == 0){
                evenFibonacciSum += Fib[i];

            }
            //System.out.println(Fib[i]);
            //totalTest += Fib[i];
        }
        //System.out.println(totalTest);
        return evenFibonacciSum;
    }

    public static void randomGame(){
        // TODO kirjuta mäng mis võtab suvalise numbri 0-100, mille kasutaja peab ära arvama
        // iga kord pärast kasutaja sisestatud täis arvu peab programm ütlema kas number oli suurem või väiksem
        // ja kasutaja peab saama uuesti arvata
        // numbri ära aramise korral peab programm välja trükkima mitu katset läks numbri ära arvamiseks
        Random random = new Random();
        int i = random.nextInt(100);
        int attempts = 0;
        System.out.println("Palun paku number!");
        Scanner scanner = new Scanner(System.in);
        int guess = scanner.nextInt();

        while(guess != i){

            if(guess > i){
                if (guess > 100){
                    System.out.println("Palun paku number vahemikus 0-100");
                } else {
                    System.out.println("Liiga suur");
                }

            } else if (guess < i) {
                if (guess < 0){
                    System.out.println("Palun paku number vahemikus 0-100");
                } else {
                    System.out.println("Liiga väike");
                }

            }
            attempts++;
            System.out.println("Palun paku uus number!");
            int newGuess = scanner.nextInt();
            guess = newGuess;
        }


        System.out.println("=" + i + "=");
        System.out.println("Palju õnne, sul kulus " + attempts + " kateset.");

    }

    public static void test(){
        int a = 4;
        int b = 8;

        a = a + b;
        b = a - b;
        a = a - b;

        System.out.println(a + "/" + b);
    }

    public static String morseCode(String text){
        // TODO kirjuta programm, mis tagastab sisestatud teksti morse koodis (https://en.wikipedia.org/wiki/Morse_code)
        // Kasuta sümboleid . ja -
        String[] morse = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--..", ".----", "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.", "-----"};
        String alphaB = "abcdefghijklmnopqrstuvwxyz1234567890";
        String invalid = ",.-öäüõ<> -*/!#¤%&/()=?`@£$€{[]}\"'";
        text = text.toLowerCase();
        System.out.println(alphaB.length());
        String result = "";
        for(int i = 0; i < text.length(); i++){
            if (!invalid.contains(String.valueOf(text.charAt(i)))){
                result.concat(morse[alphaB.indexOf(String.valueOf(text.charAt(i)))] + "|");
            }

        }
        return result;
    }
}
