package com.fastcode.example.application.core.customer;

import com.fastcode.example.application.core.customer.dto.*;
import com.fastcode.example.domain.core.address.AddressEntity;
import com.fastcode.example.domain.core.customer.CustomerEntity;
import java.time.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ICustomerMapper {
    CustomerEntity createCustomerInputToCustomerEntity(CreateCustomerInput customerDto);

    @Mappings(
        {
            @Mapping(source = "entity.address.addressId", target = "addressId"),
            @Mapping(source = "entity.address.addressId", target = "addressDescriptiveField"),
        }
    )
    CreateCustomerOutput customerEntityToCreateCustomerOutput(CustomerEntity entity);

    CustomerEntity updateCustomerInputToCustomerEntity(UpdateCustomerInput customerDto);

    @Mappings(
        {
            @Mapping(source = "entity.address.addressId", target = "addressId"),
            @Mapping(source = "entity.address.addressId", target = "addressDescriptiveField"),
        }
    )
    UpdateCustomerOutput customerEntityToUpdateCustomerOutput(CustomerEntity entity);

    @Mappings(
        {
            @Mapping(source = "entity.address.addressId", target = "addressId"),
            @Mapping(source = "entity.address.addressId", target = "addressDescriptiveField"),
        }
    )
    FindCustomerByIdOutput customerEntityToFindCustomerByIdOutput(CustomerEntity entity);

    @Mappings(
        {
            @Mapping(source = "address.address", target = "address"),
            @Mapping(source = "address.lastUpdate", target = "lastUpdate"),
            @Mapping(source = "foundCustomer.customerId", target = "customerCustomerId"),
        }
    )
    GetAddressOutput addressEntityToGetAddressOutput(AddressEntity address, CustomerEntity foundCustomer);
}
