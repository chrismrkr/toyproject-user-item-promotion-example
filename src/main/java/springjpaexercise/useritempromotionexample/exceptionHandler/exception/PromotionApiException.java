package springjpaexercise.useritempromotionexample.exceptionHandler.exception;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class PromotionApiException {
    private String code;
    private Map<String, String> messages;
    public PromotionApiException(String code) {
        this.code = code;
        this.messages = new HashMap<>();
    }
}
