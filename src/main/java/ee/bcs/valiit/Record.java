package ee.bcs.valiit;

public class Record {

    private String date;
    private String transaction;

    public Record() {
    }

    public Record(String transaction, String time) {
        this.date = time;
        this.transaction = transaction;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getDate() {
        return date;
    }

    public String getTransaction() {
        return transaction;
    }
}
