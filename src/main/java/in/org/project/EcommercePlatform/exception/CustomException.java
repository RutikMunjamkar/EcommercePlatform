package in.org.project.EcommercePlatform.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
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
        this.message=message;
        this.httpCode=httpCode;
    }
}
