package springjpaexercise.useritempromotionexample.entity.dto;

import lombok.Data;
import springjpaexercise.useritempromotionexample.entity.enumtype.UserStat;
import springjpaexercise.useritempromotionexample.entity.enumtype.UserType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class UserDto {
    private String username;
    private String userType;
    private String userStat;
}
