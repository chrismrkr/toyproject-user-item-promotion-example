package springjpaexercise.useritempromotionexample.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springjpaexercise.useritempromotionexample.entity.enumtype.ItemType;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Item {
    @Id @Column(name = "item_id")
    @GeneratedValue
    private Long id;
    private String itemName;
    @Enumerated(value = EnumType.STRING)
    private ItemType itemType;
    private int itemPrice;
    private LocalDate itemDisplayStartDate;
    private LocalDate itemDisplayEndDate;

    public static class Builder {
        private String itemName;
        private ItemType itemType;
        private int itemPrice;
        private LocalDate itemDisplayStartDate;
        private LocalDate itemDisplayEndDate;
        public Builder itemName(String itemName) {
            this.itemName = itemName;
            return this;
        }
        public Builder itemType(ItemType itemType) {
            this.itemType = itemType;
            return this;
        }
        public Builder itemPrice(int itemPrice) {
            this.itemPrice = itemPrice;
            return this;
        }
        public Builder itemDisplayStartDate(LocalDate itemDisplayStartDate) {
            this.itemDisplayStartDate = itemDisplayStartDate;
            return this;
        }
        public Builder itemDisplayEndDate(LocalDate itemDisplayEndDate) {
            this.itemDisplayEndDate = itemDisplayEndDate;
            return this;
        }
        public Item build() {
            return new Item(this);
        }
    }
    public static Builder builder() {
        return new Builder();
    }
    private Item(Builder builder) {
        this.itemName = builder.itemName;
        this.itemPrice = builder.itemPrice;
        this.itemType = builder.itemType;
        this.itemDisplayStartDate = builder.itemDisplayStartDate;
        this.itemDisplayEndDate = builder.itemDisplayEndDate;
    }
}
