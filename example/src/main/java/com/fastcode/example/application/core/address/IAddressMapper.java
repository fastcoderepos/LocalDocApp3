package com.fastcode.example.application.core.address;

import com.fastcode.example.application.core.address.dto.*;
import com.fastcode.example.domain.core.address.AddressEntity;
import com.fastcode.example.domain.core.city.CityEntity;
import java.time.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface IAddressMapper {
    AddressEntity createAddressInputToAddressEntity(CreateAddressInput addressDto);

    @Mappings(
        {
            @Mapping(source = "entity.city.cityId", target = "cityId"),
            @Mapping(source = "entity.city.cityId", target = "cityDescriptiveField"),
        }
    )
    CreateAddressOutput addressEntityToCreateAddressOutput(AddressEntity entity);

    AddressEntity updateAddressInputToAddressEntity(UpdateAddressInput addressDto);

    @Mappings(
        {
            @Mapping(source = "entity.city.cityId", target = "cityId"),
            @Mapping(source = "entity.city.cityId", target = "cityDescriptiveField"),
        }
    )
    UpdateAddressOutput addressEntityToUpdateAddressOutput(AddressEntity entity);

    @Mappings(
        {
            @Mapping(source = "entity.city.cityId", target = "cityId"),
            @Mapping(source = "entity.city.cityId", target = "cityDescriptiveField"),
        }
    )
    FindAddressByIdOutput addressEntityToFindAddressByIdOutput(AddressEntity entity);

    @Mappings(
        {
            @Mapping(source = "city.city", target = "city"),
            @Mapping(source = "city.lastUpdate", target = "lastUpdate"),
            @Mapping(source = "foundAddress.addressId", target = "addressAddressId"),
        }
    )
    GetCityOutput cityEntityToGetCityOutput(CityEntity city, AddressEntity foundAddress);
}
