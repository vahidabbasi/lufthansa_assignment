package com.lufthansa.bookingflights.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingFlightInfo {
    private FlightInfo flightInfo;
    private String bookingId;
}