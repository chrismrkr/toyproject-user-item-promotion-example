package springjpaexercise.useritempromotionexample.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springjpaexercise.useritempromotionexample.entity.Item;
import springjpaexercise.useritempromotionexample.entity.Promotion;
import springjpaexercise.useritempromotionexample.entity.PromotionItem;
import springjpaexercise.useritempromotionexample.entity.dto.PromotionDto;
import springjpaexercise.useritempromotionexample.entity.embeddable.DiscountPolicy;
import springjpaexercise.useritempromotionexample.entity.embeddable.StartEndDate;
import springjpaexercise.useritempromotionexample.entity.enumtype.DiscountType;
import springjpaexercise.useritempromotionexample.entity.dto.PromotionItemDto;
import springjpaexercise.useritempromotionexample.repository.ItemRepository;
import springjpaexercise.useritempromotionexample.repository.PromotionItemRepository;
import springjpaexercise.useritempromotionexample.repository.PromotionRepository;
import springjpaexercise.useritempromotionexample.service.PromotionService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {
    private final PromotionItemRepository promotionItemRepository;
    private final ItemRepository itemRepository;
    private final PromotionRepository promotionRepository;
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;


    @Override
    public PromotionItemDto findPromotionItem(Long itemId) {
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

        PromotionItemDto dto = PromotionItemDto.builder()
                .itemName(item.getItemName())
                .itemType(item.getItemType().toString())
                .originalItemPrice(item.getItemPrice())
                .startDate(item.getItemDisplayDate().getStartDate().toString())
                .endDate(item.getItemDisplayDate().getEndDate().toString())
                .build();
        dto.setDiscountPrice(discountPriceList);
        return dto;
    }

    @Override
    @Transactional
    public PromotionDto savePromotion(PromotionDto promotionDto) {
        // 1. 상품 조회
        Item findItem = itemRepository.findById(promotionDto.getItemId())
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("PROMOTION NOT EXIST");
                });
        // 2. Promotion 생성 및 저장
        DiscountPolicy discountPolicy = validateDiscountPolicy(promotionDto);
        Promotion newPromotion = Promotion.builder()
                .promotionName(promotionDto.getPromotionName())
                .promotionDate(new StartEndDate(
                        LocalDate.parse(promotionDto.getStartDate(), dateTimeFormatter),
                        LocalDate.parse(promotionDto.getEndDate(), dateTimeFormatter)
                ))
                .discountPolicy(discountPolicy)
                .build();
        Promotion createPromotion = promotionRepository.save(newPromotion);
        // 3. PromotionItem 생성 및 저장
        PromotionItem promotionItem = new PromotionItem(createPromotion, findItem);
        promotionItemRepository.save(promotionItem);
        return promotionDto;
    }

    @Override
    public void deletePromotion(Long id) {
        Promotion promotion = promotionRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("PROMOTION NOT FOUND");
        });
        promotionRepository.delete(promotion);
    }

    private DiscountPolicy validateDiscountPolicy(PromotionDto promotionDto) {
        if(promotionDto.getDiscountAmount() != null && promotionDto.getDiscountAmount() > 0
                && promotionDto.getDiscountRate() != null && promotionDto.getDiscountRate() > 0) {
            throw new IllegalArgumentException("ONLY ONE POLICY CAN BE APPLIED");
        } else if(promotionDto.getDiscountAmount() != null && promotionDto.getDiscountAmount() > 0) {
            return DiscountPolicy.builder().discountAmount(promotionDto.getDiscountAmount()).build();
        } else if(promotionDto.getDiscountRate() != null && promotionDto.getDiscountRate() > 0) {
            return DiscountPolicy.builder().discountRate(promotionDto.getDiscountRate()).build();
        } else {
            throw new IllegalArgumentException("DISCOUNT POLICY NOT APPLIED");
        }
    }
}
