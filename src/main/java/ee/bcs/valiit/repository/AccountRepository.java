package ee.bcs.valiit.repository;


import ee.bcs.valiit.AccountRowMapper;
import ee.bcs.valiit.Record;
import ee.bcs.valiit.BankAccount;
import ee.bcs.valiit.Client;
import ee.bcs.valiit.ClientRowMapper;
import ee.bcs.valiit.HistoryRowMapper;
import ee.bcs.valiit.controller.RequestJSON;
import ee.bcs.valiit.exceptions.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class AccountRepository {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    // ===============================================================//
    // ======================== GET  =========================//
    // ===============================================================//
    // GET CLIENT BY ID
    public Client getUserById(String clientID){

        String sql = "SELECT * FROM bankclient WHERE id = :id";
        Map paramMap = new HashMap();
        paramMap.put("id", new BigDecimal(clientID));
        // check if user by given id exists
        List<Client> clients = jdbcTemplate.query(sql, paramMap, new ClientRowMapper());

        if(clients.size() == 0){
            throw new ApplicationException("User not found!");
        }

        Client result = jdbcTemplate.queryForObject(sql, paramMap, new ClientRowMapper());
        return result;
    }

    // GET ALL CLIENTS
    public List<Client> getAllClients(){
        String sql = "SELECT * FROM bankclient";
        Map paramMap = new HashMap();
        List<Client> result = jdbcTemplate.query(sql, paramMap, new ClientRowMapper());

        return result;
    }

    // GET ACCOUNT BY ACCOUNTNR
    public BankAccount getAccountByAccountNr(String accountNr){

        String sql = "SELECT * FROM bankaccount WHERE accountnr = :accountnr";
        Map paramMap = new HashMap();
        paramMap.put("accountnr", accountNr);

        // check if account by given number exists
        List<BankAccount> accounts = jdbcTemplate.query(sql, paramMap, new AccountRowMapper());

        if(accounts.size() == 0){
            throw new ApplicationException("Account not found!");
        }

        BankAccount result = jdbcTemplate.queryForObject(sql, paramMap, new AccountRowMapper());
        return result;
    }

    // GET ALL BANKACCOUNTS
    public List<BankAccount> getAllAccounts(){
        String sql = "SELECT * FROM bankaccount";
        Map paramMap = new HashMap();
        List<BankAccount> result = jdbcTemplate.query(sql, paramMap, new AccountRowMapper());
        return result;
    }

    // GET ALL ACCOUNTS BY BANKCLIENT_ID
    public List<BankAccount> getAccountsByClientId(String clientID) {

        String sql = "SELECT * FROM bankaccount WHERE bankclient_id = :bankclient_id";
        Map paramMap = new HashMap();
        paramMap.put("bankclient_id", new BigDecimal(clientID));
        List<BankAccount> result = jdbcTemplate.query(sql, paramMap, new AccountRowMapper());
        return result;
    }

    // GET ALL HISTORY BY BANKACCOUNT_ID
    public List<Record> getAccountHistory(BigDecimal bankAccountID){
        String sql = "SELECT * FROM history WHERE bankaccount_id = :bankaccount_id";
        Map paramMap = new HashMap();
        paramMap.put("bankaccount_id", bankAccountID);
        List<Record> result = jdbcTemplate.query(sql, paramMap, new HistoryRowMapper());
        return result;
    }

    // ===============================================================//
    // ============================ POST =============================//
    // ===============================================================//

    // CREATE BANK CLIENT
    public String createClient(String clientFirstName, String clientLastName){

        String sql = "INSERT INTO bankclient (firstname, lastname, registered, age) VALUES (:firstname, :lastname, :registered, :age)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("firstname", clientFirstName);
        paramMap.put("lastname", clientLastName);
        paramMap.put("registered", getTime());
        //test
        paramMap.put("age", generateAge());
        //===
        jdbcTemplate.update(sql, paramMap);

        String response = clientFirstName + " " + clientLastName + "  added to the database";
        return response;
    }
    // CREATE BANKACCOUNT
    public String createAccount(String clientID){
        BigDecimal id = new BigDecimal(generateID());
        Long bankclient_id = Long.parseLong(clientID);
        String accountnr = generateAccountNr();
        String registered = getTime();
        //BigDecimal balance = new BigDecimal("0");
        String balance = "0";

        // CHECK IF USER EXISTS
        Client client = getUserById(Long.toString(bankclient_id));

        // CREATE BANK ACCOUNT
        String createAccount = "INSERT INTO bankaccount (id, accountnr, bankclient_id, registered, balance) VALUES (:id, :accountnr, :bankclient_id, :registered, :balance)";
        Map<String, Object> paramMapAccount = new HashMap<>();
        paramMapAccount.put("id", id);
        paramMapAccount.put("accountnr", accountnr);
        paramMapAccount.put("bankclient_id", bankclient_id);
        paramMapAccount.put("registered", registered);
        paramMapAccount.put("balance", balance);
        jdbcTemplate.update(createAccount, paramMapAccount);

        String response = accountnr + " created";
        return response;
    }

    // ADD NEW HISTORY
    public String createHistory(String transaction, BigDecimal accountID){
        String createHistory = "INSERT INTO history (id, date, transaction, bankaccount_id) VALUES (:id, :date, :transaction, :bankaccount_id)";

        Map<String, Object> paramHistoryMap = new HashMap<>();
        paramHistoryMap.put("id", Long.parseLong(generateID()));
        paramHistoryMap.put("date", getTime());
        paramHistoryMap.put("transaction", transaction);
        paramHistoryMap.put("bankaccount_id", accountID);
        jdbcTemplate.update(createHistory, paramHistoryMap);
        return "200";
    }

    // ===============================================================//
    // ============================ PUT =============================//
    // ===============================================================//
    // UPDATE BANK ACCOUNT
    public String updateAccount(String accountNr, String fieldName, String fieldVal){
        String updateBankAccount = "UPDATE bankaccount SET " + fieldName + " = :" + fieldName + " WHERE accountnr = :accountnr";
        //UPDATE table_name SET column_name = ‘column_value’ WHERE id = 5;
        Map<String, Object> updateBankAccountMap = new HashMap<>();
        updateBankAccountMap.put(fieldName, fieldVal);
        updateBankAccountMap.put("accountnr", accountNr);
        jdbcTemplate.update(updateBankAccount, updateBankAccountMap);
        return "200";
    }

    // ===============================================================//
    // ============================ DELETE =============================//
    // ===============================================================//
    // DELETE HISTORY
    public int deleteHistory(BigDecimal bankaccount_id){
        String deleteHistory = "DELETE FROM history WHERE bankaccount_id = :bankaccount_id";
        Map<String, Object> deleteHistoryMap = new HashMap<>();
        deleteHistoryMap.put("bankaccount_id", bankaccount_id);
        return jdbcTemplate.update(deleteHistory, deleteHistoryMap);
    }
    // DELETE ACCOUNT
    public int deleteAccount(String accountNr){
        // FIRST GET AND CHECK IF ACCOUNT EXISTS
        BankAccount bankAccount = getAccountByAccountNr(accountNr);

        // DELETE ALL HISTORY RELATED TO THE GIVEN ACCOUNT
        deleteHistory(bankAccount.getId());

        String deleteAccount = "DELETE FROM bankaccount WHERE accountnr = :accountnr";
        Map<String, Object> deleteAccountMap = new HashMap<>();
        deleteAccountMap.put("accountnr", accountNr);
        return jdbcTemplate.update(deleteAccount, deleteAccountMap);
    }

    // DELETE CLIENT
    public int deleteClient(String clientID){
        // FIRST CHECK IF ACCOUNT EXISTS
        Client client = getUserById(clientID);

        // GET BANKACCOUNTS
        List<BankAccount> accounts = getAccountsByClientId(client.getId().toString());
        if(accounts.size() > 0){
            client.setBankAccounts(accounts);
        }
        // DELETE ALL ACCOUNTS RELATED TO THE CLIENT WHICH ALSO DELETES HISTORY OF THE ACCOUNTS
        System.out.println(client.getBankAccounts().size());
        for(BankAccount bankAccount: client.getBankAccounts()){
            deleteAccount(bankAccount.getAccountNr());
        }


        String deleteClient = "DELETE FROM bankclient WHERE id = :id";
        Map<String, Object> deleteClientMap = new HashMap<>();
        deleteClientMap.put("id", Long.parseLong(clientID));
        return jdbcTemplate.update(deleteClient, deleteClientMap);
    }

    // ===============================================================//
    // =========================== METHOD ============================//
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

    // generate age
    public int generateAge(){
        int randomAge = (int) (10 + (Math.random() * 100.0));

        return randomAge;
    }
}
