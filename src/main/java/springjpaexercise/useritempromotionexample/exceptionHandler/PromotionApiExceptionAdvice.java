package springjpaexercise.useritempromotionexample.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import springjpaexercise.useritempromotionexample.controller.PromotionController;
import springjpaexercise.useritempromotionexample.exceptionHandler.exception.PromotionApiException;
import springjpaexercise.useritempromotionexample.exceptionHandler.exception.UserApiExceptionResult;

@RestControllerAdvice(assignableTypes = {PromotionController.class})
public class PromotionApiExceptionAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public PromotionApiException methodArgNotValidException(MethodArgumentNotValidException e) {
        PromotionApiException exceptionResult = new PromotionApiException("INVALID_ARGUMENT");
        e.getFieldErrors().stream().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            exceptionResult.getMessages().put(fieldName, message);
        });
        return exceptionResult;
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public PromotionApiException illegalArgsException(IllegalArgumentException e) {
        PromotionApiException exceptionResult = new PromotionApiException("INVALID_ARGUMENT");
        exceptionResult.getMessages().put("exception", e.getMessage());
        return exceptionResult;
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IllegalStateException.class)
    public PromotionApiException illegalStateException(IllegalArgumentException e) {
        PromotionApiException exceptionResult = new PromotionApiException("SERVER_ERROR");
        exceptionResult.getMessages().put("exception", e.getMessage());
        return exceptionResult;
    }
}
