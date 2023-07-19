package springjpaexercise.useritempromotionexample.exception;

import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class DisplayDateNotValidException extends IllegalArgumentException {
    public DisplayDateNotValidException() {
        super();
    }

    public DisplayDateNotValidException(String s) {
        super(s);
    }

    public DisplayDateNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public DisplayDateNotValidException(Throwable cause) {
        super(cause);
    }
}
