package ee.bcs.valiit.controller;

import ee.bcs.valiit.BankAccount;
import ee.bcs.valiit.Client;
import ee.bcs.valiit.exceptions.ApplicationException;
import ee.bcs.valiit.repository2.Bankclient;
import ee.bcs.valiit.repository2.HibernateRepository;
import ee.bcs.valiit.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("bank")
@RestController
public class BankController {

    //HIBERNATE TEST
    @Autowired
    private HibernateRepository hibernateRepository;


    //Map<String, Client> clients = new HashMap<>();
    //Map<String, BankAccount> accounts = new HashMap<>();

    //Map<String, BankAccount> accounts = new HashMap<>();
    // NB SAAB PANNA KA MAPI MAPI SISSE
    @Autowired
    private AccountService accountService;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    /*
    MAPS JA HASHMAP
    Map<String, BigDecimal> accounts = new HashMap<>();
    accounts.put("EE123", BigDecimal.ZERO);
    accounts.put("EE124", BigDecimal.TEN);
    accounts.put("EE125", BigDecimal("23489949484");
    System.out.println(accounts.get("EE124");
     */
    // createAccount (accountNr)
    // depositMoney (accountNr, money)
    // withdrawMoney (accountNr, money)
    // getAccountBalance (accountNr, money)
    // transferMoney (fromAccount, toAccount, money)
    // getAccountBalance(accountNr)

    // Raskem
    // createClient(firstName lastName, ....)
    // muuta createAccount (clientId, accountNr)
    // getBalanceHistory (accountNr) kõik tehingud (boonus pane kuupäev ka

    // ===============================================================//
    // ============================ GET ==============================//
    // ===============================================================//

    // EXCEPTION TEST
    @GetMapping("/exception_test")
    public boolean exceptionTest(@RequestParam(value = "num", required = false) Integer num){

        return positiveNum(num);
    }

    //// HIBERNATE
    // GET ALL CLIENTS, CLIENT BY NAME OR CLIENT BY ID
    @GetMapping("/hibernate/clients")
    public List<Bankclient> getClients(@RequestParam(value = "firstname", required = false) String name,
                                      @RequestParam(value = "id", required = false) Long id){
        List<Bankclient> emptyList = new ArrayList<>();
        if(name == null && id == null){
            return hibernateRepository.findAll();
        } else if(id == null && name != null){
            return hibernateRepository.findAllByFirstnameIgnoreCase(name);
        } else {
            return hibernateRepository.findAllById(id);
        }
    }
    /*
    @GetMapping("/hibernate_test")
    public List<Bankclient> getAllClients(){
        return bankClientRepository.findAll();
    }*/
    /// =================== ////



    // =============================
    // =============================
    //  TEST
    @GetMapping("/testing")
    public String createTest(@RequestParam("param") String param){
        return "OK";
    }
    @GetMapping("/testingobject")
    public ResponseJSON getObject(){
        ResponseJSON response = new ResponseJSON("value");
        return response;
    }
    @PostMapping("/testing/post")
    public String postTest(@RequestBody RequestJSON body){
        RequestJSON request = body;
        if(request.money.equals("5000")){
            return "OK";
        }
        return "NOT";
    }
    @PutMapping("/testing/put/{id}")
    public String putTest(@PathVariable("id") String id){
        //return "OK";

        if(id.equals("123456")){
            return "OK";
        }
        return "NOT";
    }

    // =============================
    // =============================
    // =============================

    // GET BANKCLIENTS
    @CrossOrigin // et lubada teatud päringuid
    @GetMapping("/clients")
    public List<Client> getClients() {
        return accountService.getClients();
    }

    // GET BANKACCOUNTS
    @GetMapping("/accounts")
    //public Map<String, Client> getAccounts(){
    public Map<String, List<BankAccount>> getAccounts() {
        return accountService.getAccounts();
    }


    /// GET CLIENT BY ID
    @GetMapping("/clients/{clientID}")
    public Client getAccountById(@PathVariable(value = "clientID") String clientID) throws Exception {

        if(clientID == null) {
            throw new ApplicationException("Please insert valid bankclient id");
        }
        return accountService.getClientById(clientID);
    }

    // GET ACCOUNTS BY CLIENTID
    @GetMapping("/clients/{clientID}/accounts")
    public List<BankAccount> getAccountsByClientID(@PathVariable("clientID") String clientID) throws Exception {
        if(clientID == null) {
            throw new ApplicationException("Please insert valid bankclient id");
        }
        return accountService.getAccountsByClientID(clientID);
    }

