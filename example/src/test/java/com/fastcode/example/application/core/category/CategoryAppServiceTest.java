package com.fastcode.example.application.core.category;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.example.application.core.category.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.domain.core.category.*;
import com.fastcode.example.domain.core.category.CategoryEntity;
import com.fastcode.example.domain.core.category.QCategoryEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class CategoryAppServiceTest {

    @InjectMocks
    @Spy
    protected CategoryAppService _appService;

    @Mock
    protected ICategoryRepository _categoryRepository;

    @Mock
    protected ICategoryMapper _mapper;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    protected static Integer ID = 15;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_appService);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findCategoryById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Optional<CategoryEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_categoryRepository.findById(any(Integer.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.findById(ID)).isEqualTo(null);
    }

    @Test
    public void findCategoryById_IdIsNotNullAndIdExists_ReturnCategory() {
        CategoryEntity category = mock(CategoryEntity.class);
        Optional<CategoryEntity> categoryOptional = Optional.of((CategoryEntity) category);
        Mockito.when(_categoryRepository.findById(any(Integer.class))).thenReturn(categoryOptional);

        Assertions
            .assertThat(_appService.findById(ID))
            .isEqualTo(_mapper.categoryEntityToFindCategoryByIdOutput(category));
    }

    @Test
    public void createCategory_CategoryIsNotNullAndCategoryDoesNotExist_StoreCategory() {
        CategoryEntity categoryEntity = mock(CategoryEntity.class);
        CreateCategoryInput categoryInput = new CreateCategoryInput();

        Mockito
            .when(_mapper.createCategoryInputToCategoryEntity(any(CreateCategoryInput.class)))
            .thenReturn(categoryEntity);
        Mockito.when(_categoryRepository.save(any(CategoryEntity.class))).thenReturn(categoryEntity);

        Assertions
            .assertThat(_appService.create(categoryInput))
            .isEqualTo(_mapper.categoryEntityToCreateCategoryOutput(categoryEntity));
    }

    @Test
    public void updateCategory_CategoryIdIsNotNullAndIdExists_ReturnUpdatedCategory() {
        CategoryEntity categoryEntity = mock(CategoryEntity.class);
        UpdateCategoryInput category = mock(UpdateCategoryInput.class);

        Mockito
            .when(_mapper.updateCategoryInputToCategoryEntity(any(UpdateCategoryInput.class)))
            .thenReturn(categoryEntity);
        Mockito.when(_categoryRepository.save(any(CategoryEntity.class))).thenReturn(categoryEntity);
        Assertions
            .assertThat(_appService.update(ID, category))
            .isEqualTo(_mapper.categoryEntityToUpdateCategoryOutput(categoryEntity));
    }

    @Test
    public void deleteCategory_CategoryIsNotNullAndCategoryExists_CategoryRemoved() {
        CategoryEntity category = mock(CategoryEntity.class);
        Optional<CategoryEntity> categoryOptional = Optional.of((CategoryEntity) category);
        Mockito.when(_categoryRepository.findById(any(Integer.class))).thenReturn(categoryOptional);

        _appService.delete(ID);
        verify(_categoryRepository).delete(category);
    }

    @Test
    public void find_ListIsEmpty_ReturnList() throws Exception {
        List<CategoryEntity> list = new ArrayList<>();
        Page<CategoryEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindCategoryByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_categoryRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void find_ListIsNotEmpty_ReturnList() throws Exception {
        List<CategoryEntity> list = new ArrayList<>();
        CategoryEntity category = mock(CategoryEntity.class);
        list.add(category);
        Page<CategoryEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindCategoryByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");
        output.add(_mapper.categoryEntityToFindCategoryByIdOutput(category));

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_categoryRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
        QCategoryEntity category = QCategoryEntity.categoryEntity;
        SearchFields searchFields = new SearchFields();
        searchFields.setOperator("equals");
        searchFields.setSearchValue("xyz");
        Map<String, SearchFields> map = new HashMap<>();
        map.put("name", searchFields);
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("xyz", String.valueOf(ID));
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(category.name.eq("xyz"));
        Assertions.assertThat(_appService.searchKeyValuePair(category, map, searchMap)).isEqualTo(builder);
    }

    @Test(expected = Exception.class)
    public void checkProperties_PropertyDoesNotExist_ThrowException() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("xyz");
        _appService.checkProperties(list);
    }

    @Test
    public void checkProperties_PropertyExists_ReturnNothing() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("name");
        _appService.checkProperties(list);
    }

    @Test
    public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
        Map<String, SearchFields> map = new HashMap<>();
        QCategoryEntity category = QCategoryEntity.categoryEntity;
        List<SearchFields> fieldsList = new ArrayList<>();
        SearchFields fields = new SearchFields();
        SearchCriteria search = new SearchCriteria();
        search.setType(3);
        search.setValue("xyz");
        search.setOperator("equals");
        fields.setFieldName("name");
        fields.setOperator("equals");
        fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
        BooleanBuilder builder = new BooleanBuilder();
        builder.or(category.name.eq("xyz"));
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
        Mockito
            .doReturn(builder)
            .when(_appService)
            .searchKeyValuePair(any(QCategoryEntity.class), any(HashMap.class), any(HashMap.class));

        Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
    }

    @Test
    public void search_StringIsNull_ReturnNull() throws Exception {
        Assertions.assertThat(_appService.search(null)).isEqualTo(null);
    }

    @Test
    public void ParsefilmCategorysJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        String keyString = "15";
        joinColumnMap.put("categoryId", keyString);
        Assertions.assertThat(_appService.parseFilmCategorysJoinColumn(keyString)).isEqualTo(joinColumnMap);
    }
}
