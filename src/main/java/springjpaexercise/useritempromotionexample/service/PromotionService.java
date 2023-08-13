package springjpaexercise.useritempromotionexample.service;

import springjpaexercise.useritempromotionexample.entity.dto.PromotionDto;
import springjpaexercise.useritempromotionexample.entity.dto.PromotionItemDto;

public interface PromotionService {
    PromotionItemDto findPromotionItem(Long itemId);
    PromotionDto savePromotion(PromotionDto promotionDto);
    void deletePromotion(Long id);
}
