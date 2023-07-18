package springjpaexercise.useritempromotionexample.exceptionHandler.exception;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class UserApiExceptionResult {
    private String code;
    private Map<String, String> messages;
    public UserApiExceptionResult(String code) {
        this.code = code;
        this.messages = new HashMap<>();
    }
}
