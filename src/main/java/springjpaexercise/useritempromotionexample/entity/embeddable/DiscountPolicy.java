package springjpaexercise.useritempromotionexample.entity.embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springjpaexercise.useritempromotionexample.entity.enumtype.DiscountType;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class DiscountPolicy {
    private DiscountType type;
    private Integer discountAmount;
    private Double discountRate;

    public static class Builder {
        private Integer discountAmount;
        private Double discountRate;
        public Builder discountAmount(int discountAmount) {
            this.discountAmount = discountAmount;
            return this;
        }
        public Builder discountRate(double discountRate) {
            this.discountRate = discountRate;
            return this;
        }
        public DiscountPolicy build() {
            return new DiscountPolicy(this);
        }
    }
    public static Builder builder() {
        return new Builder();
    }
    private DiscountPolicy(Builder builder) {
        this.discountAmount = builder.discountAmount;
        if(builder.discountAmount != null) {
            this.type = DiscountType.AMOUNT;
        }
        this.discountRate = builder.discountRate;
        if(builder.discountRate != null) {
            this.type = DiscountType.RATE;
        }
    }
}
