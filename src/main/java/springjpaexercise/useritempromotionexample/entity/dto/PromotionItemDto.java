package springjpaexercise.useritempromotionexample.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class PromotionItemDto {
    private String itemName;
    private String itemType;
    private String startDate;
    private String endDate;
    private int originalPrice;
    private Integer discountPrice;
    public void setDiscountPrice(List<Integer> discountPriceList) {
        discountPriceList.sort((o1, o2) -> o1 < o2 ? -1 : 1);
        for(Integer discountPrice : discountPriceList) {
            if(discountPrice > 0) {
                this.discountPrice = discountPrice;
                return;
            }
        }
        throw new IllegalArgumentException("PROMOTION NOT EXIST");
    }
    public static class Builder {
        private String itemName;
        private String itemType;
        private int originalPrice;
        private String startDate;
        private String endDate;
        public Builder itemName(String itemName) {
            this.itemName = itemName;
            return this;
        }
        public Builder itemType(String itemType) {
            this.itemType = itemType;
            return this;
        }
        public Builder originalItemPrice(int price) {
            this.originalPrice = price;
            return this;
        }
        public Builder startDate(String startDate) {
            this.startDate = startDate;
            return this;
        }
        public Builder endDate(String endDate) {
            this.endDate = endDate;
            return this;
        }
        public PromotionItemDto build() {
            return new PromotionItemDto(this);
        }
    }
    public static Builder builder() {
        return new Builder();
    }
    private PromotionItemDto(Builder builder) {
        this.itemName = builder.itemName;
        this.itemType = builder.itemType;
        this.originalPrice = builder.originalPrice;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
    }
}
