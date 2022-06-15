package com.lufthansa.bookingflights.controller;

import com.lufthansa.bookingflights.exceptions.BookingFlightsException;
import com.lufthansa.bookingflights.model.BookingFlightInfo;
import com.lufthansa.bookingflights.model.BookingInfo;
import com.lufthansa.bookingflights.model.request.BookingFlightsRequest;
import com.lufthansa.bookingflights.model.response.BookingFlightsResponse;
import com.lufthansa.bookingflights.model.response.ErrorResponse;
import com.lufthansa.bookingflights.service.BookingFlightsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.HttpURLConnection;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value = "/v1")
@Validated
@Api("A REST-controller to handle various request to service")
public class BookingFlightsController {

    private static final String ERROR_MESSAGE = "could not find the booked flight with entered booking Id";
    private final BookingFlightsService bookingFlightsService;

    @Autowired
    public BookingFlightsController(BookingFlightsService bookingFlightsService) {
        Objects.requireNonNull(bookingFlightsService, "bookingFlightsService was null when injected");
        this.bookingFlightsService = bookingFlightsService;
    }

    @PutMapping(value ="/bookings/{bookingId}", produces = "application/json")
    @ApiOperation(value = "book a flight")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_CREATED, message = "Flight has been booked.", response =
                    BookingFlightsResponse.class),
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Booked flight has been updated.", response =
                    BookingFlightsResponse.class),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "The request is missing or have badly formatted"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAVAILABLE, message = "Server error", response = ErrorResponse.class)
    })
    public ResponseEntity<BookingFlightsResponse> bookFlight(@PathVariable String bookingId,
                                                             @RequestBody @Valid BookingFlightsRequest bookingFlightsRequest) {

        Optional<BookingInfo> bookingInfo = bookingFlightsService.getBooking(bookingId);

        BookingFlightInfo bookingFlightInfo;

        if(bookingInfo.isPresent()) {
            bookingFlightInfo = bookingFlightsService.updateFlightInfo(bookingId,
                    bookingFlightsRequest.getOrigin(),
                    bookingFlightsRequest.getDestination(),
                    bookingFlightsRequest.getFlightDate(), bookingFlightsRequest.getNumOfTransits(),
                    bookingFlightsRequest.getMinPrice(), bookingFlightsRequest.getMaxPrice());

            BookingFlightsResponse bookingFlightsResponse = getBookingFlightsResponse(bookingFlightsRequest, bookingFlightInfo);

            return ResponseEntity.status(HttpStatus.CREATED).body(bookingFlightsResponse);

        } else {
            bookingFlightInfo = bookingFlightsService.bookFlight(bookingId,
                    bookingFlightsRequest.getOrigin(),
                    bookingFlightsRequest.getDestination(),
                    bookingFlightsRequest.getFlightDate(), bookingFlightsRequest.getNumOfTransits(),
                    bookingFlightsRequest.getMinPrice(), bookingFlightsRequest.getMaxPrice());

            BookingFlightsResponse bookingFlightsResponse = getBookingFlightsResponse(bookingFlightsRequest, bookingFlightInfo);

            return ResponseEntity.status(HttpStatus.OK).body(bookingFlightsResponse);
        }
    }

    private BookingFlightsResponse getBookingFlightsResponse(@RequestBody @Valid BookingFlightsRequest bookingFlightsRequest, BookingFlightInfo bookingFlightInfo) {
        return BookingFlightsResponse.builder()
                .flightDate(bookingFlightsRequest.getFlightDate())
                .destination(bookingFlightsRequest.getDestination())
                .numOfTransit(bookingFlightsRequest.getNumOfTransits())
                .origin(bookingFlightsRequest.getOrigin())
                .flightNumber(bookingFlightInfo.getFlightInfo().getFlightNum())
                .bookingId(bookingFlightInfo.getBookingId())
                .routeId(bookingFlightInfo.getFlightInfo().getRouteId())
                .build();
    }

    @GetMapping(value = "/bookings/{bookId}", produces = "application/json")
    @ApiOperation(value = "Get book flight form database")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "return booked flight"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Server error", response = ErrorResponse.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "If the book id is not found.", response =
                    ErrorResponse.class),

    })
    public ResponseEntity<BookingFlightsResponse> getBooking(@PathVariable String bookId) {
        BookingInfo bookingInfo = bookingFlightsService.getBooking(bookId).orElseThrow(() -> new BookingFlightsException(ERROR_MESSAGE,
                HttpStatus.NOT_FOUND));

        return ResponseEntity.status(HttpStatus.OK).body(BookingFlightsResponse.builder()
                .origin(bookingInfo.getOrigin())
                .flightNumber(bookingInfo.getFlightNum())
                .numOfTransit(bookingInfo.getNumOfTransits())
                .destination(bookingInfo.getDestination())
                .flightDate(bookingInfo.getFlightDate())
                .bookingId(bookingInfo.getBookingId())
                .routeId(bookingInfo.getRouteId())
                .build());
    }

}
