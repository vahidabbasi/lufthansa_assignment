package com.lufthansa.bookingflights.handlers;

import com.lufthansa.bookingflights.exceptions.BookingFlightsException;
import com.lufthansa.bookingflights.exceptions.BookingIdGenerationException;
import com.lufthansa.bookingflights.model.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.ResponseEntity.status;

@Slf4j
@ControllerAdvice
public class BookingFlightsExceptionHandler {
    /**
     * To make sure that we do not let any exception through to the customer.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception exception) {
        log.error("RuntimeException: ", exception);
        return internalServerError(exception.getMessage());
    }

    @ExceptionHandler(BookingFlightsException.class)
    public ResponseEntity handleBookingFlightsException(BookingFlightsException exception) {
        log.error("bookingFlightsException: {}", exception.getMessage());
        return createError(exception.getHttpStatus(), exception.getDisplayMessage());
    }

    @ExceptionHandler(BookingIdGenerationException.class)
    public ResponseEntity handleBookingIdGenerationException(final BookingIdGenerationException exception) {
        log.info("ReferenceGenerationException: {}", exception.getMessage());
        return internalServerError(exception.getDisplayMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.error("HttpMessageNotReadableException: ", exception);
        return badRequest("Format not supported");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("MethodArgumentNotValidException: ", exception);
        FieldError fieldError = exception.getBindingResult().getFieldError();
        return badRequest(fieldError.getField() + ": " + fieldError.getDefaultMessage());
    }

    /**
     * Returns an INTERNAL_SERVER_ERROR to the client with the given error message.
     */
    private static ResponseEntity<ErrorResponse> internalServerError(String displayMessage) {
        log.error("Internal server error: {}", displayMessage);
        return createError(HttpStatus.SERVICE_UNAVAILABLE, displayMessage);
    }

    /**
     * Returns an HTTP error with the given statuses.
     */
    private static ResponseEntity<ErrorResponse> createError(HttpStatus httpStatus, String message) {
        return status(httpStatus).body(ErrorResponse.builder()
                .message(message)
                .build());
    }

    /**
     * Returns a 400 BAD_REQUEST response with the specified status.
     */
    private ResponseEntity<ErrorResponse> badRequest(String message) {
        return createError(HttpStatus.BAD_REQUEST, message);
    }
}
