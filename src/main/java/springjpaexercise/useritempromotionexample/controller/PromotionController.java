package springjpaexercise.useritempromotionexample.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import springjpaexercise.useritempromotionexample.entity.dto.PromotionItemDto;
import springjpaexercise.useritempromotionexample.service.PromotionService;

@RestController
@RequiredArgsConstructor
public class PromotionController {
    private final PromotionService promotionService;

    @GetMapping("/promotion/item/{itemId}")
    public PromotionItemDto selectPromotion(@PathVariable(name = "itemId") Long itemId) {
        PromotionItemDto promotionItem = promotionService.findPromotionItem(itemId);
        return promotionItem;
    }
}
