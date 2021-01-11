package com.fastcode.example.domain.core.payment;

import java.time.*;
import java.util.*;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("paymentRepository")
public interface IPaymentRepository
    extends JpaRepository<PaymentEntity, Integer>, QuerydslPredicateExecutor<PaymentEntity> {}
