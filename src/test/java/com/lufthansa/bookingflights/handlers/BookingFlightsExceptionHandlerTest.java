package com.lufthansa.bookingflights.handlers;

import com.lufthansa.bookingflights.exceptions.BookingFlightsException;
import com.lufthansa.bookingflights.model.response.ErrorResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static com.lufthansa.bookingflights.utils.TestUtils.ERROR_MESSAGE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookingFlightsExceptionHandlerTest {

    private BookingFlightsExceptionHandler exceptionHandler = new BookingFlightsExceptionHandler();

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private FieldError fieldError;

    @Test
    public void shouldHandleGeneralException() {

        ResponseEntity responseEntity = exceptionHandler.handleException(new Exception());

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, responseEntity.getStatusCode());
        assertEquals(ErrorResponse.builder().build(), responseEntity.getBody());
    }

    @Test
    public void shouldHandleBookingFlightsException() {
        BookingFlightsException bookingFlightsException = new BookingFlightsException(ERROR_MESSAGE);

        ResponseEntity responseEntity = exceptionHandler.handleBookingFlightsException(bookingFlightsException);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertEquals(ErrorResponse.builder().message(ERROR_MESSAGE).build(), responseEntity.getBody());
    }

    @Test
    public void shouldHandleHttpMessageNotReadableException() {
        HttpMessageNotReadableException httpMessageNotReadableException = new HttpMessageNotReadableException(ERROR_MESSAGE);

        ResponseEntity responseEntity = exceptionHandler.handleHttpMessageNotReadableException(httpMessageNotReadableException);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(ErrorResponse.builder().message("Format not supported").build(), responseEntity.getBody());
    }

    @Test
    public void shouldHandleMethodArgumentNotValidException() {
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldError()).thenReturn(fieldError);
        when(fieldError.getField()).thenReturn("field");
        when(fieldError.getDefaultMessage()).thenReturn("ArgumentNotValid");

        ResponseEntity responseEntity = exceptionHandler.handleMethodArgumentNotValidException(methodArgumentNotValidException);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(ErrorResponse.builder().message("field: ArgumentNotValid").build(), responseEntity.getBody());
    }
}