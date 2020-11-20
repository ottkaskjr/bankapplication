package ee.bcs.valiit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/*
You have to mark every endpoint (class that recieves requests) with RestController annotation
Functions in RestController class will return results in json format
 */
@RequestMapping("test")// defines first part of path paneb kõikide URL'ide ette "test" stringi
@RestController// @restController on märge, millega anname Springile teada, et selle klassiga me haldame/võtame vastu päringuid
public class TestController {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    int randomNr = 59;
    private int attempts = 0;
    private List<Employee> employees = new ArrayList<>();
    // @ - algav on annotatsioon, millel puudub funktsionaalsus, see on lihtsalt märge
    // @Get - get request(@PostMapping, @PutMapping, @DeleteMapping
    // user symbol * to mark all

    /*
    // Special Symbols
    ?   One character("hello or aello")
    *   Zero or more characters("/*" matches "/abc" but not "/abc/def"
    **  Zero or more directories in path "/**" Matches everyting

     */

    /*
    Path Parameter
    You can add path variable into path definition, by encapsulating it in bracers
    @GetMapping("/employe/{id}")
    public Long test(@PathVariable("id") Long id){
        return id;
    }
     */
    /*
    Query
    @RequestParam
    /employee?employeeId=5&testId=4
     */
    @CrossOrigin
    @GetMapping("register")
    public String testVue(String email){
        System.out.println(email);
        return "OK";
    }
    @CrossOrigin
    @PostMapping("vuepost")
    public List<User> testVuePost(@RequestBody User user){
        User newUser = new User(user.getName(), user.getEmail(), user.getAge());
        System.out.println(newUser.toString());
        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(new User("john", "smith", 35));
        userList.add(new User("sam", "smith", 32));
        userList.add(new User("ann", "smith", 26));
        return userList;
    }

    @GetMapping(value = "/")
    public String test(){
        return "Hello World";
    }
    // "/" pole ette vaja
    @GetMapping(value = "Hello")
    public String test2(){
        return "Midagi muud";
    }

    @GetMapping("/hello/{midagi}")
    public String test3(@PathVariable("midagi") String mingiTekst,
                        @RequestParam("employeeId") Long employeeId,
                        @RequestParam(value = "testId", required = false, defaultValue = "0") Long optional){
        return mingiTekst + " :) " + employeeId + " " + optional;
    }

    //MINU HTML TEST
    @GetMapping("/testpage")
    public String testpage(){
        return "";
    }

    //////////////



    @GetMapping("/createemployee")
    public Employee getEmployee(@RequestParam("firstname") String firstName,
                                @RequestParam("lastname") String lastName,
                                @RequestParam("age") int age,
                                @RequestParam("id") String id,
                                @RequestParam("employed") boolean employed){
        Employee e = new Employee(firstName, lastName, age, id, employed);
        //e.setFirstName(firstName);
        //e.setLastName(lastName);
        return e;
    }
    // GET ALL EMPLOYEES
    @GetMapping("/employees")
    public List<Employee> getEmployees(){
        //Employee a = new Employee("Tom", "Pom", 26, "12345", true);
        //Employee b = new Employee("Jim", "Bim", 34, "54321", true);
        //Employee c = new Employee("Ann", "Wann", 28, "33333", true);
        //<Employee> employees = new ArrayList<>();
        //e.setFirstName(firstName);
        //e.setLastName(lastName);
        return employees;
    }
    // GET EMPLOYEE BY ID
    @GetMapping("/employees/{id}")
    public Employee getEmployee(@PathVariable("id") String id){
        //Employee a = new Employee("Tom", "Pom", 26, "12345", true);
        //Employee b = new Employee("Jim", "Bim", 34, "54321", true);
        //Employee c = new Employee("Ann", "Wann", 28, "33333", true);
        //<Employee> employees = new ArrayList<>();
        //e.setFirstName(firstName);
        //e.setLastName(lastName);

        /*employees.stream().filter(employee -> employee.getId().equals(id));*/

        for(int i = 0; i < employees.size(); i++){
            if(employees.get(i).getId().equals(id)){
                return employees.get(i);
            }
        }

        return new Employee("", "", 0, "notFound", false);
    }
    // POST EMPLOYEE
    @PostMapping("/employee")
    public Employee postEmployee(@RequestBody Employee e){
        //Employee e = new Employee(firstName, lastName, age, id, employed);
        //e.setFirstName(firstName);
        //e.setLastName(lastName);
        //return request;
        //System.out.println(request);
        //Employee newEmployee = new Employee("Juhan", "Viiding", 150, "246426142614", false);
        employees.add(e);
        return e;
    }
    // PUT EMPLOYEE
    @PutMapping("/employee/{id}")
    public Employee putEmployee(@RequestBody Employee e,
                                @PathVariable("id") String id){
        //Employee e = new Employee(firstName, lastName, age, id, employed);
        //e.setFirstName(firstName);
        //e.setLastName(lastName);
        //return request;
        //System.out.println(request);
        //Employee newEmployee = new Employee("Juhan", "Viiding", 150, "246426142614", false);
        for(int i = 0; i < employees.size(); i++){
            if(employees.get(i).getId().equals(id)){
                employees.set(i, e);
                return e;
            }
        }
        return new Employee("", "", 0, "notFound", false);
    }
    // DELETE EMPLOYEE
    @DeleteMapping("/employee/{id}")
    public String deleteEmployee(@PathVariable("id") String id){
        //Employee e = new Employee(firstName, lastName, age, id, employed);
        //e.setFirstName(firstName);
        //e.setLastName(lastName);
        //return request;
        //System.out.println(request);
        //Employee newEmployee = new Employee("Juhan", "Viiding", 150, "246426142614", false);
        String response = "Kasutajat " + id + " ei leitud";
        for(int i = 0; i < employees.size(); i++){
            if(employees.get(i).getId().equals(id)){
                response = employees.get(i).getFirstName() + " " + employees.get(i).getLastName() + "(" + employees.get(i).getId() + ") on töötajate nimekirjast eemaldatud.";
                employees.remove(i);
                return response;
            }
        }
        return response;
    }


