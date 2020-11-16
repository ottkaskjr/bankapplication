package ee.bcs.valiit.service;


import ee.bcs.valiit.AccountRowMapper;
import ee.bcs.valiit.BankAccount;
import ee.bcs.valiit.Client;
import ee.bcs.valiit.Record;
import ee.bcs.valiit.ClientRowMapper;
import ee.bcs.valiit.HistoryRowMapper;
import ee.bcs.valiit.controller.RequestJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import ee.bcs.valiit.repository.AccountRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AccountService {

    /*
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;*/


    // TEGELIKULT SÜNTAKSI POOLEST KORREKTNE VIIS VÕRRELDES ÜLEVAL OLEVA @Autowired ANNOTATSIOONIGA

    public AccountService(AccountRepository accountRepository, NamedParameterJdbcTemplate jdbcTemplate) {
        this.accountRepository = accountRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    private AccountRepository accountRepository;
    private NamedParameterJdbcTemplate jdbcTemplate;

    // ===============================================================//
    // ============================ GET =============================//
    // ===============================================================//

    // GET ALL CLIENTS
    public Map<String, List<Client>> getClients() {
        List<Client> result = accountRepository.getAllClients();
        Map<String, List<Client>> allClients = new HashMap<>();
        allClients.put(result.size() + " bank clients", result);
        return allClients;
    }

    // GET CLIENT BY ID
    public Client getClientById(String clientID) {
        //List<Client> clients = accountRepository.getUserById(clientID);
        Client client = accountRepository.getUserById(clientID);
        if(!client.isEmpty()){
            List<BankAccount> bankAccounts = accountRepository.getAccountsByClientId(clientID);
            // GET ACCOUNTS HISTORIES

            for(int i = 0; i < bankAccounts.size(); i++){
                List<Record> history = accountRepository.getAccountHistory(bankAccounts.get(i).getId());
                bankAccounts.get(i).setHistory(history);
            }
            client.setBankAccounts(bankAccounts);
            return client;
        }


        return new Client("notFound", "", "");
    }

    // GET BANKACCOUNT BY ACCOUNTNR
    public BankAccount getAccountByNr(String accountNr) {
        BankAccount bankAccount = accountRepository.getAccountByAccountNr(accountNr);
        if(!bankAccount.isEmpty()){
            List<Record> history = accountRepository.getAccountHistory(bankAccount.getId());
            bankAccount.setHistory(history);
            return bankAccount;
        }

        return new BankAccount(new BigDecimal("0"), "notFound", new BigDecimal("0"), "", "");
    }

    // GET ALL BANKACCOUNTS
    public Map<String, List<BankAccount>> getAccounts() {
        List<BankAccount> result = accountRepository.getAllAccounts();
        Map<String, List<BankAccount>> allAccounts = new HashMap<>();
        allAccounts.put(result.size() + " bank accounts", result);
        return allAccounts;
    }


    // GET ACCOUNT BALANCE
    public String getAccountBalanceService(String accountNr){
        BankAccount bankAccount = accountRepository.getAccountBalanceRepo(accountNr);
        String balance = bankAccount.getBalance();
        return balance;
    }



    // ===============================================================//
    // ============================ POST =============================//
    // ===============================================================//
    // POST BANCCLIENT
    public String createClient(String clientFirstName, String clientLastName) {
        return accountRepository.createClient(clientFirstName, clientLastName);
    }

    // POST BANKCACCOUNT
    public String createAccount(String clientID) {

        return accountRepository.createAccount(clientID);
    }


    // ===============================================================//
    // ============================ PUT =============================//
    // ===============================================================//

    // DEPOSIT TO ACCOUNT
    public String depositAccount(RequestJSON operation, String accountNr) {

        RequestJSON Operation = new RequestJSON(operation.money);
        BigDecimal Money = new BigDecimal(Operation.money);

        // GET ACCOUNT
        BankAccount bankAccount = accountRepository.getAccountByAccountNr(accountNr);
        if (bankAccount.isEmpty()) {
            return "Account " + accountNr + " not found";
        }
        //BankAccount bankAccount = accountList.get(0);


        String currentBalance = bankAccount.getBalance();
        String newBalance = new BigDecimal(currentBalance).add(Money).toString();

        // UPDATE BANKACCOUNT
        if(!accountRepository.updateAccount(accountNr, "balance", newBalance).equals("200")){
            return "Updating client failed";
        }

        // ADD HISTORY
        String transaction = "+" + Money.toString();
        if(!accountRepository.createHistory(transaction, bankAccount.getId()).equals("200")){
            return "Creating history failed";
        }

        return "DEPOSITED " + Money.toString() + " to " + accountNr + ". New balance: " + newBalance;
        //return depositMoney(clientID, accountNr, Money);
    }

    // WITHDRAW TO ACCOUNT
    public String withdrawAccount(RequestJSON operation, String accountNr) {
        RequestJSON Operation = new RequestJSON(operation.money);
        BigDecimal Money = new BigDecimal(Operation.money);

        // GET ACCOUNT

        BankAccount bankAccount = accountRepository.getAccountByAccountNr(accountNr);

        if (bankAccount.isEmpty()) {
            return "Account " + accountNr + " not found";

        }


        String currentBalance = bankAccount.getBalance();


        BigDecimal newBalance = new BigDecimal(currentBalance).subtract(Money);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            return "Insufficient funds. Bank account only has " + currentBalance;
        }
        String newBalanceVerified = newBalance.toString();

        // UPDATE BANKACCOUNT
        if(!accountRepository.updateAccount(accountNr, "balance", newBalanceVerified).equals("200")){
            return "Updating client failed";
        }

        // ADD HISTORY
        String transaction = "-" + Money.toString();
        if(!accountRepository.createHistory(transaction, bankAccount.getId()).equals("200")){
            return "Creating history failed";
        }

        //withdrawMoney(String clientID, String accountNr, BigDecimal money)
        return "Withdrawed " + Money.toString() + " from " + accountNr + ". New balance: " + newBalanceVerified;
    }



    // TRANSFER
    public String transferAccount(RequestJSON operation, String fromAccount, String toAccount) {

        RequestJSON Operation = new RequestJSON(operation.money);
        BigDecimal Money = new BigDecimal(Operation.money);

        // GET SENDER ACCOUNT
        BankAccount senderAccount = accountRepository.getAccountByAccountNr(fromAccount);

        if (senderAccount.isEmpty()) {
            return "Account " + fromAccount + " not found";
}


        BigDecimal senderNewBalance = new BigDecimal(senderAccount.getBalance()).subtract(Money);
        if (senderNewBalance.compareTo(BigDecimal.ZERO) < 0) {
            return "Insufficient funds. Bank account only has " + senderAccount.getBalance();
        }

        // UPDATE SENDER ACCOUNT

        String senderNewBalanceStr = senderNewBalance.toString();
        // UPDATE BANKACCOUNT
        if(!accountRepository.updateAccount(fromAccount, "balance", senderNewBalanceStr).equals("200")){
            return "Updating sender failed";
        }

        // GET RECIEVER ACCOUNT
        BankAccount recieverAccount = accountRepository.getAccountByAccountNr(toAccount);

        if (recieverAccount.isEmpty()) {
            return "Account " + toAccount + " not found";

        }

        BigDecimal recieverNewBalance = new BigDecimal(recieverAccount.getBalance()).add(Money);


        // UPDATE RECIEVER ACCOUNT
        String newBalanceToStr = recieverNewBalance.toString();

        // UPDATE BANKACCOUNT
        if(!accountRepository.updateAccount(toAccount, "balance", newBalanceToStr).equals("200")){
            return "Updating reciever failed";
        }

        // ADD SENDER HISTORY
        String transaction = "-" + Money.toString() + " to: " + toAccount;
        if(!accountRepository.createHistory(transaction, senderAccount.getId()).equals("200")){
            return "Creating history for sender account failed";
        }

        // ADD RECIEVER HISTORY
        String transactionReciever = "+" + Money.toString() + " from: " + fromAccount;
        if(!accountRepository.createHistory(transactionReciever, recieverAccount.getId()).equals("200")){
            return "Creating history for sender account failed";
        }

        return "Transferred " + Money.toString() + " from " + fromAccount + " to " + toAccount;
    }


    // ===============================================================//
    // ============================ DELETE =============================//
    // ===============================================================//
    // DELETE BANKACCOUNT
    public String deleteAccount(String accountNr) {
       if(accountRepository.deleteAccount(accountNr) == 1){
           return "Account " + accountNr + " deleted.";
       }
       return "Deleting account " + accountNr + " failed";

    }

    // DELETE BANKCLIENT
    public String deleteClient(String clientID) {
        if(accountRepository.deleteClient(clientID) == 1){
            return "Account " + clientID + " deleted.";
        }
        return "Client " + clientID + " deleted.";
    }

    // ===============================================================//
    // ============================ METHODS =============================//
    // ===============================================================//

    public String getTime() {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(new Date());
        return date;
    }

    // generate account id
    public String generateID() {

        /*
        long generatedLong = new Random().nextLong();
        // if accountnr is negative number
        if(generatedLong < 0){
            generatedLong *= -1;
        }*/
        double random = Math.random() * 1000000.0;
        int randomInt = (int) random;
        return Integer.toString(randomInt);
    }


    // generate account nr
    public String generateAccountNr() {
        long generatedLong = new Random().nextLong();
        // if accountnr is negative number
        if (generatedLong < 0) {
            generatedLong *= -1;
        }
        return "AC" + Long.toString(generatedLong);
    }
}


