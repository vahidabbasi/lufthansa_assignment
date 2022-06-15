package com.lufthansa.bookingflights.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when validation failed or other cases
 */
public class BookingFlightsException extends RuntimeException {
    private final String displayMessage;
    private final HttpStatus httpStatus;

    public BookingFlightsException(String message) {
        super(message);
        displayMessage = message;
        httpStatus = HttpStatus.FORBIDDEN;
    }

    public BookingFlightsException(String message,
                                   HttpStatus httpStatus) {
        super(message);
        displayMessage = message;
        this.httpStatus = httpStatus;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
