package springjpaexercise.useritempromotionexample.entity.embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class DiscountPolicy {
    private int discountAmount;
    private double discountRate;

    public static class Builder {
        private int discountAmount;
        private double discountRate;
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
        this.discountRate = builder.discountRate;
    }
}
