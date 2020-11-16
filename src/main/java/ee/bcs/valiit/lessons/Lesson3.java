package ee.bcs.valiit.lessons;

import java.util.*;

public class Lesson3 {

    public static void main(String[] args) {
        /*
        List<Integer> testList = new ArrayList<>();
        testList.add(4);
        testList.add(5);
        testList.add(6);
        Arrays.toString(new int[0]);
        for (Integer i : testList) {
            System.out.println(i);
        }

        System.out.print(testList);

        System.out.println(reverseString("Test1 Test2"));
        Map<String, String> testMap = new HashMap<>();
        test(testMap);
        Object a;

         */
    }

    public static void test(Map<String, String> testMap) {
        testMap.put("test", "test");
    }

    public static int uusSumma(int x, int y) {
        // TODO liida kokku ja tagasta x ja y väärtus
        return x+y;
    }

    public static int sum(int[] x){
        // Todo liida kokku kõik numbrid massivis x
        /*
        System.out.println("Tere");
        Scanner scanner = new Scanner(System.in);
        scanner.hasNext();
        String a = "1";
        int b = Integer.parseInt(a);
        */
        int sum = 0;
        for(int num : x){
            sum += num;
        }
        return sum;
    }

    public static int factorial(int x) {
        // TODO tagasta x faktoriaal.
        // Näiteks
        // x = 5
        // return 5*4*3*2*1 = 24
        int sum = x;
        for(int i = x-1; i >=1; i--){
            sum *= i;
        }
        return sum;
    }

    public static int[] sort(int[] a) {
        // TODO sorteeri massiiv suuruse järgi
        // Näiteks {2, 6, 8, 1}
        // Väljund {1, 2, 6, 8}
        // ÕIGEM ON LOOPIDA ÜHTE ARRAY'D:
        // [1, 10, 7, 8, 5]
        // 1  [10, 7, 8, 5]
        // 1 5 [10, 7, 8] jne
        // ehk ülejäänud arrayst


        int thisIndex = 0;

        for(int i = 0; i < a.length; i++){
            int smallest = a[i];
            for(int j = i; j < a.length; j++){
                if(a[j] <= smallest){
                    smallest = a[j];
                    thisIndex = j;
                }
            }

            a[thisIndex] = a[i];
            a[i] = smallest;
        }
        return a;

    }
    public static String reverseString(String a) {
        // TODO tagasta string tagurpidi
        // Näiteks:
        // a = "Test";
        // return tseT";
        /*
        System.out.println(a.substring(0, 1));
        System.out.println(a.substring(1, 2));
        System.out.println(a.substring(2, 3));
        System.out.println(a.substring(3, 4));
        System.out.println(a.substring(2, 4));
        System.out.println(a.substring(2));*/
        /*
        String[] array = a.split("e");
        System.out.println(array[0]);
        System.out.println(array[1]);

        System.out.println(a.indexOf(" "));*/

        String result = "";
        for(int i = a.length()-1; i >= 0 ; i--){
            result += String.valueOf(a.charAt(i));
        }

        return result;
    }

    public static boolean isPrime(int x){
        // TODO tagasta kas sisestatud arv on primaar arv (jagub ainult 1 ja iseendaga)
        return x % 2 == 0;
    }

}
