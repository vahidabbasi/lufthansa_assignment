package com.lufthansa.bookingflights.controller;

import com.lufthansa.bookingflights.exceptions.BookingFlightsException;
import com.lufthansa.bookingflights.model.response.BookingFlightsResponse;
import com.lufthansa.bookingflights.service.BookingFlightsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static com.lufthansa.bookingflights.utils.TestUtils.*;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookingFlightsControllerTest {

    @Mock
    private BookingFlightsService bookingFlightsService;

    @InjectMocks
    private BookingFlightsController bookingFlightsController;

    @Test
    public void shouldBookFlightWhenFlightWasNotBooked() {

        when(bookingFlightsService.getBooking(anyString())).thenReturn(Optional.of(BOOKING_INFO));
        when(bookingFlightsService.updateFlightInfo(anyString(), anyString(), anyString(), any(), anyInt(),
                anyDouble(), anyDouble())).thenReturn(BOOKING_FLIGHT_INFO);

        ResponseEntity<BookingFlightsResponse> actualResponse = bookingFlightsController.bookFlight(BOOKING_ID, BOOKING_FLIGHTS_REQUEST);

        verify(bookingFlightsService).getBooking(BOOKING_ID);
        verify(bookingFlightsService).updateFlightInfo(BOOKING_ID, BOOKING_FLIGHTS_REQUEST.getOrigin(),
                BOOKING_FLIGHTS_REQUEST.getDestination(), BOOKING_FLIGHTS_REQUEST.getFlightDate(),
                BOOKING_FLIGHTS_REQUEST.getNumOfTransits(), BOOKING_FLIGHTS_REQUEST.getMinPrice(),
                BOOKING_FLIGHTS_REQUEST. getMaxPrice());
        verifyNoMoreInteractions(bookingFlightsService);

        assertEquals(BOOKING_FLIGHTS_RESPONSE, actualResponse.getBody());
        assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
    }

    @Test
    public void shouldUpdateBookedFlightWhenFlightWasBooked() {

        when(bookingFlightsService.getBooking(anyString())).thenReturn(Optional.ofNullable(null));
        when(bookingFlightsService.bookFlight(anyString(), anyString(), anyString(), any(), anyInt(),
                anyDouble(), anyDouble())).thenReturn(BOOKING_FLIGHT_INFO);

        ResponseEntity<BookingFlightsResponse> actualResponse = bookingFlightsController.bookFlight(BOOKING_ID, BOOKING_FLIGHTS_REQUEST);

        verify(bookingFlightsService).getBooking(BOOKING_ID);
        verify(bookingFlightsService).bookFlight(BOOKING_ID, BOOKING_FLIGHTS_REQUEST.getOrigin(),
                BOOKING_FLIGHTS_REQUEST.getDestination(), BOOKING_FLIGHTS_REQUEST.getFlightDate(),
                BOOKING_FLIGHTS_REQUEST.getNumOfTransits(), BOOKING_FLIGHTS_REQUEST.getMinPrice(),
                BOOKING_FLIGHTS_REQUEST. getMaxPrice());
        verifyNoMoreInteractions(bookingFlightsService);

        assertEquals(BOOKING_FLIGHTS_RESPONSE, actualResponse.getBody());
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    public void shouldGetBooking() {

        when(bookingFlightsService.getBooking(anyString())).thenReturn(Optional.of(BOOKING_INFO));

        ResponseEntity<BookingFlightsResponse> actualResponse = bookingFlightsController.getBooking(BOOKING_ID);

        verify(bookingFlightsService).getBooking(BOOKING_ID);
        verifyNoMoreInteractions(bookingFlightsService);

        assertEquals(BOOKING_FLIGHTS_RESPONSE, actualResponse.getBody());
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    public void shouldGetBookingFlightsExceptionIfFlightNotFound() {

        when(bookingFlightsService.getBooking(anyString())).thenReturn(Optional.ofNullable(null));

        try {
            bookingFlightsController.getBooking(BOOKING_ID);
            fail("Expected exception");
        } catch (final BookingFlightsException e) {
            verify(bookingFlightsService).getBooking(BOOKING_ID);
            assertEquals(HttpStatus.NOT_FOUND, e.getHttpStatus());
            assertEquals(BOOKED_FLIGHT_NOT_FOUND_ERROR_MESSAGE, e.getMessage());
        }
    }
}