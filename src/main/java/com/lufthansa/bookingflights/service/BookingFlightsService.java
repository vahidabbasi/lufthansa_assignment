package com.lufthansa.bookingflights.service;

import com.lufthansa.bookingflights.exceptions.BookingFlightsException;
import com.lufthansa.bookingflights.model.BookingFlightInfo;
import com.lufthansa.bookingflights.model.BookingInfo;
import com.lufthansa.bookingflights.model.FlightInfo;
import com.lufthansa.bookingflights.repository.BookingFlightsDAO;
import com.lufthansa.bookingflights.validators.RequestValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class BookingFlightsService {

    private static final String FLIGHT_STATUS_BOOKED = "BOOKED";
    private static final String ERROR_MESSAGE = "could not find the flight with entered inputs";
    private final BookingFlightsDAO bookingFlightsDAO;
    private final RequestValidator requestValidator;


    @Autowired
    public BookingFlightsService(BookingFlightsDAO bookingFlightsDAO, RequestValidator requestValidator) {
        Objects.requireNonNull(bookingFlightsDAO, "bookingFlightsDAO was null when injected");
        Objects.requireNonNull(requestValidator, "requestValidator was null when injected");
        this.bookingFlightsDAO = bookingFlightsDAO;
        this.requestValidator = requestValidator;
    }

    public BookingFlightInfo bookFlight(String bookingId, String origin, String destination, LocalDate flightDate,
                                        int numOfTransits, double minPrice, double maxPrice) {

        log.info("update a booked flight in database");
        requestValidator.validatePrice(minPrice, maxPrice);

        FlightInfo flightInfo = bookingFlightsDAO.getFlightInfo(origin, destination, flightDate,
                numOfTransits, minPrice, maxPrice).orElseThrow(() -> new BookingFlightsException(ERROR_MESSAGE,
                HttpStatus.NOT_FOUND));

        bookingFlightsDAO.bookFlight(bookingId, flightInfo.getRouteId(),
                FLIGHT_STATUS_BOOKED);

        return BookingFlightInfo.builder()
                .flightInfo(flightInfo)
                .bookingId(bookingId)
                .build();
    }

    public BookingFlightInfo updateFlightInfo(String bookingId, String origin, String destination, LocalDate flightDate,
                                        int numOfTransits, double minPrice, double maxPrice) {

        log.info("book a flight in database");
        requestValidator.validatePrice(minPrice, maxPrice);

        FlightInfo flightInfo = bookingFlightsDAO.getFlightInfo(origin, destination, flightDate,
                numOfTransits, minPrice, maxPrice).orElseThrow(() -> new BookingFlightsException(ERROR_MESSAGE,
                HttpStatus.NOT_FOUND));

        bookingFlightsDAO.updateFlightRoute(bookingId, flightInfo.getRouteId());

        return BookingFlightInfo.builder()
                .flightInfo(flightInfo)
                .bookingId(bookingId)
                .build();
    }

    public Optional<BookingInfo> getBooking(String bookId) {
        return bookingFlightsDAO.getBookingInfo(bookId);
    }
}
