package springjpaexercise.useritempromotionexample.exceptionHandler.exception;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ItemApiExceptionResult {
    private String code;
    private Map<String, String> messages;
    public ItemApiExceptionResult(String code) {
        this.code = code;
        this.messages = new HashMap<>();
    }
}
