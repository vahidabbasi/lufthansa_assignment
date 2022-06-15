package com.lufthansa.bookingflights.repository;

import com.lufthansa.bookingflights.model.BookingInfo;
import com.lufthansa.bookingflights.model.FlightInfo;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.time.LocalDate;
import java.util.Optional;

public interface BookingFlightsDAO {
    @SqlUpdate("INSERT INTO BOOKING_INFO(BOOKING_ID, ROUTE_ID, STATUS) VALUES(?, ?, ?)")
    void bookFlight(String bookingId, int routeId, String status);

    @SqlQuery("SELECT ROUTE_ID, FLIGHT_NUM from ROUTES_INFO where ORIGIN= :origin and DESTINATION= :destination and " +
            "FLIGHT_DATE= " +
            ":flightDate and NUM_OF_TRANSITS= :numOfTransits and Price BETWEEN :minPrice AND :maxPrice")
    @RegisterConstructorMapper(FlightInfo.class)
    Optional<FlightInfo> getFlightInfo(String origin, String destination, LocalDate flightDate, int numOfTransits,
                                      double minPrice, double maxPrice);

    @SqlQuery("SELECT BOOKING_ID, BI.ROUTE_ID, ORIGIN, DESTINATION, FLIGHT_NUM, FLIGHT_DATE, NUM_OF_TRANSITS" +
            " from ROUTES_INFO RI JOIN BOOKING_INFO BI ON BI.ROUTE_ID = RI.ROUTE_ID WHERE BOOKING_ID = :bookId" )
    @RegisterConstructorMapper(BookingInfo.class)
    Optional<BookingInfo> getBookingInfo(String bookId);

    @SqlUpdate("UPDATE BOOKING_INFO SET ROUTE_ID= :routeId WHERE BOOKING_ID= :bookId")
    void updateFlightRoute(String bookId, int routeId);

    @SqlQuery("SELECT COUNT(*) FROM BOOKING_INFO WHERE BOOKING_ID = :bookingId")
    int isBookingIdExist(String bookingId);
}
