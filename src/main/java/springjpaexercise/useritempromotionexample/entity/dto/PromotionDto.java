package springjpaexercise.useritempromotionexample.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import springjpaexercise.useritempromotionexample.entity.enumtype.DiscountType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class PromotionDto {
    @NotNull
    private Long itemId;
    @NotBlank
    private String promotionName;
    private Integer discountAmount;
    private Double discountRate;
    @NotBlank
    private String StartDate;
    @NotBlank
    private String endDate;
    public static class Builder {
        private Long itemId;
        private String promotionName;
        private Integer discountAmount;
        private Double discountRate;
        private String StartDate;
        private String endDate;
        public Builder itemId(Long itemId) {
            this.itemId = itemId;
            return this;
        }
        public Builder discountAmount(Integer discountAmount) {
            this.discountAmount = discountAmount;
            return this;
        }
        public Builder discountRate(Double discountRate) {
            this.discountRate = discountRate;
            return this;
        }
        public Builder promotionName(String promotionName) {
            this.promotionName = promotionName;
            return this;
        }
        public Builder startDate(String startDate) {
            StartDate = startDate;
            return this;
        }
        public Builder endDate(String endDate) {
            this.endDate = endDate;
            return this;
        }
        public PromotionDto build() {
            return new PromotionDto(this);
        }
    }
    public static Builder builder() {
        return new Builder();
    }
    private PromotionDto(Builder builder) {
        this.itemId = builder.itemId;
        this.promotionName = builder.promotionName;
        this.discountAmount = builder.discountAmount;
        this.discountRate = builder.discountRate;
        this.StartDate = builder.StartDate;
        this.endDate = builder.endDate;
    }
}
