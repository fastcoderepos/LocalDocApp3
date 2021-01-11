package com.fastcode.example.application.core.category;

import com.fastcode.example.application.core.category.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.domain.core.category.CategoryEntity;
import com.fastcode.example.domain.core.category.ICategoryRepository;
import com.fastcode.example.domain.core.category.QCategoryEntity;
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

@Service("categoryAppService")
@RequiredArgsConstructor
public class CategoryAppService implements ICategoryAppService {

    @Qualifier("categoryRepository")
    @NonNull
    protected final ICategoryRepository _categoryRepository;

    @Qualifier("ICategoryMapperImpl")
    @NonNull
    protected final ICategoryMapper mapper;

    @NonNull
    protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateCategoryOutput create(CreateCategoryInput input) {
        CategoryEntity category = mapper.createCategoryInputToCategoryEntity(input);

        CategoryEntity createdCategory = _categoryRepository.save(category);
        return mapper.categoryEntityToCreateCategoryOutput(createdCategory);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateCategoryOutput update(Integer categoryId, UpdateCategoryInput input) {
        CategoryEntity category = mapper.updateCategoryInputToCategoryEntity(input);

        CategoryEntity updatedCategory = _categoryRepository.save(category);
        return mapper.categoryEntityToUpdateCategoryOutput(updatedCategory);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Integer categoryId) {
        CategoryEntity existing = _categoryRepository.findById(categoryId).orElse(null);
        _categoryRepository.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindCategoryByIdOutput findById(Integer categoryId) {
        CategoryEntity foundCategory = _categoryRepository.findById(categoryId).orElse(null);
        if (foundCategory == null) return null;

        return mapper.categoryEntityToFindCategoryByIdOutput(foundCategory);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindCategoryByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<CategoryEntity> foundCategory = _categoryRepository.findAll(search(search), pageable);
        List<CategoryEntity> categoryList = foundCategory.getContent();
        Iterator<CategoryEntity> categoryIterator = categoryList.iterator();
        List<FindCategoryByIdOutput> output = new ArrayList<>();

        while (categoryIterator.hasNext()) {
            CategoryEntity category = categoryIterator.next();
            output.add(mapper.categoryEntityToFindCategoryByIdOutput(category));
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QCategoryEntity category = QCategoryEntity.categoryEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(category, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    list.get(i).replace("%20", "").trim().equals("categoryId") ||
                    list.get(i).replace("%20", "").trim().equals("lastUpdate") ||
                    list.get(i).replace("%20", "").trim().equals("name")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QCategoryEntity category,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("categoryId")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(category.categoryId.eq(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(category.categoryId.ne(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        category.categoryId.between(
                            Integer.valueOf(details.getValue().getStartingValue()),
                            Integer.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        category.categoryId.goe(Integer.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        category.categoryId.loe(Integer.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("lastUpdate")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    category.lastUpdate.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    category.lastUpdate.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (details.getValue().getOperator().equals("range")) {
                    LocalDateTime startLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getStartingValue()
                    );
                    LocalDateTime endLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getEndingValue()
                    );
                    if (startLocalDateTime != null && endLocalDateTime != null) builder.and(
                        category.lastUpdate.between(startLocalDateTime, endLocalDateTime)
                    ); else if (endLocalDateTime != null) builder.and(
                        category.lastUpdate.loe(endLocalDateTime)
                    ); else if (startLocalDateTime != null) builder.and(category.lastUpdate.goe(startLocalDateTime));
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("name")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    category.name.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    category.name.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    category.name.ne(details.getValue().getSearchValue())
                );
            }
        }

        return builder;
    }

    public Map<String, String> parseFilmCategorysJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("categoryId", keysString);

        return joinColumnMap;
    }
}
