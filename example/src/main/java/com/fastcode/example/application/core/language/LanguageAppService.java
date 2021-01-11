package com.fastcode.example.application.core.language;

import com.fastcode.example.application.core.language.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.domain.core.language.ILanguageRepository;
import com.fastcode.example.domain.core.language.LanguageEntity;
import com.fastcode.example.domain.core.language.QLanguageEntity;
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

@Service("languageAppService")
@RequiredArgsConstructor
public class LanguageAppService implements ILanguageAppService {

    @Qualifier("languageRepository")
    @NonNull
    protected final ILanguageRepository _languageRepository;

    @Qualifier("ILanguageMapperImpl")
    @NonNull
    protected final ILanguageMapper mapper;

    @NonNull
    protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateLanguageOutput create(CreateLanguageInput input) {
        LanguageEntity language = mapper.createLanguageInputToLanguageEntity(input);

        LanguageEntity createdLanguage = _languageRepository.save(language);
        return mapper.languageEntityToCreateLanguageOutput(createdLanguage);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateLanguageOutput update(Integer languageId, UpdateLanguageInput input) {
        LanguageEntity language = mapper.updateLanguageInputToLanguageEntity(input);

        LanguageEntity updatedLanguage = _languageRepository.save(language);
        return mapper.languageEntityToUpdateLanguageOutput(updatedLanguage);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Integer languageId) {
        LanguageEntity existing = _languageRepository.findById(languageId).orElse(null);
        _languageRepository.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindLanguageByIdOutput findById(Integer languageId) {
        LanguageEntity foundLanguage = _languageRepository.findById(languageId).orElse(null);
        if (foundLanguage == null) return null;

        return mapper.languageEntityToFindLanguageByIdOutput(foundLanguage);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindLanguageByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<LanguageEntity> foundLanguage = _languageRepository.findAll(search(search), pageable);
        List<LanguageEntity> languageList = foundLanguage.getContent();
        Iterator<LanguageEntity> languageIterator = languageList.iterator();
        List<FindLanguageByIdOutput> output = new ArrayList<>();

        while (languageIterator.hasNext()) {
            LanguageEntity language = languageIterator.next();
            output.add(mapper.languageEntityToFindLanguageByIdOutput(language));
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QLanguageEntity language = QLanguageEntity.languageEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(language, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    list.get(i).replace("%20", "").trim().equals("languageId") ||
                    list.get(i).replace("%20", "").trim().equals("lastUpdate") ||
                    list.get(i).replace("%20", "").trim().equals("name")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QLanguageEntity language,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("languageId")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(language.languageId.eq(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(language.languageId.ne(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        language.languageId.between(
                            Integer.valueOf(details.getValue().getStartingValue()),
                            Integer.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        language.languageId.goe(Integer.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        language.languageId.loe(Integer.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("lastUpdate")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    language.lastUpdate.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    language.lastUpdate.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (details.getValue().getOperator().equals("range")) {
                    LocalDateTime startLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getStartingValue()
                    );
                    LocalDateTime endLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getEndingValue()
                    );
                    if (startLocalDateTime != null && endLocalDateTime != null) builder.and(
                        language.lastUpdate.between(startLocalDateTime, endLocalDateTime)
                    ); else if (endLocalDateTime != null) builder.and(
                        language.lastUpdate.loe(endLocalDateTime)
                    ); else if (startLocalDateTime != null) builder.and(language.lastUpdate.goe(startLocalDateTime));
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("name")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    language.name.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    language.name.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    language.name.ne(details.getValue().getSearchValue())
                );
            }
        }

        return builder;
    }

    public Map<String, String> parseFilmsJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("languageId", keysString);

        return joinColumnMap;
    }
}
