package com.lufthansa.bookingflights.exceptions;

public class BookingIdGenerationException extends RuntimeException {
    private final String displayMessage;

    public BookingIdGenerationException(final String message) {
        super(message);
        displayMessage = message;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

}
