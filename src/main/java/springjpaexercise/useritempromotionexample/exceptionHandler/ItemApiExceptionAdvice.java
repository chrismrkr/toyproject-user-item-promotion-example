package springjpaexercise.useritempromotionexample.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import springjpaexercise.useritempromotionexample.controller.ItemController;
import springjpaexercise.useritempromotionexample.exceptionHandler.exception.ItemApiExceptionResult;

import java.util.List;

@RestControllerAdvice(assignableTypes = {ItemController.class})
public class ItemApiExceptionAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ItemApiExceptionResult methodArgNotValidException(MethodArgumentNotValidException e) {
        ItemApiExceptionResult exceptionResult = new ItemApiExceptionResult("INVALID_ARGUMENT");
        e.getFieldErrors().stream().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            exceptionResult.getMessages().put(fieldName, message);
        });
        List<ObjectError> globalErrors = e.getGlobalErrors();
        return exceptionResult;
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ItemApiExceptionResult illegalArgsException(IllegalArgumentException e) {
        ItemApiExceptionResult exceptionResult = new ItemApiExceptionResult("INVALID_ARGUMENT");
        exceptionResult.getMessages().put("exception", e.getMessage());
        return exceptionResult;
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IllegalStateException.class)
    public ItemApiExceptionResult illegalStateException(IllegalArgumentException e) {
        ItemApiExceptionResult exceptionResult = new ItemApiExceptionResult("SERVER_ERROR");
        exceptionResult.getMessages().put("exception", e.getMessage());
        return exceptionResult;
    }
}
