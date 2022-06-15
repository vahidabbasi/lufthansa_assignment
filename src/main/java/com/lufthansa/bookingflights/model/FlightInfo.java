package com.lufthansa.bookingflights.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FlightInfo {
    private int routeId;
    private String flightNum;
}