package com.fastcode.example.application.core.staff;

import com.fastcode.example.application.core.staff.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.domain.core.address.AddressEntity;
import com.fastcode.example.domain.core.address.IAddressRepository;
import com.fastcode.example.domain.core.staff.IStaffRepository;
import com.fastcode.example.domain.core.staff.QStaffEntity;
import com.fastcode.example.domain.core.staff.StaffEntity;
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

@Service("staffAppService")
@RequiredArgsConstructor
public class StaffAppService implements IStaffAppService {

    @Qualifier("staffRepository")
    @NonNull
    protected final IStaffRepository _staffRepository;

    @Qualifier("addressRepository")
    @NonNull
    protected final IAddressRepository _addressRepository;

    @Qualifier("IStaffMapperImpl")
    @NonNull
    protected final IStaffMapper mapper;

    @NonNull
    protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateStaffOutput create(CreateStaffInput input) {
        StaffEntity staff = mapper.createStaffInputToStaffEntity(input);
        AddressEntity foundAddress = null;
        if (input.getAddressId() != null) {
            foundAddress = _addressRepository.findById(input.getAddressId()).orElse(null);

            if (foundAddress != null) {
                staff.setAddress(foundAddress);
            } else {
                return null;
            }
        } else {
            return null;
        }

        StaffEntity createdStaff = _staffRepository.save(staff);
        return mapper.staffEntityToCreateStaffOutput(createdStaff);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateStaffOutput update(Integer staffId, UpdateStaffInput input) {
        StaffEntity staff = mapper.updateStaffInputToStaffEntity(input);
        AddressEntity foundAddress = null;

        if (input.getAddressId() != null) {
            foundAddress = _addressRepository.findById(input.getAddressId()).orElse(null);

            if (foundAddress != null) {
                staff.setAddress(foundAddress);
            } else {
                return null;
            }
        } else {
            return null;
        }

        StaffEntity updatedStaff = _staffRepository.save(staff);
        return mapper.staffEntityToUpdateStaffOutput(updatedStaff);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Integer staffId) {
        StaffEntity existing = _staffRepository.findById(staffId).orElse(null);
        _staffRepository.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindStaffByIdOutput findById(Integer staffId) {
        StaffEntity foundStaff = _staffRepository.findById(staffId).orElse(null);
        if (foundStaff == null) return null;

        return mapper.staffEntityToFindStaffByIdOutput(foundStaff);
    }

    //Address
    // ReST API Call - GET /staff/1/address
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetAddressOutput getAddress(Integer staffId) {
        StaffEntity foundStaff = _staffRepository.findById(staffId).orElse(null);
        if (foundStaff == null) {
            logHelper.getLogger().error("There does not exist a staff wth a id=%s", staffId);
            return null;
        }
        AddressEntity re = foundStaff.getAddress();
        return mapper.addressEntityToGetAddressOutput(re, foundStaff);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindStaffByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<StaffEntity> foundStaff = _staffRepository.findAll(search(search), pageable);
        List<StaffEntity> staffList = foundStaff.getContent();
        Iterator<StaffEntity> staffIterator = staffList.iterator();
        List<FindStaffByIdOutput> output = new ArrayList<>();

        while (staffIterator.hasNext()) {
            StaffEntity staff = staffIterator.next();
            output.add(mapper.staffEntityToFindStaffByIdOutput(staff));
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QStaffEntity staff = QStaffEntity.staffEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(staff, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    list.get(i).replace("%20", "").trim().equals("addressId") ||
                    list.get(i).replace("%20", "").trim().equals("active") ||
                    list.get(i).replace("%20", "").trim().equals("email") ||
                    list.get(i).replace("%20", "").trim().equals("firstName") ||
                    list.get(i).replace("%20", "").trim().equals("lastName") ||
                    list.get(i).replace("%20", "").trim().equals("lastUpdate") ||
                    list.get(i).replace("%20", "").trim().equals("password") ||
                    list.get(i).replace("%20", "").trim().equals("staffId") ||
                    list.get(i).replace("%20", "").trim().equals("storeId") ||
                    list.get(i).replace("%20", "").trim().equals("username")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QStaffEntity staff,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("active")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    (
                        details.getValue().getSearchValue().equalsIgnoreCase("true") ||
                        details.getValue().getSearchValue().equalsIgnoreCase("false")
                    )
                ) builder.and(staff.active.eq(Boolean.parseBoolean(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    (
                        details.getValue().getSearchValue().equalsIgnoreCase("true") ||
                        details.getValue().getSearchValue().equalsIgnoreCase("false")
                    )
                ) builder.and(staff.active.ne(Boolean.parseBoolean(details.getValue().getSearchValue())));
            }
            if (details.getKey().replace("%20", "").trim().equals("email")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    staff.email.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    staff.email.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    staff.email.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("firstName")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    staff.firstName.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    staff.firstName.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    staff.firstName.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("lastName")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    staff.lastName.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    staff.lastName.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    staff.lastName.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("lastUpdate")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    staff.lastUpdate.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    staff.lastUpdate.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (details.getValue().getOperator().equals("range")) {
                    LocalDateTime startLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getStartingValue()
                    );
                    LocalDateTime endLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getEndingValue()
                    );
                    if (startLocalDateTime != null && endLocalDateTime != null) builder.and(
                        staff.lastUpdate.between(startLocalDateTime, endLocalDateTime)
                    ); else if (endLocalDateTime != null) builder.and(staff.lastUpdate.loe(endLocalDateTime)); else if (
                        startLocalDateTime != null
                    ) builder.and(staff.lastUpdate.goe(startLocalDateTime));
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("password")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    staff.password.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    staff.password.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    staff.password.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("staffId")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(staff.staffId.eq(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(staff.staffId.ne(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        staff.staffId.between(
                            Integer.valueOf(details.getValue().getStartingValue()),
                            Integer.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        staff.staffId.goe(Integer.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        staff.staffId.loe(Integer.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("storeId")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(staff.storeId.eq(Short.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(staff.storeId.ne(Short.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        staff.storeId.between(
                            Short.valueOf(details.getValue().getStartingValue()),
                            Short.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        staff.storeId.goe(Short.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        staff.storeId.loe(Short.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("username")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    staff.username.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    staff.username.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    staff.username.ne(details.getValue().getSearchValue())
                );
            }
        }

        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("addressId")) {
                builder.and(staff.address.addressId.eq(Integer.parseInt(joinCol.getValue())));
            }
        }
        return builder;
    }

    public Map<String, String> parsePaymentsJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("staffId", keysString);

        return joinColumnMap;
    }

    public Map<String, String> parseRentalsJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("staffId", keysString);

        return joinColumnMap;
    }

    public Map<String, String> parseStoresJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("managerStaffId", keysString);

        return joinColumnMap;
    }
}
