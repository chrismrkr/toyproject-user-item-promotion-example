package springjpaexercise.useritempromotionexample.entity.dto;

import lombok.Builder;
import lombok.Data;
import springjpaexercise.useritempromotionexample.entity.enumtype.UserStat;
import springjpaexercise.useritempromotionexample.entity.enumtype.UserType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class UserDto {
    @NotEmpty(message = "회원명을 입력해주세요.")
    private String username;

    @NotEmpty(message = "회원종류를 선택해주세요.")
    private String userType;
    private String userStat;
}
