package springjpaexercise.useritempromotionexample.entity.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import springjpaexercise.useritempromotionexample.entity.enumtype.UserStat;
import springjpaexercise.useritempromotionexample.entity.enumtype.UserType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class UserDto {
    @NotBlank(message = "회원명을 입력해주세요.")
    @Pattern(regexp = "^[a-z]{1}[a-z0-9]{5,10}+$"
            , message = "영문으로 시작하고, 소문자 영문 숫자 조합 6-11자리만 허용합니다.")
    private String username;

    @NotBlank(message = "회원종류를 선택해주세요.")
    private String userType;
    private String userStat;
    public static class Builder {
        private String username;
        private String userType;
        private String userStat;
        public Builder username(String username) {
            this.username = username;
            return this;
        }
        public Builder userType(String userType) {
            this.userType = userType;
            return this;
        }
        public Builder userStat(String userStat) {
            this.userStat = userStat;
            return this;
        }
        public UserDto build() {
            return new UserDto(this);
        }
    }
    public static Builder builder() {
        return new Builder();
    }
    private UserDto(Builder builder) {
        this.username = builder.username;
        this.userType = builder.userType;
        this.userStat = builder.userStat;
    }
}
