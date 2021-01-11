package com.fastcode.example.application.core.payment.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetRentalOutput {

    private LocalDateTime lastUpdate;
    private LocalDateTime rentalDate;
    private Integer rentalId;
    private LocalDateTime returnDate;
    private Integer paymentPaymentId;
}