    // GET ACCOUNT BY NUMBER
    @GetMapping("/accounts/{accountNr}")
    public BankAccount getAccounts(@PathVariable(value = "accountNr") String accountNr) {
        if(accountNr == null){
            throw new ApplicationException("Please insert valid bankclient id");
        }
        return accountService.getAccountByNr(accountNr);
        //accounts.forEach(account -> allAccounts.add(account));
        //BankAccount notFound = new BankAccount(new BigDecimal("-1"), "notFound", new BigDecimal("-1"), "-1", "-1");
        //return notFound;
    }

    // TEST ROUTE FOR GETTING ACCOUNT BALANCE
    @GetMapping("accounts/{accountNr}/balance")
    public String getAccountBalance(@PathVariable("accountNr") String accountNr){
        if(accountNr == null){
            throw new ApplicationException("Account not found or invalid or missing account number");
        }
        return accountService.getAccountBalanceService(accountNr);
    }


    // ===============================================================//
    // ============================ POST =============================//
    // ===============================================================//
    // POST CLIENT TÖÖTAB
    @PostMapping("/client")
    public String createClient(@RequestBody Client client) {
        return accountService.createClient(client.getFirstName(), client.getLastName());
    }

    // POST BANKACCOUNT TÖÖTAB
    @PostMapping("/client/{client_id}/accounts/add")
    public String createAccount(@PathVariable("client_id") String clientID) {
        return accountService.createAccount(clientID);
    }


    // ===============================================================//
    // ============================ PUT ==============================//
    // ===============================================================//

    // DEPOSIT TO ACCOUNT TÖÖTAB

    @PutMapping("/deposit/{accountNr}")
    public String depositAccount(@RequestBody RequestJSON operation,
                                 //@PathVariable("clientID") String clientID,
                                 @PathVariable("accountNr") String accountNr) {
        return accountService.depositAccount(operation, accountNr);
    }

    // WITHDRAW FROM ACCOUNT
    @PutMapping("/withdraw/{accountNr}")
    public String withdrawAccount(@RequestBody RequestJSON operation,
                                  @PathVariable("accountNr") String accountNr) {
        return accountService.withdrawAccount(operation, accountNr);
    }


    // TRANSFER TO ACCOUNT
    //@PutMapping("/{fromAccount}/transfer/{toAccount}")
    @PutMapping("/{fromAccount}/transfer/{toAccount}")
    public String transferAccount(@RequestBody RequestJSON operation,
                                  //@PathVariable("fromID") String fromID,
                                  //@PathVariable("toID") String toID,
                                  @PathVariable("fromAccount") String fromAccount,
                                  @PathVariable("toAccount") String toAccount) {

        return accountService.transferAccount(operation, fromAccount, toAccount);
    }
    // ===============================================================//
    // =========================== DELETE ============================//
    // ===============================================================//
    //QUERY
    //

    // TEE NEED ROUDID KA ÄRA
    // DELETE CLIENT
    @DeleteMapping("/delete/client/{clientID}")
    public String deleteClient(@PathVariable("clientID") String clientID) {

        return accountService.deleteClient(clientID);
    }

    // DELETE ACCOUNT
    @DeleteMapping("/delete/account/{accountNr}")
    public String deleteAccount(@PathVariable("accountNr") String accountNr) {
        return accountService.deleteAccount(accountNr);
    }



    // EXCEPTION TEST METHOD
    public boolean positiveNum(Integer i){
        if (i <= 0) {
            throw new ApplicationException("Number peab olema suurem kui 0");
        } else {
            return true;
        }

    }


    /*
    public String getAccountBalance(String clientID, String accountNr){
        BankAccount account = clients.get(clientID).getAccount(accountNr);
        if (account != null){
            return account.getBalance().toString();
        }
        return "Account not found";

    }*/
    /*
    public String depositMoney(String clientID, String accountNr, BigDecimal money){
        System.out.println(clientID);
        System.out.println(accountNr);

        BankAccount account = clients.get(clientID).accounts.get(accountNr);
        System.out.println(account);
        if (account != null){
            if (money.compareTo(new BigDecimal("0")) == 1) {
                account.deposit(money); //balance = balance.add(money);
                // PANE UPDATE RECORDS SIIA
                String transaction = "+" + money.toString();
                clients.get(clientID).accounts.get(accountNr).records.add(new Record(transaction, getTime()));
                return "Added " + money.toString() + " to " + account.getOwnerFullName() + "(account " + account.getAccountNr() + ") . New balance: " + account.getBalance();
            }
            return "Negative amount. Operation failed";
        }

        return "Account not found. Operation failed";

    }*/
    /*
    public String depositMoneyBetter(String accountNr, BigDecimal money){

        BankAccount account = accounts.get(accountNr);
        System.out.println(account);
        if (account != null){
            if (money.compareTo(new BigDecimal("0")) == 1) {
                account.deposit(money); //balance = balance.add(money);
                // PANE UPDATE RECORDS SIIA
                String transaction = "+" + money.toString();
                accounts.get(accountNr).records.add(new Record(transaction, getTime()));
                return "Added " + money.toString() + " to " + account.getOwnerFullName() + "(account " + account.getAccountNr() + ") . New balance: " + account.getBalance();
            }
            return "Negative amount. Operation failed";
        }

        return "Account not found. Operation failed";

    }*/

