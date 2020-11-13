package ee.bcs.valiit;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Date;

public class BankAccount {
    private BigDecimal id;
    private String accountNr;
    private BigDecimal bankClientId;
    private String registered;
    private String balance;
    //public List<Record> records = new ArrayList<>();
    private List<Record> history = new ArrayList<>();
    public BankAccount(){

    }

    public BankAccount(BigDecimal id, String accountNr, BigDecimal bankClientId, String registered, String balance) {
        this.id = id;
        this.accountNr = accountNr;
        this.bankClientId = bankClientId;
        this.registered = registered;
        this.balance = balance;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getAccountNr() {
        return accountNr;
    }

    public void setAccountNr(String accountNr) {
        this.accountNr = accountNr;
    }

    public BigDecimal getBankClientId() {
        return bankClientId;
    }

    public void setBankClientId(BigDecimal bankClientId) {
        this.bankClientId = bankClientId;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public List<Record> getHistory() {
        return history;
    }

    public void setHistory(List<Record> history) {
        this.history = history;
    }

    public boolean isEmpty(){
        return this.id == null;
    }

    // withdraw
    /*
    public boolean withdraw(BigDecimal money){
        if (balance.subtract(money).compareTo(new BigDecimal("0")) == 0 || balance.subtract(money).compareTo(new BigDecimal("0")) == 1){
            balance = balance.subtract(money);
            return true;
        }
        return false;

    }*/



}
