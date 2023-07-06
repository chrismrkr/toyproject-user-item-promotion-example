package springjpaexercise.useritempromotionexample.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springjpaexercise.useritempromotionexample.entity.embeddable.StartEndDate;
import springjpaexercise.useritempromotionexample.entity.enumtype.ItemType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = {"id"})
public class Item {
    @Id @Column(name = "item_id")
    @GeneratedValue
    private Long id;
    private String itemName;
    @Enumerated(value = EnumType.STRING)
    private ItemType itemType;
    private int itemPrice;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="startDate", column = @Column(name="item_display_start_date")),
            @AttributeOverride(name="endDate", column = @Column(name="item_display_end_date"))
    })
    private StartEndDate itemDisplayDate;

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE)
    private Set<PromotionItem> promotionItems = new HashSet<>();

    public static class Builder {
        private String itemName;
        private ItemType itemType;
        private int itemPrice;
        private StartEndDate itemDisplayDate;
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
        public Builder itemDisplayDate(StartEndDate itemDisplayDate) {
            this.itemDisplayDate = itemDisplayDate;
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
        this.itemDisplayDate = builder.itemDisplayDate;
    }
}