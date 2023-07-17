package springjpaexercise.useritempromotionexample.unittest.entity.valid;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springjpaexercise.useritempromotionexample.entity.dto.UserDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class UserTest {
    @Autowired
    Validator validator;
    @Test
    @DisplayName("username이 \"\"인 경우")
    void blankUsername() {
        UserDto userDto = UserDto.builder().username("").userType("GENERAL").build();
        Set<ConstraintViolation<UserDto>> validate = validator.validate(userDto);

        Iterator<ConstraintViolation<UserDto>> iterator = validate.iterator();
        while(iterator.hasNext()) {
            ConstraintViolation<UserDto> next = iterator.next();
            log.info(next.getMessage());
            assertEquals("회원명을 입력해주세요.", next.getMessage());
        }
    }
    @Test
    @DisplayName("username이 null인 경우")
    void nullUsername() {
        UserDto userDto = UserDto.builder().userType("GENERAL").build();
        Set<ConstraintViolation<UserDto>> validate = validator.validate(userDto);
        Iterator<ConstraintViolation<UserDto>> iterator = validate.iterator();
        while(iterator.hasNext()) {
            ConstraintViolation<UserDto> next = iterator.next();
            log.info(next.getMessage());
            assertEquals("회원명을 입력해주세요.", next.getMessage());
        }
    }
    @Test
    @DisplayName("username이 \"  \"(비어 있는) 경우")
    void emptyUsername() {
        UserDto userDto = UserDto.builder().username("  ").userType("GENERAL").build();
        Set<ConstraintViolation<UserDto>> validate = validator.validate(userDto);
        Iterator<ConstraintViolation<UserDto>> iterator = validate.iterator();
        while(iterator.hasNext()) {
            ConstraintViolation<UserDto> next = iterator.next();
            log.info(next.getMessage());
            assertEquals("회원명을 입력해주세요.", next.getMessage());
        }
    }
    @Test
    @DisplayName("userType이 \"\"인 경우")
    void blankUserType() {
        UserDto userDto = UserDto.builder().username("user1").userType("").build();
        Set<ConstraintViolation<UserDto>> validate = validator.validate(userDto);

        Iterator<ConstraintViolation<UserDto>> iterator = validate.iterator();
        while(iterator.hasNext()) {
            ConstraintViolation<UserDto> next = iterator.next();
            log.info(next.getMessage());
            assertEquals("회원종류를 선택해주세요.", next.getMessage());
        }
    }
    @Test
    @DisplayName("userType이 null인 경우")
    void nullUserType() {
        UserDto userDto = UserDto.builder().username("user").build();
        Set<ConstraintViolation<UserDto>> validate = validator.validate(userDto);
        Iterator<ConstraintViolation<UserDto>> iterator = validate.iterator();
        while(iterator.hasNext()) {
            ConstraintViolation<UserDto> next = iterator.next();
            log.info(next.getMessage());
            assertEquals("회원종류를 선택해주세요.", next.getMessage());
        }
    }
    @Test
    @DisplayName("userType이 \"  \"(비어 있는) 경우")
    void emptyUserType() {
        UserDto userDto = UserDto.builder().username("user").userType("  ").build();
        Set<ConstraintViolation<UserDto>> validate = validator.validate(userDto);
        Iterator<ConstraintViolation<UserDto>> iterator = validate.iterator();
        while(iterator.hasNext()) {
            ConstraintViolation<UserDto> next = iterator.next();
            log.info(next.getMessage());
            assertEquals("회원종류를 선택해주세요.", next.getMessage());
        }
    }
}
