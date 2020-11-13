package ee.bcs.valiit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private String firstName;
    private String lastName;
    private String registered;
    private BigDecimal id;
    private List<BankAccount> bankAccounts = new ArrayList<>();
    //public Map<String, BankAccount> accounts = new HashMap<>();

    public Client() {

    }

    public Client(String firstName, String lastName, String registered) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.registered = registered;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public boolean isEmpty(){
        return this.id == null;
    }

    /*public Map<String, BankAccount> getAccounts(){
        return this.accounts;
    }*/
    /*
    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }*/

    /*
    public String getAccountID() {
        return accountID;
    }*/


    /*
    public BankAccount getAccount(String accountNr){
        //BankAccount account = new BankAccount();

        for (BankAccount account: this.accounts){
            if (account.getAccountNr().equals(accountNr)){
                return account;
            }
        }
        System.out.println("TRYING TO RETURN ACCOUNT");
        return this.accounts.get(accountNr);
        //return new BankAccount("notFound", "", "", "");
    }*/

    /*
    public String addAccount(BankAccount newAccount){
        this.accounts.add(newAccount);
        return "Account " + newAccount.getAccountNr() + " created for " +  this.getFirstName();
    }*/


}
