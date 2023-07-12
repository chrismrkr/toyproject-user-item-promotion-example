package springjpaexercise.useritempromotionexample.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springjpaexercise.useritempromotionexample.entity.Item;
import springjpaexercise.useritempromotionexample.entity.Promotion;
import springjpaexercise.useritempromotionexample.entity.PromotionItem;
import springjpaexercise.useritempromotionexample.entity.embeddable.DiscountPolicy;
import springjpaexercise.useritempromotionexample.entity.enumtype.DiscountType;
import springjpaexercise.useritempromotionexample.entity.vo.PromotionItemVo;
import springjpaexercise.useritempromotionexample.repository.PromotionItemRepository;
import springjpaexercise.useritempromotionexample.service.PromotionService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {
    private final PromotionItemRepository promotionItemRepository;

    @Override
    public PromotionItemVo findPromotionItem(Long itemId) {
        List<PromotionItem> promotionItemList = promotionItemRepository.findByItemId(itemId, LocalDate.now());
        if(promotionItemList.size() == 0) {
            throw new IllegalArgumentException("PROMOTION NOT EXIST");
        }
        Item item = promotionItemList.get(0).getItem();
        List<Integer> discountPriceList = promotionItemList.stream()
                .map(promotionItem -> {
                    DiscountPolicy discountPolicy = promotionItem.getPromotion().getDiscountPolicy();
                    if(discountPolicy.getType().equals(DiscountType.AMOUNT)) {
                        return item.getItemPrice() - discountPolicy.getDiscountAmount();
                    } else {
                        return (int)((double)(item.getItemPrice()) * (1.0 - discountPolicy.getDiscountRate()));
                    }
                })
                .collect(Collectors.toList());

        PromotionItemVo vo = PromotionItemVo.builder()
                .itemName(item.getItemName())
                .itemType(item.getItemType().toString())
                .originalItemPrice(item.getItemPrice())
                .startDate(item.getItemDisplayDate().getStartDate().toString())
                .endDate(item.getItemDisplayDate().getEndDate().toString())
                .build();
        vo.setDiscountPrice(discountPriceList);
        return vo;
    }
}
