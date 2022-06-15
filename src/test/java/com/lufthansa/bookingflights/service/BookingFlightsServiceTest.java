package com.lufthansa.bookingflights.service;

import com.lufthansa.bookingflights.exceptions.BookingIdGenerationException;
import com.lufthansa.bookingflights.model.BookingFlightInfo;
import com.lufthansa.bookingflights.model.BookingInfo;
import com.lufthansa.bookingflights.repository.BookingFlightsDAO;
import com.lufthansa.bookingflights.validators.RequestValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Optional;

import static com.lufthansa.bookingflights.utils.TestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BookingFlightsServiceTest  {

    @Mock
    private RequestValidator requestValidator;

    @Mock
    private BookingFlightsDAO bookingFlightsDAO;

    @InjectMocks
    private BookingFlightsService bookingFlightsService;

    @Test
    public void shouldBookFlightWhenClientProvideBookingId() {
        doNothing().when(requestValidator).validatePrice(anyDouble(), anyDouble());
        when(bookingFlightsDAO.getFlightInfo(anyString(), anyString(), any(), anyInt(),anyDouble(), anyDouble()))
                .thenReturn(Optional.of(FLIGHT_INFO));
        doNothing().when(bookingFlightsDAO).bookFlight(anyString(), anyInt(), anyString());

        BookingFlightInfo actualResponse = bookingFlightsService.bookFlight(BOOKING_ID, ORIGIN, DESTINATION, FLIGHT_DATE,
                NUM_OF_TRANSITS, MIN_PRICE, MAX_PRICE);

        verify(bookingFlightsDAO).getFlightInfo(ORIGIN, DESTINATION, FLIGHT_DATE,NUM_OF_TRANSITS, MIN_PRICE, MAX_PRICE);
        verify(bookingFlightsDAO).bookFlight(BOOKING_ID, FLIGHT_INFO.getRouteId(), FLIGHT_STATUS_BOOKED);
        verifyNoMoreInteractions(bookingFlightsDAO);

        assertEquals(BOOKING_FLIGHT_INFO, actualResponse);
    }

    @Test
    public void shouldUpdateBookedFlight() {

        doNothing().when(requestValidator).validatePrice(anyDouble(), anyDouble());
        when(bookingFlightsDAO.getFlightInfo(anyString(), anyString(), any(), anyInt(),anyDouble(), anyDouble()))
                .thenReturn(Optional.of(FLIGHT_INFO));
        doNothing().when(bookingFlightsDAO).updateFlightRoute(anyString(), anyInt());

        BookingFlightInfo actualResponse = bookingFlightsService.updateFlightInfo(BOOKING_ID, ORIGIN, DESTINATION, FLIGHT_DATE,
                NUM_OF_TRANSITS, MIN_PRICE, MAX_PRICE);

        verify(bookingFlightsDAO).getFlightInfo(ORIGIN, DESTINATION, FLIGHT_DATE,NUM_OF_TRANSITS, MIN_PRICE, MAX_PRICE);
        verify(bookingFlightsDAO).updateFlightRoute(BOOKING_ID, FLIGHT_INFO.getRouteId());
        verifyNoMoreInteractions(bookingFlightsDAO);

        assertEquals(BOOKING_FLIGHT_INFO, actualResponse);
    }

    @Test
    public void shouldGetBooking() {

        when(bookingFlightsDAO.getBookingInfo(anyString())).thenReturn(Optional.of(BOOKING_INFO));

        Optional<BookingInfo> actualResponse = bookingFlightsService.getBooking(BOOKING_ID);

        verify(bookingFlightsDAO).getBookingInfo(BOOKING_ID);
        verifyNoMoreInteractions(bookingFlightsDAO);

        assertEquals(BOOKING_INFO, actualResponse.get());
    }

    @Test
    public void shouldBookFlight() {
        doNothing().when(requestValidator).validatePrice(anyDouble(), anyDouble());
        when(bookingFlightsDAO.getFlightInfo(anyString(), anyString(), any(), anyInt(),anyDouble(), anyDouble()))
                .thenReturn(Optional.of(FLIGHT_INFO));
        when(bookingFlightsDAO.isBookingIdExist(anyString())).thenReturn(0);

        BookingFlightInfo actualResponse = bookingFlightsService.bookFlight(ORIGIN, DESTINATION, FLIGHT_DATE,
                NUM_OF_TRANSITS, MIN_PRICE, MAX_PRICE);

        verify(bookingFlightsDAO).getFlightInfo(ORIGIN, DESTINATION, FLIGHT_DATE,NUM_OF_TRANSITS, MIN_PRICE, MAX_PRICE);
        verify(bookingFlightsDAO).bookFlight(anyString(), eq(FLIGHT_INFO.getRouteId()), eq(FLIGHT_STATUS_BOOKED));
        verify(bookingFlightsDAO).isBookingIdExist(anyString());
        verifyNoMoreInteractions(bookingFlightsDAO);

        actualResponse.setBookingId(BOOKING_ID);
        assertEquals(BOOKING_FLIGHT_INFO, actualResponse);
    }

    @Test(expected = BookingIdGenerationException.class)
    public void shouldBookFlightThrowException() {
        doNothing().when(requestValidator).validatePrice(anyDouble(), anyDouble());
        when(bookingFlightsDAO.getFlightInfo(anyString(), anyString(), any(), anyInt(),anyDouble(), anyDouble()))
                .thenReturn(Optional.of(FLIGHT_INFO));
        when(bookingFlightsDAO.isBookingIdExist(anyString())).thenReturn(1);

        bookingFlightsService.bookFlight(ORIGIN, DESTINATION, FLIGHT_DATE,
                NUM_OF_TRANSITS, MIN_PRICE, MAX_PRICE);
    }
}