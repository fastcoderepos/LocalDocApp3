package com.fastcode.example.application.core.inventory.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateInventoryOutput {

    private Integer inventoryId;
    private LocalDateTime lastUpdate;
    private Short storeId;
    private Integer filmId;
    private Integer filmDescriptiveField;
}
