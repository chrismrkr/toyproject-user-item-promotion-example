package springjpaexercise.useritempromotionexample.entity.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import springjpaexercise.useritempromotionexample.entity.embeddable.StartEndDate;
import springjpaexercise.useritempromotionexample.entity.enumtype.ItemType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ItemDto {
    @NotBlank(message = "상품명을 입력해주세요.")
    private String itemName;
    @NotBlank(message = "상품 종류를 지정해주세요")
    private String itemType;
    @NotBlank(message = "상품 가격을 입력해주세요.")
    private Integer itemPrice;
    @NotBlank(message = "시작일을 설정해주세요.")
    private String startDate;
    @NotBlank(message = "종료일을 설정해주세요.")
    private String endDate;
    public static class Builder {
        private String itemName;
        private String itemType;
        private Integer itemPrice;
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

        public Builder itemPrice(Integer itemPrice) {
            this.itemPrice = itemPrice;
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
    }
    public static Builder builder() {
        return new Builder();
    }
    private ItemDto(Builder builder) {
        this.itemName = builder.itemName;
        this.itemType = builder.itemType;
        this.itemPrice = builder.itemPrice;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
    }
}
