package springjpaexercise.useritempromotionexample.entity.embeddable;

import lombok.*;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Setter
@Getter
public class StartEndDate {
    private LocalDate startDate;
    private LocalDate endDate;
}
