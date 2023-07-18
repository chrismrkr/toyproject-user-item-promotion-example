package springjpaexercise.useritempromotionexample.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import springjpaexercise.useritempromotionexample.controller.UserController;
import springjpaexercise.useritempromotionexample.exceptionHandler.exception.UserApiExceptionResult;

@RestControllerAdvice(assignableTypes = {UserController.class})
public class UserApiExceptionAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public UserApiExceptionResult methodArgNotValidException(MethodArgumentNotValidException e) {
        UserApiExceptionResult exceptionResult = new UserApiExceptionResult("INVALID_ARGUMENT");
        e.getFieldErrors().stream().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            exceptionResult.getMessages().put(fieldName, message);
        });
        return exceptionResult;
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public UserApiExceptionResult illegalArgsException(IllegalArgumentException e) {
        UserApiExceptionResult exceptionResult = new UserApiExceptionResult("INVALID_ARGUMENT");
        exceptionResult.getMessages().put("exception", e.getMessage());
        return exceptionResult;
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IllegalStateException.class)
    public UserApiExceptionResult illegalStateException(IllegalArgumentException e) {
        UserApiExceptionResult exceptionResult = new UserApiExceptionResult("SERVER_ERROR");
        exceptionResult.getMessages().put("exception", e.getMessage());
        return exceptionResult;
    }
}