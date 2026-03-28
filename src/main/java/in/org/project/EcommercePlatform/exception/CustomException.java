package in.org.project.EcommercePlatform.exception;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CustomException extends RuntimeException{

    private LocalDateTime localDateTime;
    private String message;
    private int httpCode;

    public CustomException(){
        this.localDateTime=LocalDateTime.now();
    }

    public CustomException(String message, int httpCode){
        this();
        this.message=message;
        this.httpCode=httpCode;
    }
}
