package edu.dadaev.greenpoint.service;

import edu.dadaev.greenpoint.entity.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class PriceService {

    public BigDecimal calculate(Resource resource, LocalDate startDate, LocalDate endDate){
         long days = ChronoUnit.DAYS.between(startDate, endDate);
         if(days <= 0){
             throw new IllegalArgumentException("invalid date range");
         }
         BigDecimal pricePerDay = resource.getPrice();

         return pricePerDay.multiply(BigDecimal.valueOf(days));
    }
}
