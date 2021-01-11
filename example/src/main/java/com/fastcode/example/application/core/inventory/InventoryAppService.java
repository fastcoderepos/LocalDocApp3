package com.fastcode.example.application.core.inventory;

import com.fastcode.example.application.core.inventory.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.domain.core.film.FilmEntity;
import com.fastcode.example.domain.core.film.IFilmRepository;
import com.fastcode.example.domain.core.inventory.IInventoryRepository;
import com.fastcode.example.domain.core.inventory.InventoryEntity;
import com.fastcode.example.domain.core.inventory.QInventoryEntity;
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

@Service("inventoryAppService")
@RequiredArgsConstructor
public class InventoryAppService implements IInventoryAppService {

    @Qualifier("inventoryRepository")
    @NonNull
    protected final IInventoryRepository _inventoryRepository;

    @Qualifier("filmRepository")
    @NonNull
    protected final IFilmRepository _filmRepository;

    @Qualifier("IInventoryMapperImpl")
    @NonNull
    protected final IInventoryMapper mapper;

    @NonNull
    protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateInventoryOutput create(CreateInventoryInput input) {
        InventoryEntity inventory = mapper.createInventoryInputToInventoryEntity(input);
        FilmEntity foundFilm = null;
        if (input.getFilmId() != null) {
            foundFilm = _filmRepository.findById(input.getFilmId()).orElse(null);

            if (foundFilm != null) {
                inventory.setFilm(foundFilm);
            } else {
                return null;
            }
        } else {
            return null;
        }

        InventoryEntity createdInventory = _inventoryRepository.save(inventory);
        return mapper.inventoryEntityToCreateInventoryOutput(createdInventory);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateInventoryOutput update(Integer inventoryId, UpdateInventoryInput input) {
        InventoryEntity inventory = mapper.updateInventoryInputToInventoryEntity(input);
        FilmEntity foundFilm = null;

        if (input.getFilmId() != null) {
            foundFilm = _filmRepository.findById(input.getFilmId()).orElse(null);

            if (foundFilm != null) {
                inventory.setFilm(foundFilm);
            } else {
                return null;
            }
        } else {
            return null;
        }

        InventoryEntity updatedInventory = _inventoryRepository.save(inventory);
        return mapper.inventoryEntityToUpdateInventoryOutput(updatedInventory);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Integer inventoryId) {
        InventoryEntity existing = _inventoryRepository.findById(inventoryId).orElse(null);
        _inventoryRepository.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindInventoryByIdOutput findById(Integer inventoryId) {
        InventoryEntity foundInventory = _inventoryRepository.findById(inventoryId).orElse(null);
        if (foundInventory == null) return null;

        return mapper.inventoryEntityToFindInventoryByIdOutput(foundInventory);
    }

    //Film
    // ReST API Call - GET /inventory/1/film
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetFilmOutput getFilm(Integer inventoryId) {
        InventoryEntity foundInventory = _inventoryRepository.findById(inventoryId).orElse(null);
        if (foundInventory == null) {
            logHelper.getLogger().error("There does not exist a inventory wth a id=%s", inventoryId);
            return null;
        }
        FilmEntity re = foundInventory.getFilm();
        return mapper.filmEntityToGetFilmOutput(re, foundInventory);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindInventoryByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<InventoryEntity> foundInventory = _inventoryRepository.findAll(search(search), pageable);
        List<InventoryEntity> inventoryList = foundInventory.getContent();
        Iterator<InventoryEntity> inventoryIterator = inventoryList.iterator();
        List<FindInventoryByIdOutput> output = new ArrayList<>();

        while (inventoryIterator.hasNext()) {
            InventoryEntity inventory = inventoryIterator.next();
            output.add(mapper.inventoryEntityToFindInventoryByIdOutput(inventory));
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QInventoryEntity inventory = QInventoryEntity.inventoryEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(inventory, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    list.get(i).replace("%20", "").trim().equals("filmId") ||
                    list.get(i).replace("%20", "").trim().equals("inventoryId") ||
                    list.get(i).replace("%20", "").trim().equals("lastUpdate") ||
                    list.get(i).replace("%20", "").trim().equals("storeId")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QInventoryEntity inventory,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("inventoryId")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(inventory.inventoryId.eq(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(inventory.inventoryId.ne(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        inventory.inventoryId.between(
                            Integer.valueOf(details.getValue().getStartingValue()),
                            Integer.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        inventory.inventoryId.goe(Integer.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        inventory.inventoryId.loe(Integer.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("lastUpdate")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    inventory.lastUpdate.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    inventory.lastUpdate.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (details.getValue().getOperator().equals("range")) {
                    LocalDateTime startLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getStartingValue()
                    );
                    LocalDateTime endLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getEndingValue()
                    );
                    if (startLocalDateTime != null && endLocalDateTime != null) builder.and(
                        inventory.lastUpdate.between(startLocalDateTime, endLocalDateTime)
                    ); else if (endLocalDateTime != null) builder.and(
                        inventory.lastUpdate.loe(endLocalDateTime)
                    ); else if (startLocalDateTime != null) builder.and(inventory.lastUpdate.goe(startLocalDateTime));
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("storeId")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(inventory.storeId.eq(Short.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(inventory.storeId.ne(Short.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        inventory.storeId.between(
                            Short.valueOf(details.getValue().getStartingValue()),
                            Short.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        inventory.storeId.goe(Short.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        inventory.storeId.loe(Short.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
        }

        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("filmId")) {
                builder.and(inventory.film.filmId.eq(Integer.parseInt(joinCol.getValue())));
            }
        }
        return builder;
    }

    public Map<String, String> parseRentalsJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("inventoryId", keysString);

        return joinColumnMap;
    }
}
