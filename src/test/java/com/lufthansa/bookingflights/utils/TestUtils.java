package com.lufthansa.bookingflights.utils;

import com.lufthansa.bookingflights.model.BookingFlightInfo;
import com.lufthansa.bookingflights.model.BookingInfo;
import com.lufthansa.bookingflights.model.FlightInfo;
import com.lufthansa.bookingflights.model.request.BookingFlightsRequest;
import com.lufthansa.bookingflights.model.response.BookingFlightsResponse;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public final class TestUtils {

    public static final String BOOKING_ID = "bookingId";
    public static final int ROUTE_ID = 1;
    public static final String ORIGIN = "FRA";
    public static List<String> INVALID_ORIGIN_DESTINATION = Arrays.asList("FRAJFK", null);
    public static final String DESTINATION = "JFK";
    public static final String FLIGHT_NUM = "LH0001";
    public static final LocalDate FLIGHT_DATE = LocalDate.now();
    public static final int NUM_OF_TRANSITS = 1;
    public static final int INVALID_NUM_OF_TRANSITS = -1;
    public static final double INVALID_PRICE = -100d;
    public static final double MIN_PRICE = 100d;
    public static final double MAX_PRICE = 1000d;
    public static final String BOOKED_FLIGHT_NOT_FOUND_ERROR_MESSAGE = "could not find the booked flight with " +
            "entered booking Id";
    public static final String FLIGHT_STATUS_BOOKED = "BOOKED";
    public static final String VALIDATION_ERROR_MESSAGE = "min price should be less than max price";
    public static final String ERROR_MESSAGE = "Exception message";

    public static final BookingInfo BOOKING_INFO = BookingInfo.builder()
            .bookingId(BOOKING_ID)
            .routeId(ROUTE_ID)
            .destination(DESTINATION)
            .flightDate(FLIGHT_DATE)
            .flightNum(FLIGHT_NUM)
            .numOfTransits(NUM_OF_TRANSITS)
            .origin(ORIGIN)
            .build();

    public static final BookingFlightsRequest BOOKING_FLIGHTS_REQUEST = BookingFlightsRequest.builder()
            .destination(DESTINATION)
            .flightDate(FLIGHT_DATE)
            .numOfTransits(NUM_OF_TRANSITS)
            .origin(ORIGIN)
            .minPrice(MIN_PRICE)
            .maxPrice(MAX_PRICE)
            .build();

    public static final BookingFlightsResponse BOOKING_FLIGHTS_RESPONSE = BookingFlightsResponse.builder()
            .bookingId(BOOKING_ID)
            .routeId(ROUTE_ID)
            .origin(ORIGIN)
            .destination(DESTINATION)
            .flightNumber(FLIGHT_NUM)
            .flightDate(FLIGHT_DATE)
            .numOfTransit(NUM_OF_TRANSITS)
            .build();

    public static final FlightInfo FLIGHT_INFO = FlightInfo.builder()
            .routeId(ROUTE_ID)
            .flightNum(FLIGHT_NUM)
            .build();

    public static final BookingFlightInfo BOOKING_FLIGHT_INFO = BookingFlightInfo.builder()
            .flightInfo(FLIGHT_INFO)
            .bookingId(BOOKING_ID)
            .build();
}
