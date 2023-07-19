package springjpaexercise.useritempromotionexample.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import springjpaexercise.useritempromotionexample.entity.dto.ItemDto;
import springjpaexercise.useritempromotionexample.exception.DisplayDateNotValidException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@Aspect
public class CheckDisplayDateAspect {
    @Around("@annotation(springjpaexercise.useritempromotionexample.annotation.CheckDisplayDate)")
    public Object doAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        ItemDto itemDto = null;
        for(Object arg : args) {
            if(arg instanceof ItemDto) {
                itemDto = (ItemDto)arg;
                break;
            }
        }
        LocalDate startDate = LocalDate.parse(itemDto.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endDate = LocalDate.parse(itemDto.getEndDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        if(startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("StartDate is after EndDate.");
        }
        return joinPoint.proceed();
    }
}
