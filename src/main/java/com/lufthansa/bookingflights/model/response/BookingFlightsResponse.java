package com.lufthansa.bookingflights.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class BookingFlightsResponse {
    private String bookingId;
    private int routeId;
    private String origin;
    private String destination;
    private String flightNumber;
    private LocalDate flightDate;
    private int numOfTransit;
}
