package springjpaexercise.useritempromotionexample.service;

import springjpaexercise.useritempromotionexample.entity.dto.PromotionItemDto;

public interface PromotionService {
    PromotionItemDto findPromotionItem(Long itemId);
}
