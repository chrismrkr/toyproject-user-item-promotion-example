package springjpaexercise.useritempromotionexample.service;

import springjpaexercise.useritempromotionexample.entity.Item;
import springjpaexercise.useritempromotionexample.entity.dto.ItemDto;

public interface ItemService {
    Item create(ItemDto itemDto);
    Item update(ItemDto itemDto, Long id);
}
