package com.fastcode.example.application.core.address;

import com.fastcode.example.application.core.address.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.domain.core.address.AddressEntity;
import com.fastcode.example.domain.core.address.IAddressRepository;
import com.fastcode.example.domain.core.address.QAddressEntity;
import com.fastcode.example.domain.core.city.CityEntity;
import com.fastcode.example.domain.core.city.ICityRepository;
import com.querydsl.core.BooleanBuilder;
import java.time.*;
import java.util.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("addressAppService")
@RequiredArgsConstructor
public class AddressAppService implements IAddressAppService {

    @Qualifier("addressRepository")
    @NonNull
    protected final IAddressRepository _addressRepository;

    @Qualifier("cityRepository")
    @NonNull
    protected final ICityRepository _cityRepository;

    @Qualifier("IAddressMapperImpl")
    @NonNull
    protected final IAddressMapper mapper;

    @NonNull
    protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateAddressOutput create(CreateAddressInput input) {
        AddressEntity address = mapper.createAddressInputToAddressEntity(input);
        CityEntity foundCity = null;
        if (input.getCityId() != null) {
            foundCity = _cityRepository.findById(input.getCityId()).orElse(null);

            if (foundCity != null) {
                address.setCity(foundCity);
            } else {
                return null;
            }
        } else {
            return null;
        }

        AddressEntity createdAddress = _addressRepository.save(address);
        return mapper.addressEntityToCreateAddressOutput(createdAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateAddressOutput update(Integer addressId, UpdateAddressInput input) {
        AddressEntity address = mapper.updateAddressInputToAddressEntity(input);
        CityEntity foundCity = null;

        if (input.getCityId() != null) {
            foundCity = _cityRepository.findById(input.getCityId()).orElse(null);

            if (foundCity != null) {
                address.setCity(foundCity);
            } else {
                return null;
            }
        } else {
            return null;
        }

        AddressEntity updatedAddress = _addressRepository.save(address);
        return mapper.addressEntityToUpdateAddressOutput(updatedAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Integer addressId) {
        AddressEntity existing = _addressRepository.findById(addressId).orElse(null);
        _addressRepository.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindAddressByIdOutput findById(Integer addressId) {
        AddressEntity foundAddress = _addressRepository.findById(addressId).orElse(null);
        if (foundAddress == null) return null;

        return mapper.addressEntityToFindAddressByIdOutput(foundAddress);
    }

    //City
    // ReST API Call - GET /address/1/city
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetCityOutput getCity(Integer addressId) {
        AddressEntity foundAddress = _addressRepository.findById(addressId).orElse(null);
        if (foundAddress == null) {
            logHelper.getLogger().error("There does not exist a address wth a id=%s", addressId);
            return null;
        }
        CityEntity re = foundAddress.getCity();
        return mapper.cityEntityToGetCityOutput(re, foundAddress);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindAddressByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<AddressEntity> foundAddress = _addressRepository.findAll(search(search), pageable);
        List<AddressEntity> addressList = foundAddress.getContent();
        Iterator<AddressEntity> addressIterator = addressList.iterator();
        List<FindAddressByIdOutput> output = new ArrayList<>();

        while (addressIterator.hasNext()) {
            AddressEntity address = addressIterator.next();
            output.add(mapper.addressEntityToFindAddressByIdOutput(address));
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QAddressEntity address = QAddressEntity.addressEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(address, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    list.get(i).replace("%20", "").trim().equals("cityId") ||
                    list.get(i).replace("%20", "").trim().equals("address") ||
                    list.get(i).replace("%20", "").trim().equals("address2") ||
                    list.get(i).replace("%20", "").trim().equals("addressId") ||
                    list.get(i).replace("%20", "").trim().equals("district") ||
                    list.get(i).replace("%20", "").trim().equals("lastUpdate") ||
                    list.get(i).replace("%20", "").trim().equals("phone") ||
                    list.get(i).replace("%20", "").trim().equals("postalCode")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QAddressEntity address,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("address")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    address.address.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    address.address.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    address.address.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("address2")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    address.address2.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    address.address2.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    address.address2.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("addressId")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(address.addressId.eq(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(address.addressId.ne(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        address.addressId.between(
                            Integer.valueOf(details.getValue().getStartingValue()),
                            Integer.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        address.addressId.goe(Integer.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        address.addressId.loe(Integer.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("district")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    address.district.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    address.district.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    address.district.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("lastUpdate")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    address.lastUpdate.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    address.lastUpdate.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (details.getValue().getOperator().equals("range")) {
                    LocalDateTime startLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getStartingValue()
                    );
                    LocalDateTime endLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getEndingValue()
                    );
                    if (startLocalDateTime != null && endLocalDateTime != null) builder.and(
                        address.lastUpdate.between(startLocalDateTime, endLocalDateTime)
                    ); else if (endLocalDateTime != null) builder.and(
                        address.lastUpdate.loe(endLocalDateTime)
                    ); else if (startLocalDateTime != null) builder.and(address.lastUpdate.goe(startLocalDateTime));
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("phone")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    address.phone.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    address.phone.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    address.phone.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("postalCode")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    address.postalCode.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    address.postalCode.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    address.postalCode.ne(details.getValue().getSearchValue())
                );
            }
        }

        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("cityId")) {
                builder.and(address.city.cityId.eq(Integer.parseInt(joinCol.getValue())));
            }
        }
        return builder;
    }

    public Map<String, String> parseCustomersJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("addressId", keysString);

        return joinColumnMap;
    }

    public Map<String, String> parseStaffsJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("addressId", keysString);

        return joinColumnMap;
    }

    public Map<String, String> parseStoresJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("addressId", keysString);

        return joinColumnMap;
    }
}
