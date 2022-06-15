package com.lufthansa.bookingflights.validators;

import com.lufthansa.bookingflights.exceptions.BookingFlightsException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import static com.lufthansa.bookingflights.utils.TestUtils.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class RequestValidatorTest {

    public static final int PRICE1 = 100;
    public static final int PRICE2 = 10;
    RequestValidator requestValidator = new RequestValidator();

    @Test
    public void shouldThrowExceptionIfMinPriceIsBiggerTHanMaxPrice() {
        try {
            requestValidator.validatePrice(PRICE1, PRICE2);
        } catch (BookingFlightsException ex) {
            assertEquals(VALIDATION_ERROR_MESSAGE, ex.getMessage());
            assertEquals(HttpStatus.BAD_REQUEST, ex.getHttpStatus());
        }
    }
}