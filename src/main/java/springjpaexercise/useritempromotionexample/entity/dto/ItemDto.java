package springjpaexercise.useritempromotionexample.entity.dto;

import lombok.Builder;
import lombok.Data;
import springjpaexercise.useritempromotionexample.entity.embeddable.StartEndDate;
import springjpaexercise.useritempromotionexample.entity.enumtype.ItemType;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
public class ItemDto {
    private String itemName;
    private String itemType;
    private Integer itemPrice;
    private String startDate;
    private String endDate;
}
