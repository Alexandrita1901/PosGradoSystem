package FCA.Sistema.Web.Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)   
    public ResponseEntity<String> handleRuntimeException(IllegalArgumentException ex) {
        return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
                
    }
    @ExceptionHandler(RuntimeException.class)   
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
    	return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_GATEWAY);
            
}

 }
