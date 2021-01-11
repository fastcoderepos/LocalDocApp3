package com.fastcode.example.application.core.country;

import com.fastcode.example.application.core.country.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.domain.core.country.CountryEntity;
import com.fastcode.example.domain.core.country.ICountryRepository;
import com.fastcode.example.domain.core.country.QCountryEntity;
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

@Service("countryAppService")
@RequiredArgsConstructor
public class CountryAppService implements ICountryAppService {

    @Qualifier("countryRepository")
    @NonNull
    protected final ICountryRepository _countryRepository;

    @Qualifier("ICountryMapperImpl")
    @NonNull
    protected final ICountryMapper mapper;

    @NonNull
    protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateCountryOutput create(CreateCountryInput input) {
        CountryEntity country = mapper.createCountryInputToCountryEntity(input);

        CountryEntity createdCountry = _countryRepository.save(country);
        return mapper.countryEntityToCreateCountryOutput(createdCountry);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateCountryOutput update(Integer countryId, UpdateCountryInput input) {
        CountryEntity country = mapper.updateCountryInputToCountryEntity(input);

        CountryEntity updatedCountry = _countryRepository.save(country);
        return mapper.countryEntityToUpdateCountryOutput(updatedCountry);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Integer countryId) {
        CountryEntity existing = _countryRepository.findById(countryId).orElse(null);
        _countryRepository.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindCountryByIdOutput findById(Integer countryId) {
        CountryEntity foundCountry = _countryRepository.findById(countryId).orElse(null);
        if (foundCountry == null) return null;

        return mapper.countryEntityToFindCountryByIdOutput(foundCountry);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindCountryByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<CountryEntity> foundCountry = _countryRepository.findAll(search(search), pageable);
        List<CountryEntity> countryList = foundCountry.getContent();
        Iterator<CountryEntity> countryIterator = countryList.iterator();
        List<FindCountryByIdOutput> output = new ArrayList<>();

        while (countryIterator.hasNext()) {
            CountryEntity country = countryIterator.next();
            output.add(mapper.countryEntityToFindCountryByIdOutput(country));
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QCountryEntity country = QCountryEntity.countryEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(country, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    list.get(i).replace("%20", "").trim().equals("country") ||
                    list.get(i).replace("%20", "").trim().equals("countryId") ||
                    list.get(i).replace("%20", "").trim().equals("lastUpdate")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QCountryEntity country,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("country")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    country.country.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    country.country.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    country.country.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("countryId")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(country.countryId.eq(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(country.countryId.ne(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        country.countryId.between(
                            Integer.valueOf(details.getValue().getStartingValue()),
                            Integer.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        country.countryId.goe(Integer.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        country.countryId.loe(Integer.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("lastUpdate")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    country.lastUpdate.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    country.lastUpdate.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (details.getValue().getOperator().equals("range")) {
                    LocalDateTime startLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getStartingValue()
                    );
                    LocalDateTime endLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getEndingValue()
                    );
                    if (startLocalDateTime != null && endLocalDateTime != null) builder.and(
                        country.lastUpdate.between(startLocalDateTime, endLocalDateTime)
                    ); else if (endLocalDateTime != null) builder.and(
                        country.lastUpdate.loe(endLocalDateTime)
                    ); else if (startLocalDateTime != null) builder.and(country.lastUpdate.goe(startLocalDateTime));
                }
            }
        }

        return builder;
    }

    public Map<String, String> parseCitysJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("countryId", keysString);

        return joinColumnMap;
    }
}
