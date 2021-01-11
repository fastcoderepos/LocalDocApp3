package com.fastcode.example.domain.core.rental;

import com.fastcode.example.domain.core.abstractentity.AbstractEntity;
import com.fastcode.example.domain.core.customer.CustomerEntity;
import com.fastcode.example.domain.core.inventory.InventoryEntity;
import com.fastcode.example.domain.core.staff.StaffEntity;
import java.time.*;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "RENTAL")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class RentalEntity extends AbstractEntity {

    @Basic
    @Column(name = "RENTAL_DATE", nullable = false)
    private LocalDateTime rentalDate;

    @Basic
    @Column(name = "RETURN_DATE", nullable = true)
    private LocalDateTime returnDate;

    @Basic
    @Column(name = "LAST_UPDATE", nullable = false)
    private LocalDateTime lastUpdate;

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "RENTAL_ID", nullable = false)
    private Integer rentalId;

    @ManyToOne
    @JoinColumn(name = "STAFF_ID")
    private StaffEntity staff;

    @ManyToOne
    @JoinColumn(name = "INVENTORY_ID")
    private InventoryEntity inventory;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private CustomerEntity customer;
}
