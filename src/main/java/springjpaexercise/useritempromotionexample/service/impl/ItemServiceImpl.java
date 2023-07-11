package springjpaexercise.useritempromotionexample.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springjpaexercise.useritempromotionexample.entity.Item;
import springjpaexercise.useritempromotionexample.entity.User;
import springjpaexercise.useritempromotionexample.entity.dto.ItemDto;
import springjpaexercise.useritempromotionexample.entity.embeddable.StartEndDate;
import springjpaexercise.useritempromotionexample.entity.enumtype.ItemType;
import springjpaexercise.useritempromotionexample.entity.enumtype.UserStat;
import springjpaexercise.useritempromotionexample.entity.enumtype.UserType;
import springjpaexercise.useritempromotionexample.repository.ItemRepository;
import springjpaexercise.useritempromotionexample.repository.UserRepository;
import springjpaexercise.useritempromotionexample.service.ItemService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public Item create(ItemDto itemDto) {
        Item newItem = Item.builder().itemName(itemDto.getItemName())
                .itemPrice(itemDto.getItemPrice())
                .itemType(ItemType.valueOf(itemDto.getItemType()))
                .itemDisplayDate(new StartEndDate(
                        LocalDate.parse(itemDto.getStartDate(), dateTimeFormatter),
                        LocalDate.parse(itemDto.getEndDate(), dateTimeFormatter)
                )).build();
        try {
            Item save = itemRepository.save(newItem);
            return save;
        } catch(Exception e) {
            throw new IllegalStateException("["+e.getMessage()+"]" + "USER SAVE FAIL : DB ERROR");
        }
    }
    @Override
    @Transactional
    public Item update(ItemDto itemDto, Long id) {
        Item findItem = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ITEM NOT EXIST"));
        if(itemDto.getItemName() != null) {
            findItem.updateItemName(itemDto.getItemName());
        }
        if(itemDto.getItemPrice() != null) {
            findItem.updateItemPrice(itemDto.getItemPrice());
        }
        if(itemDto.getItemType() != null) {
            findItem.updateItemType(itemDto.getItemType());
        }
        if(itemDto.getStartDate() != null) {
            findItem.updateStartDate(itemDto.getStartDate(), dateTimeFormatter);
        }
        if(itemDto.getEndDate() != null) {
            findItem.updateEndDate(itemDto.getEndDate(), dateTimeFormatter);
        }
        return findItem;
    }

    @Override
    public List<Item> findItemListByUserType(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("USER NOT EXISTS"));
        if(findUser.getUserStat().equals(UserStat.WITHDRAWAL)) {
            throw new IllegalStateException("WITHDRAWAL USER CANNOT GET ITEM LIST");
        }
        if(findUser.getUserType().equals(UserType.ENTERPRISE)) {
            return itemRepository.findByDateBetweenStartEndDate(LocalDate.now());
        } else {
            return itemRepository.findByItemTypeAndByDateBetweenStartEndDate(
                    ItemType.GENERAL, LocalDate.now()
                    );g
        }
    }


}
