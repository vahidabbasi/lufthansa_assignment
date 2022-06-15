package com.lufthansa.bookingflights.model.request;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.lufthansa.bookingflights.utils.TestUtils.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class BookingFlightsRequestTest extends AbstractValidatorTest{

    private BookingFlightsRequest bookingFlightsRequest;

    @Before
    public void before() {
        bookingFlightsRequest = BookingFlightsRequest.builder()
                .destination(DESTINATION)
                .flightDate(FLIGHT_DATE)
                .numOfTransits(NUM_OF_TRANSITS)
                .origin(ORIGIN)
                .minPrice(MIN_PRICE)
                .maxPrice(MAX_PRICE)
                .build();
    }

    @Test
    public void shouldBeValidRequest() {
        assertTrue(isRequestValid(bookingFlightsRequest));
    }

    @Test
    public void shouldValidateInvalidFlightDate() {
        bookingFlightsRequest.setFlightDate(null);
        assertFalse(isRequestValid(bookingFlightsRequest));
    }

    @Test
    public void shouldValidateNumOfTransits() {
        bookingFlightsRequest.setNumOfTransits(INVALID_NUM_OF_TRANSITS);
        assertFalse(isRequestValid(bookingFlightsRequest));
    }

    @Test
    public void shouldValidateMinPrice() {
        bookingFlightsRequest.setMinPrice(INVALID_PRICE);
        assertFalse(isRequestValid(bookingFlightsRequest));
    }

    @Test
    public void shouldValidateMaxPrice() {
        bookingFlightsRequest.setMaxPrice(INVALID_PRICE);
        assertFalse(isRequestValid(bookingFlightsRequest));
    }

    @Test
    public void shouldValidateOrigin() {
        INVALID_ORIGIN_DESTINATION.forEach(value -> assertFalse(isOriginValid(value)));
    }

    @Test
    public void shouldValidateDestination() {
        INVALID_ORIGIN_DESTINATION.forEach(value -> assertFalse(isDestinationValid(value)));
    }

    private boolean isOriginValid(String origin) {
        bookingFlightsRequest.setOrigin(origin);
        return isRequestValid(bookingFlightsRequest);
    }

    private boolean isDestinationValid(String origin) {
        bookingFlightsRequest.setOrigin(origin);
        return isRequestValid(bookingFlightsRequest);
    }
}