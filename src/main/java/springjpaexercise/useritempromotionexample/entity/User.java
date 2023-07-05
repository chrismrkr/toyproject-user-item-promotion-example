package springjpaexercise.useritempromotionexample.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springjpaexercise.useritempromotionexample.entity.enumtype.UserStat;
import springjpaexercise.useritempromotionexample.entity.enumtype.UserType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "Users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {
    @Id @Column(name = "user_id")
    @GeneratedValue
    private Long id;

    private String username;
    @Enumerated(value = EnumType.STRING)
    private UserType userType;
    @Enumerated(value = EnumType.STRING)
    private UserStat userStat;

    public static class Builder {
        private String username;
        private UserType userType;
        private UserStat userStat;
        public Builder username(String username) {
            this.username = username;
            return this;
        }
        public Builder userType(UserType userType) {
            this.userType = userType;
            return this;
        }
        public Builder userStat(UserStat userStat) {
            this.userStat = userStat;
            return this;
        }
        public User build() {
            return new User(this);
        }
    }
    public static Builder builder() {
        return new Builder();
    }
    private User(Builder builder) {
        this.username = builder.username;
        this.userStat = builder.userStat;
        this.userType = builder.userType;
    }
}
