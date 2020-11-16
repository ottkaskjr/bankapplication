package ee.bcs.valiit.lessons;

import java.math.BigDecimal;

public class Lesson4 {
    // TODO loo programm, mis hoiab mälus panga konto numbrit ja konto balanssi
    // TEE programm:
    //

    public static void main(String[] args) {
        BigDecimal a = new BigDecimal("10");
        BigDecimal b = BigDecimal.ONE;

        if(a.compareTo(b) < 0){
            System.out.println("jep");
        } else {
            System.out.println("ei ole");
        }
    }

    private static BigDecimal account;
    private static BigDecimal balance;
    private static String owner;
    // Counstructor
    Lesson4(String account, String balance, String owner){

        this.account = new BigDecimal(account);
        this.balance = new BigDecimal(balance);
        this.owner = owner;
    }

    // getters
    public String getAccount(){
        return this.account.toString();
    }
    public String getBalance(){
        return this.balance.toString();
    }
    public String getOwner(){
        return this.owner;
    }

    // setters
    public String changeBalance(String account, String change){
        String response = "Account numbers don't match";
        if (compareAccounts(account)){
            BigDecimal Change = new BigDecimal(change);
            this.balance = this.balance.add(Change);
            response = "Transaction complete";
        }
        return response;
    }

    // functions
    public boolean compareAccounts(String account){
        BigDecimal comparable = new BigDecimal(account);
        //https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html
        // LOE LÄBI!!!
        // ALUMINE SÜNTAKS KUNA TEGELT NÕUTAKSE inti compareTo puhul, siis saab lisada antud meetodi lõppu == 0, mis tähistab,
        // et need on võrdsed, teise juhul tuleb lisada > 0 võo < 0, siis ei ole võrdsed
        return this.account.compareTo(comparable) == 0;


    }
}
