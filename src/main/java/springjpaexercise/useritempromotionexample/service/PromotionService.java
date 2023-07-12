package springjpaexercise.useritempromotionexample.service;

import springjpaexercise.useritempromotionexample.entity.vo.PromotionItemVo;

public interface PromotionService {
    PromotionItemVo findPromotionItem(Long itemId);
}
