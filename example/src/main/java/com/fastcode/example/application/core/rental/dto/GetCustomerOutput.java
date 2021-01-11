package com.fastcode.example.application.core.rental.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCustomerOutput {

    private Integer active;
    private LocalDate createDate;
    private Integer customerId;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime lastUpdate;
    private Short storeId;
    private Integer rentalRentalId;
}
