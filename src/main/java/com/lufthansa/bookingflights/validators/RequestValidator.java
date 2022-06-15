package com.lufthansa.bookingflights.validators;

import com.lufthansa.bookingflights.exceptions.BookingFlightsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RequestValidator {

    public void validatePrice(double minPrice, double maxPrice) {
        log.info("Validate Price");
        if(minPrice >= maxPrice){
            throw new BookingFlightsException("min price should be less than max price",
                    HttpStatus.BAD_REQUEST);
        }
    }
}