    /*
    public String withdrawMoney(String clientID, String accountNr, BigDecimal money){
        BankAccount account = clients.get(clientID).getAccount(accountNr);
        if (account != null) {
            if (money.compareTo(new BigDecimal("0")) == 1) {
                if(account.withdraw(money)){
                    String transaction = "-" + money.toString();
                    clients.get(clientID).accounts.get(accountNr).records.add(new Record(transaction, getTime()));
                    return "Withdrawed " + money.toString() + " from " + account.getOwnerFullName() + "(account " + account.getAccountNr() + ") . New balance: " + account.getBalance();
                };

                return "Insufficient funds";
            }
            return "Negative amount. Operation failed";
        }
        return "Account not found. Operation failed";
    }*/


    /*
    public String transferMoney(String fromClientId, String toClientId, String fromAccountNr, String toAccountNr, BigDecimal money){
        BankAccount fromAccount = clients.get(fromClientId).accounts.get(fromAccountNr);
        BankAccount toAccount = clients.get(toClientId).accounts.get(toAccountNr);

        if(fromAccount == null) {
            return "Sender account does not exist";
        }
        if(toAccount == null){
            return "Reciever account does not exist";
        }
        if (money.compareTo(new BigDecimal("0")) == -1) {
            return "Negative amount. Operation failed";
        }

        if(!fromAccount.withdraw(money)){
            return "Insufficient funds";
        }
        toAccount.deposit(money);
        String transaction1 = "-" + money.toString() + " To:" + toAccount.getOwnerFullName();
        String transaction2 = "+" + money.toString() + " From:" + fromAccount.getOwnerFullName();
        clients.get(fromClientId).accounts.get(fromAccountNr).records.add(new Record(transaction1, getTime()));
        clients.get(toClientId).accounts.get(toAccountNr).records.add(new Record(transaction2, getTime()));
        return "Transaction complete";

    }
    */
     /*
    public String transferMoneyBetter(String fromAccountNr, String toAccountNr, BigDecimal money){
        //BankAccount fromAccount = clients.get(fromClientId).accounts.get(fromAccountNr);
        //BankAccount toAccount = clients.get(toClientId).accounts.get(toAccountNr);
        BankAccount fromAccount = accounts.get(fromAccountNr);
        BankAccount toAccount = accounts.get(toAccountNr);

        if(fromAccount == null) {
            return "Sender account does not exist";
        }
        if(toAccount == null){
            return "Reciever account does not exist";
        }
        if (money.compareTo(new BigDecimal("0")) == -1) {
            return "Negative amount. Operation failed";
        }

        if(!fromAccount.withdraw(money)){
            return "Insufficient funds";
        }
        toAccount.deposit(money);
        String transaction1 = "-" + money.toString() + " To:" + toAccount.getOwnerFullName();
        String transaction2 = "+" + money.toString() + " From:" + fromAccount.getOwnerFullName();
        //clients.get(fromClientId).accounts.get(fromAccountNr).records.add(new Record(transaction1, getTime()));
        //clients.get(toClientId).accounts.get(toAccountNr).records.add(new Record(transaction2, getTime()));

        accounts.get(fromAccountNr).records.add(new Record(transaction1, getTime()));
        accounts.get(toAccountNr).records.add(new Record(transaction2, getTime()));
        return "Transaction complete";

    }*/

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

    /*
    public void updateRecords(String action, boolean transfer, String accountID, String name, String time){
        System.out.println(time);
        System.out.println(action);
        System.out.println(transfer);
        System.out.println(accountID);
        System.out.println(name);

        records.add(new Record(time, action, accountID, name));
    }*/

    /*
    public List<Record> getRecords(){
        return records;
    }
    */

    /*
    public String addAccount(BankAccount newAccount){
        this.accounts.add(newAccount);
        return "Account " + newAccount.getAccountNr() + " created for " +  this.getFirstName();
    }*/
    /*
    public void setAccountId(String id) {
        this.accountID = id;
    }*/

    public void getRecords() {

    }

}
// transferMoney (fromAccount, toAccount, money)
// getAccountBalance(accountNr)

