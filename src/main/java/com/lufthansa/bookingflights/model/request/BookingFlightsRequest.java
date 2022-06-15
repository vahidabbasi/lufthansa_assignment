package com.lufthansa.bookingflights.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingFlightsRequest {

	@NotNull
	@ApiModelProperty(required = true)
	@Size(min = 3, message="must be at least {min} characters")
	@Size(max = 3, message="must be less than {max} characters")
	private String origin;

	@NotNull
	@ApiModelProperty(required = true)
	@Size(min = 3, message="must be at least {min} characters")
	@Size(max = 3, message="must be less than {max} characters")
	private String destination;

	@NotNull
	@ApiModelProperty(required = true)
	private LocalDate flightDate;

	@ApiModelProperty(required = true)
	@Min(value = 0, message = "need zero or positive integer")
	private int numOfTransits;

	@ApiModelProperty(required = true)
	@Min(value = 0, message = "need zero or positive double")
	private double minPrice;

	@ApiModelProperty(required = true)
	@Min(value = 0, message = "need zero or positive double")
	private double maxPrice;
}
