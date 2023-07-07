package springjpaexercise.useritempromotionexample.entity.dto;

import lombok.Data;

@Data
public class ResponseDto {
    private String status;
    private Object body;
}
