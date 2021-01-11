package com.fastcode.example.domain.core.customer;

import com.fastcode.example.domain.core.abstractentity.AbstractEntity;
import com.fastcode.example.domain.core.address.AddressEntity;
import java.time.*;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CUSTOMER")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class CustomerEntity extends AbstractEntity {

    @Basic
    @Column(name = "LAST_NAME", nullable = false, length = 50)
    private String lastName;

    @Basic
    @Column(name = "active", nullable = true)
    private Integer active;

    @Basic
    @Column(name = "activebool", nullable = false)
    private Boolean activebool;

    @Basic
    @Column(name = "STORE_ID", nullable = true)
    private Short storeId;

    @Basic
    @Column(name = "FIRST_NAME", nullable = false, length = 50)
    private String firstName;

    @Basic
    @Column(name = "LAST_UPDATE", nullable = true)
    private LocalDateTime lastUpdate;

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "CUSTOMER_ID", nullable = false)
    private Integer customerId;

    @Basic
    @Column(name = "email", nullable = true, length = 50)
    private String email;

    @Basic
    @Column(name = "CREATE_DATE", nullable = false)
    private LocalDate createDate;

    @ManyToOne
    @JoinColumn(name = "ADDRESS_ID")
    private AddressEntity address;
}
