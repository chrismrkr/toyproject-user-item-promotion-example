package springjpaexercise.useritempromotionexample.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import springjpaexercise.useritempromotionexample.entity.Item;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ItemListDto {
    private Long userId;
    private int itemCount;
    private List<ItemDto> itemList;
    public static class Builder {
        private Long userId;
        private int itemCount;
        private List<ItemDto> itemList;
        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }
        public Builder itemCount(int itemCount) {
            this.itemCount = itemCount;
            return this;
        }
        public Builder itemList(List<Item> itemList) {
            this.itemList = new ArrayList<>();
            itemList.stream().forEach(item -> {
                this.itemList.add(
                        ItemDto.builder()
                        .itemName(item.getItemName())
                        .itemPrice(item.getItemPrice())
                        .itemType(item.getItemType().toString())
                        .startDate(item.getItemDisplayDate().getStartDate().toString())
                        .endDate(item.getItemDisplayDate().getEndDate().toString())
                        .build()
                );
            });
            return this;
        }
        public ItemListDto build() {
            return new ItemListDto(this);
        }
    }
    public static Builder builder() {
        return new Builder();
    }
    public ItemListDto(Builder builder) {
        this.userId = builder.userId;
        this.itemCount = builder.itemCount;
        this.itemList = builder.itemList;
    }
}
