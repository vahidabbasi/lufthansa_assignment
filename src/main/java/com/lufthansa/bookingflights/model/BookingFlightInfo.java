package com.lufthansa.bookingflights.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BookingFlightInfo {
    private FlightInfo flightInfo;
    private String bookingId;
}