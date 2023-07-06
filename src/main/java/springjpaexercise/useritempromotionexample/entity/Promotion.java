package springjpaexercise.useritempromotionexample.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springjpaexercise.useritempromotionexample.entity.embeddable.DiscountPolicy;
import springjpaexercise.useritempromotionexample.entity.embeddable.StartEndDate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = {"id"})
public class Promotion {
    @Id @Column(name = "promotion_id")
    @GeneratedValue
    private Long id;
    private String promotionName;
    @Embedded
    private DiscountPolicy discountPolicy;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="startDate", column = @Column(name="promotion_start_date")),
            @AttributeOverride(name="endDate", column = @Column(name="promotion_end_date"))
    })
    private StartEndDate promotionDate;

    @OneToMany(mappedBy = "promotion", cascade = CascadeType.REMOVE)
    private Set<PromotionItem> promotionItems = new HashSet<>();

    public static class Builder {
        private String promotionName;
        private DiscountPolicy discountPolicy;
        private StartEndDate promotionDate;

        public Builder promotionName(String promotionName) {
            this.promotionName = promotionName;
            return this;
        }
        public Builder discountPolicy(DiscountPolicy discountPolicy) {
            this.discountPolicy = discountPolicy;
            return this;
        }
        public Builder promotionDate(StartEndDate promotionDate) {
            this.promotionDate = promotionDate;
            return this;
        }

        public Promotion build() {
            return new Promotion(this);
        }
    }
    public static Builder builder() {
        return new Builder();
    }
    public Promotion(Builder builder) {
        this.promotionName = builder.promotionName;
        this.discountPolicy = builder.discountPolicy;
        this.promotionDate = builder.promotionDate;
    }
}
