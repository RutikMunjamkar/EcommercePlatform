package in.org.project.EcommercePlatform.exception;

import org.springframework.http.ResponseEntity;
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
}