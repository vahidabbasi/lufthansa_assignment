package com.lufthansa.bookingflights.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class BookingInfo {
    private String bookingId;
    private int routeId;
    private String origin;
    private String destination;
    private String flightNum;
    private LocalDate flightDate;
    private int numOfTransits;
}