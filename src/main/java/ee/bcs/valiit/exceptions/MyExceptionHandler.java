package ee.bcs.valiit.exceptions;

import ee.bcs.valiit.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleApplicationException(Exception e){
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<Object> notFound(Exception e){

        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleError(Exception e){
        e.getStackTrace();
        //System.out.println("VIGA");
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
