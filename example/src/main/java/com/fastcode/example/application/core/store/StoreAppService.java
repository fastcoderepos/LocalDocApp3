package com.fastcode.example.application.core.store;

import com.fastcode.example.application.core.store.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.domain.core.address.AddressEntity;
import com.fastcode.example.domain.core.address.IAddressRepository;
import com.fastcode.example.domain.core.staff.IStaffRepository;
import com.fastcode.example.domain.core.staff.StaffEntity;
import com.fastcode.example.domain.core.store.IStoreRepository;
import com.fastcode.example.domain.core.store.QStoreEntity;
import com.fastcode.example.domain.core.store.StoreEntity;
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

@Service("storeAppService")
@RequiredArgsConstructor
public class StoreAppService implements IStoreAppService {

    @Qualifier("storeRepository")
    @NonNull
    protected final IStoreRepository _storeRepository;

    @Qualifier("addressRepository")
    @NonNull
    protected final IAddressRepository _addressRepository;

    @Qualifier("staffRepository")
    @NonNull
    protected final IStaffRepository _staffRepository;

    @Qualifier("IStoreMapperImpl")
    @NonNull
    protected final IStoreMapper mapper;

    @NonNull
    protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateStoreOutput create(CreateStoreInput input) {
        StoreEntity store = mapper.createStoreInputToStoreEntity(input);
        AddressEntity foundAddress = null;
        StaffEntity foundStaff = null;
        if (input.getAddressId() != null) {
            foundAddress = _addressRepository.findById(input.getAddressId()).orElse(null);

            if (foundAddress != null) {
                store.setAddress(foundAddress);
            } else {
                return null;
            }
        } else {
            return null;
        }
        if (input.getManagerStaffId() != null) {
            foundStaff = _staffRepository.findById(input.getManagerStaffId()).orElse(null);

            if (foundStaff != null) {
                store.setStaff(foundStaff);
            } else {
                return null;
            }
        } else {
            return null;
        }

        StoreEntity createdStore = _storeRepository.save(store);
        return mapper.storeEntityToCreateStoreOutput(createdStore);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateStoreOutput update(Integer storeId, UpdateStoreInput input) {
        StoreEntity store = mapper.updateStoreInputToStoreEntity(input);
        AddressEntity foundAddress = null;
        StaffEntity foundStaff = null;

        if (input.getAddressId() != null) {
            foundAddress = _addressRepository.findById(input.getAddressId()).orElse(null);

            if (foundAddress != null) {
                store.setAddress(foundAddress);
            } else {
                return null;
            }
        } else {
            return null;
        }

        if (input.getManagerStaffId() != null) {
            foundStaff = _staffRepository.findById(input.getManagerStaffId()).orElse(null);

            if (foundStaff != null) {
                store.setStaff(foundStaff);
            } else {
                return null;
            }
        } else {
            return null;
        }

        StoreEntity updatedStore = _storeRepository.save(store);
        return mapper.storeEntityToUpdateStoreOutput(updatedStore);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Integer storeId) {
        StoreEntity existing = _storeRepository.findById(storeId).orElse(null);
        _storeRepository.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindStoreByIdOutput findById(Integer storeId) {
        StoreEntity foundStore = _storeRepository.findById(storeId).orElse(null);
        if (foundStore == null) return null;

        return mapper.storeEntityToFindStoreByIdOutput(foundStore);
    }

    //Address
    // ReST API Call - GET /store/1/address
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetAddressOutput getAddress(Integer storeId) {
        StoreEntity foundStore = _storeRepository.findById(storeId).orElse(null);
        if (foundStore == null) {
            logHelper.getLogger().error("There does not exist a store wth a id=%s", storeId);
            return null;
        }
        AddressEntity re = foundStore.getAddress();
        return mapper.addressEntityToGetAddressOutput(re, foundStore);
    }

    //Staff
    // ReST API Call - GET /store/1/staff
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetStaffOutput getStaff(Integer storeId) {
        StoreEntity foundStore = _storeRepository.findById(storeId).orElse(null);
        if (foundStore == null) {
            logHelper.getLogger().error("There does not exist a store wth a id=%s", storeId);
            return null;
        }
        StaffEntity re = foundStore.getStaff();
        return mapper.staffEntityToGetStaffOutput(re, foundStore);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindStoreByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<StoreEntity> foundStore = _storeRepository.findAll(search(search), pageable);
        List<StoreEntity> storeList = foundStore.getContent();
        Iterator<StoreEntity> storeIterator = storeList.iterator();
        List<FindStoreByIdOutput> output = new ArrayList<>();

        while (storeIterator.hasNext()) {
            StoreEntity store = storeIterator.next();
            output.add(mapper.storeEntityToFindStoreByIdOutput(store));
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QStoreEntity store = QStoreEntity.storeEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(store, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    list.get(i).replace("%20", "").trim().equals("addressId") ||
                    list.get(i).replace("%20", "").trim().equals("managerStaffId") ||
                    //       list.get(i).replace("%20","").trim().equals("staffId") ||
                    list.get(i).replace("%20", "").trim().equals("lastUpdate") ||
                    list.get(i).replace("%20", "").trim().equals("storeId")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QStoreEntity store,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("lastUpdate")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    store.lastUpdate.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    store.lastUpdate.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (details.getValue().getOperator().equals("range")) {
                    LocalDateTime startLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getStartingValue()
                    );
                    LocalDateTime endLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getEndingValue()
                    );
                    if (startLocalDateTime != null && endLocalDateTime != null) builder.and(
                        store.lastUpdate.between(startLocalDateTime, endLocalDateTime)
                    ); else if (endLocalDateTime != null) builder.and(store.lastUpdate.loe(endLocalDateTime)); else if (
                        startLocalDateTime != null
                    ) builder.and(store.lastUpdate.goe(startLocalDateTime));
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("storeId")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(store.storeId.eq(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(store.storeId.ne(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        store.storeId.between(
                            Integer.valueOf(details.getValue().getStartingValue()),
                            Integer.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        store.storeId.goe(Integer.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        store.storeId.loe(Integer.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }

            if (details.getKey().replace("%20", "").trim().equals("staff")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(store.staff.staffId.eq(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(store.staff.staffId.ne(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        store.staff.staffId.between(
                            Integer.valueOf(details.getValue().getStartingValue()),
                            Integer.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        store.staff.staffId.goe(Integer.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        store.staff.staffId.loe(Integer.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
        }

        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("addressId")) {
                builder.and(store.address.addressId.eq(Integer.parseInt(joinCol.getValue())));
            }
        }
        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("managerStaffId")) {
                builder.and(store.staff.staffId.eq(Integer.parseInt(joinCol.getValue())));
            }

            if (joinCol != null && joinCol.getKey().equals("staff")) {
                builder.and(store.staff.staffId.eq(Integer.parseInt(joinCol.getValue())));
            }
        }
        return builder;
    }
}
