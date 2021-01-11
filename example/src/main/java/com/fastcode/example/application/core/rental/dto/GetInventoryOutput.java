package com.fastcode.example.application.core.rental.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetInventoryOutput {

    private Integer inventoryId;
    private LocalDateTime lastUpdate;
    private Short storeId;
    private Integer rentalRentalId;
}
