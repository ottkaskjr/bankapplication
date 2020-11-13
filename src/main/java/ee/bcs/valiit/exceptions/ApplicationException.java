package ee.bcs.valiit.exceptions;

public class ApplicationException extends RuntimeException{
    public ApplicationException(String message){
        super(message);
    }
}
