package com.fastcode.example.application.core.city;

import com.fastcode.example.application.core.city.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.domain.core.city.CityEntity;
import com.fastcode.example.domain.core.city.ICityRepository;
import com.fastcode.example.domain.core.city.QCityEntity;
import com.fastcode.example.domain.core.country.CountryEntity;
import com.fastcode.example.domain.core.country.ICountryRepository;
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

@Service("cityAppService")
@RequiredArgsConstructor
public class CityAppService implements ICityAppService {

    @Qualifier("cityRepository")
    @NonNull
    protected final ICityRepository _cityRepository;

    @Qualifier("countryRepository")
    @NonNull
    protected final ICountryRepository _countryRepository;

    @Qualifier("ICityMapperImpl")
    @NonNull
    protected final ICityMapper mapper;

    @NonNull
    protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateCityOutput create(CreateCityInput input) {
        CityEntity city = mapper.createCityInputToCityEntity(input);
        CountryEntity foundCountry = null;
        if (input.getCountryId() != null) {
            foundCountry = _countryRepository.findById(input.getCountryId()).orElse(null);

            if (foundCountry != null) {
                city.setCountry(foundCountry);
            } else {
                return null;
            }
        } else {
            return null;
        }

        CityEntity createdCity = _cityRepository.save(city);
        return mapper.cityEntityToCreateCityOutput(createdCity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateCityOutput update(Integer cityId, UpdateCityInput input) {
        CityEntity city = mapper.updateCityInputToCityEntity(input);
        CountryEntity foundCountry = null;

        if (input.getCountryId() != null) {
            foundCountry = _countryRepository.findById(input.getCountryId()).orElse(null);

            if (foundCountry != null) {
                city.setCountry(foundCountry);
            } else {
                return null;
            }
        } else {
            return null;
        }

        CityEntity updatedCity = _cityRepository.save(city);
        return mapper.cityEntityToUpdateCityOutput(updatedCity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Integer cityId) {
        CityEntity existing = _cityRepository.findById(cityId).orElse(null);
        _cityRepository.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindCityByIdOutput findById(Integer cityId) {
        CityEntity foundCity = _cityRepository.findById(cityId).orElse(null);
        if (foundCity == null) return null;

        return mapper.cityEntityToFindCityByIdOutput(foundCity);
    }

    //Country
    // ReST API Call - GET /city/1/country
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetCountryOutput getCountry(Integer cityId) {
        CityEntity foundCity = _cityRepository.findById(cityId).orElse(null);
        if (foundCity == null) {
            logHelper.getLogger().error("There does not exist a city wth a id=%s", cityId);
            return null;
        }
        CountryEntity re = foundCity.getCountry();
        return mapper.countryEntityToGetCountryOutput(re, foundCity);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindCityByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<CityEntity> foundCity = _cityRepository.findAll(search(search), pageable);
        List<CityEntity> cityList = foundCity.getContent();
        Iterator<CityEntity> cityIterator = cityList.iterator();
        List<FindCityByIdOutput> output = new ArrayList<>();

        while (cityIterator.hasNext()) {
            CityEntity city = cityIterator.next();
            output.add(mapper.cityEntityToFindCityByIdOutput(city));
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QCityEntity city = QCityEntity.cityEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(city, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    list.get(i).replace("%20", "").trim().equals("countryId") ||
                    list.get(i).replace("%20", "").trim().equals("city") ||
                    list.get(i).replace("%20", "").trim().equals("cityId") ||
                    list.get(i).replace("%20", "").trim().equals("lastUpdate")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QCityEntity city,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("city")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    city.city.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    city.city.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    city.city.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("cityId")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(city.cityId.eq(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(city.cityId.ne(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        city.cityId.between(
                            Integer.valueOf(details.getValue().getStartingValue()),
                            Integer.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        city.cityId.goe(Integer.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        city.cityId.loe(Integer.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("lastUpdate")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    city.lastUpdate.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    city.lastUpdate.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (details.getValue().getOperator().equals("range")) {
                    LocalDateTime startLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getStartingValue()
                    );
                    LocalDateTime endLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getEndingValue()
                    );
                    if (startLocalDateTime != null && endLocalDateTime != null) builder.and(
                        city.lastUpdate.between(startLocalDateTime, endLocalDateTime)
                    ); else if (endLocalDateTime != null) builder.and(city.lastUpdate.loe(endLocalDateTime)); else if (
                        startLocalDateTime != null
                    ) builder.and(city.lastUpdate.goe(startLocalDateTime));
                }
            }
        }

        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("countryId")) {
                builder.and(city.country.countryId.eq(Integer.parseInt(joinCol.getValue())));
            }
        }
        return builder;
    }

    public Map<String, String> parseAddressJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("cityId", keysString);

        return joinColumnMap;
    }
}
