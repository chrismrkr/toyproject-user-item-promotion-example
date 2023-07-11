package springjpaexercise.useritempromotionexample.service;

import springjpaexercise.useritempromotionexample.entity.Item;
import springjpaexercise.useritempromotionexample.entity.dto.ItemDto;

import java.util.List;

public interface ItemService {
    Item create(ItemDto itemDto);
    Item update(ItemDto itemDto, Long id);
    List<Item> findItemListByUserType(Long userId);
}
