package in.org.project.EcommercePlatform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String,Object>> handleCustomException(CustomException customException){
        Map<String,Object>response=new HashMap<>();
        int httpStatusCode= customException.getHttpCode();
        response.put("httpStatusCode",httpStatusCode);
        response.put("message",customException.getMessage());
        response.put("time",customException.getLocalDateTime());
        return ResponseEntity.status(httpStatusCode).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String,Object>> handleAccessDeniedException(AccessDeniedException accessDeniedException){
        Map<String,Object>response=new HashMap<>();
        response.put("httpStatusCode",HttpStatus.FORBIDDEN.value());
        response.put("message",accessDeniedException.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
}