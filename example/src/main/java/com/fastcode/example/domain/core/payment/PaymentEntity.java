package com.fastcode.example.domain.core.payment;

import com.fastcode.example.domain.core.abstractentity.AbstractEntity;
import com.fastcode.example.domain.core.customer.CustomerEntity;
import com.fastcode.example.domain.core.rental.RentalEntity;
import com.fastcode.example.domain.core.staff.StaffEntity;
import java.math.BigDecimal;
import java.time.*;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PAYMENT")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class PaymentEntity extends AbstractEntity {

    @Basic
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "PAYMENT_ID", nullable = false)
    private Integer paymentId;

    @Basic
    @Column(name = "PAYMENT_DATE", nullable = false)
    private LocalDateTime paymentDate;

    @ManyToOne
    @JoinColumn(name = "STAFF_ID")
    private StaffEntity staff;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "RENTAL_ID")
    private RentalEntity rental;
}
