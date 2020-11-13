package ee.bcs.valiit;

public class Employee {
    private String firstName;
    private String lastName;
    private int age;
    private String id;
    private boolean employed;

    // KUI KONSTRUKTORIT POLE, ON DEFAULT KONSTRUKTOR AUTOMAATNE
    //public Employee() {}

    public Employee(String firstName, String lastName, int age, String id, boolean employed) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.id = id;
        this.employed = employed;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isEmployed() {
        return employed;
    }

    public void setEmployed(boolean employed) {
        this.employed = employed;
    }
}
