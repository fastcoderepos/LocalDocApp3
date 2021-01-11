package com.fastcode.example.application.extended.payment;

import com.fastcode.example.application.core.payment.IPaymentMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IPaymentMapperExtended extends IPaymentMapper {}
