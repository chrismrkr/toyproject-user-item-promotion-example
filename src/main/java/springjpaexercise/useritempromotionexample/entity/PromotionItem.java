package springjpaexercise.useritempromotionexample.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(PromotionItem.JoinTableId.class)
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PromotionItem {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    public PromotionItem(Promotion promotion, Item item) {
        this.promotion = promotion;
        this.item = item;
    }
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class JoinTableId implements Serializable {
        private Long item;
        private Long promotion;
        public static class Builder {
            private Long item;
            private Long promotion;
            public Builder itemPK(Long id) {
                this.item = id;
                return this;
            }
            public Builder promotionPK(Long id) {
                this.promotion = id;
                return this;
            }
            public JoinTableId build() {
                return new JoinTableId(this);
            }
        }
        private JoinTableId(Builder builder) {
            this.item = builder.item;
            this.promotion = builder.promotion;
        }
        public static Builder builder() {
            return new Builder();
        }
    }
}