    /* HARJUTUS */
    @GetMapping("/program/{method}")
    public String program(@PathVariable("method") String method,
                          @RequestParam("num") String input,
                          @RequestParam(value = "num2", required = false, defaultValue = "") String input2){

        String responseJSON = "UNKNOWN METHOD";
        Programs program = new Programs();
        String programList = program.listMethods();
        if(programList.contains(method.toLowerCase())){
            if (method.toLowerCase().equals("randomgame")) {
                responseJSON = program.getMethod(method.toLowerCase(), input, Integer.toString(randomNr));
                if (responseJSON.equals("success")){
                    responseJSON = "Palju õnne! Sul kulus " + attempts + " katset!";
                } else {
                    attempts++;
                }
            } else {
                System.out.println("FOUND METHOD");
                responseJSON = program.getMethod(method.toLowerCase(), input, input2);
            }

        }


        return responseJSON;
    }

    @GetMapping("guess")
    public String guess(@RequestParam("nr") int nr){
        if(true){
            return "arvasid ära";
        } else if(true){
            return "arv on liiga väike";
        }
        else {
            return "arv on liiga suur";
        }
    }
    @GetMapping("table")
    public Object table(int x, int y){
        List <List<Integer>> result = new ArrayList<>();
        String stringResult = "";
        for(int i = 1; i <= x; i++){
            List<Integer> sisemineList = new ArrayList<>();
            for(int j = 1; j <= y; j++){
                sisemineList.add(i*y);
                stringResult += i*y + "</br>";
            }
        }
        return stringResult;
    }


    @GetMapping("/a/*/a/{a}/{b}/c")
    public void testing(@PathVariable("a") String a,
                        @PathVariable("b") Integer b,
                        @RequestParam("a") String aa,
                        @RequestParam("b") Integer bb)
    {
        //http://localhost:8080/test/a/path/a/tekst/12345/c?a=txt&b=123
        System.out.println("test");
    }



    //@GetMapping("/a/*/a/{a}/{b}/c")
    /*
    public void ex4(@PathVariable("a") String a,
                    @RequestParam("a") String aa,
                    @RequestParam("b") Integer bb,
                    @PathVariable("b") Integer b){

        System.out.println("test");
    }*/

    // HARJUTUSED:
    //localhost:8080/users/8/contracts/8?filterBy=status
    @GetMapping("/users/{nr2}/contracts/{nr}")
    public String aaa(@PathVariable("nr") int nr,
                      @PathVariable("nr2") int nr2,
                      @RequestParam("filterBy") String v1){
        return "";
    }

    /*
    /company/5/employee/8/contract/5
    /?employeeId=8&somethingElse=tere
    /company/6?company=5&a=a&b=b
    */

    //company/5/employee/8/contract/5

    /*
    @GetMapping("/company/{companyID}/employee/{employeeID}/contract/{contractID}")
    public String ex1(@PathVariable("companyID") String companyID,
                      @PathVariable("employeeID") String employeeID,
                      @PathVariable("contractID") String contractID){
        return "";
    }
    //?employeeId=8&somethingElse=tere
    @GetMapping("/")
    public String ex2(@RequestParam("employeeId") String employeeId,
                      @RequestParam("somethingElse") String somethingElse){
        return "";
    }
    //company/6?company=5&a=a&b=b
    @GetMapping("/company/{companyName}")
    public String ex3(@PathVariable("companyName") String companyName,
                      @RequestParam("company") String company,
                      @RequestParam(value = "a", required = false) String a,
                      @RequestParam(value = "b", required = false) String b){
        return "";
    }*/



    ///// ================= POST =======================
    @PostMapping("/employee1")
    public List<Employee> postEmployee1(@RequestBody Employee e){

        //Employee e = new Employee(firstName, lastName, age, id, employed);
        //e.setFirstName(firstName);
        //e.setLastName(lastName);
        //return request;
        //System.out.println(request);
        Employee newEmployee = new Employee("Juhan", "Viiding", 150, "246426142614", false);
        List<Employee> employees = new ArrayList<>();
        employees.add(e);
        employees.add(newEmployee);
        return employees;
    }

}
