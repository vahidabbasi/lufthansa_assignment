package com.lufthansa.bookingflights.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingInfo {
    private String bookingId;
    private int routeId;
    private String origin;
    private String destination;
    private String flightNum;
    private LocalDate flightDate;
    private int numOfTransits;
}