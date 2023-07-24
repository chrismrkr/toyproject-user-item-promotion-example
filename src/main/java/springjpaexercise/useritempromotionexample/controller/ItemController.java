package springjpaexercise.useritempromotionexample.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springjpaexercise.useritempromotionexample.entity.Item;
import springjpaexercise.useritempromotionexample.entity.dto.ItemDto;
import springjpaexercise.useritempromotionexample.entity.dto.ItemListDto;
import springjpaexercise.useritempromotionexample.service.ItemService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/item")
    public ItemListDto selectItem(@RequestParam(name = "userId") String userId) {
        List<Item> itemList = itemService.findItemListByUserType(Long.parseLong(userId));
        ItemListDto itemListDto = createItemListDto(Long.parseLong(userId), itemList);
        return itemListDto;
    }

    @PostMapping("/item")
    public ItemDto createItem(@Valid @RequestBody ItemDto itemDto) {
        Item item = itemService.create(itemDto);
        ItemDto responseDto = createResponseItemDto(item);
        return responseDto;
    }
    @PutMapping("/item/{id}")
    public ItemDto updateItem(@Valid @RequestBody ItemDto itemDto, @PathVariable("id") Long id) {
        Item updatedItem = itemService.update(itemDto, id);
        ItemDto responseDto = createResponseItemDto(updatedItem);
        return responseDto;
    }
    @DeleteMapping("/item/{id}")
    public String deleteItem(@PathVariable("id") Long id) {
        itemService.delete(id);
        return "ok";
    }

    private ItemDto createResponseItemDto(Item item) {
        ItemDto itemDto = ItemDto.builder()
                .itemName(item.getItemName())
                .itemPrice(item.getItemPrice())
                .itemType(item.getItemType().toString())
                .startDate(item.getItemDisplayDate().getStartDate().toString())
                .endDate(item.getItemDisplayDate().getEndDate().toString())
                .build();
        return itemDto;
    }
    private ItemListDto createItemListDto(Long userId, List<Item> itemList) {
        ItemListDto itemListDto = ItemListDto.builder()
                .userId(userId)
                .itemCount(itemList.size())
                .itemList(itemList)
                .build();
        return itemListDto;
    }
}
