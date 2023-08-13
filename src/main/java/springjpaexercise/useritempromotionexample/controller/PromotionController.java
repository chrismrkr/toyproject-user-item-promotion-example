package springjpaexercise.useritempromotionexample.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springjpaexercise.useritempromotionexample.entity.dto.PromotionDto;
import springjpaexercise.useritempromotionexample.entity.dto.PromotionItemDto;
import springjpaexercise.useritempromotionexample.service.PromotionService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class PromotionController {
    private final PromotionService promotionService;

    @GetMapping("/promotion")
    public PromotionItemDto selectPromotion(@RequestParam(name = "itemId") String itemId) {
        PromotionItemDto promotionItem = promotionService.findPromotionItem(Long.parseLong(itemId));
        return promotionItem;
    }

    @PostMapping("/promotion")
    public PromotionDto createPromotion(@Valid @RequestBody PromotionDto promotionDto) {
        PromotionDto result = promotionService.savePromotion(promotionDto);
        return result;
    }

    @DeleteMapping("/promotion/{promotionId}")
    public String deletePromotion(@PathVariable(name = "promotionId") Long id) {
        promotionService.deletePromotion(id);
        return "ok";
    }
}
