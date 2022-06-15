package com.lufthansa.bookingflights.model.response;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {
    @ApiModelProperty(value = "An optional message that may detail the error.", example = "Please enter right value " +
            "with correct format")
    private String message;
}
