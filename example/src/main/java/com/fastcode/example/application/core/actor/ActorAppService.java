package com.fastcode.example.application.core.actor;

import com.fastcode.example.application.core.actor.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.domain.core.actor.ActorEntity;
import com.fastcode.example.domain.core.actor.IActorRepository;
import com.fastcode.example.domain.core.actor.QActorEntity;
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

@Service("actorAppService")
@RequiredArgsConstructor
public class ActorAppService implements IActorAppService {

    @Qualifier("actorRepository")
    @NonNull
    protected final IActorRepository _actorRepository;

    @Qualifier("IActorMapperImpl")
    @NonNull
    protected final IActorMapper mapper;

    @NonNull
    protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateActorOutput create(CreateActorInput input) {
        ActorEntity actor = mapper.createActorInputToActorEntity(input);

        ActorEntity createdActor = _actorRepository.save(actor);
        return mapper.actorEntityToCreateActorOutput(createdActor);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateActorOutput update(Integer actorId, UpdateActorInput input) {
        ActorEntity actor = mapper.updateActorInputToActorEntity(input);

        ActorEntity updatedActor = _actorRepository.save(actor);
        return mapper.actorEntityToUpdateActorOutput(updatedActor);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Integer actorId) {
        ActorEntity existing = _actorRepository.findById(actorId).orElse(null);
        _actorRepository.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindActorByIdOutput findById(Integer actorId) {
        ActorEntity foundActor = _actorRepository.findById(actorId).orElse(null);
        if (foundActor == null) return null;

        return mapper.actorEntityToFindActorByIdOutput(foundActor);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindActorByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<ActorEntity> foundActor = _actorRepository.findAll(search(search), pageable);
        List<ActorEntity> actorList = foundActor.getContent();
        Iterator<ActorEntity> actorIterator = actorList.iterator();
        List<FindActorByIdOutput> output = new ArrayList<>();

        while (actorIterator.hasNext()) {
            ActorEntity actor = actorIterator.next();
            output.add(mapper.actorEntityToFindActorByIdOutput(actor));
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QActorEntity actor = QActorEntity.actorEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(actor, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    list.get(i).replace("%20", "").trim().equals("actorId") ||
                    list.get(i).replace("%20", "").trim().equals("firstName") ||
                    list.get(i).replace("%20", "").trim().equals("lastName") ||
                    list.get(i).replace("%20", "").trim().equals("lastUpdate")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QActorEntity actor,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("actorId")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(actor.actorId.eq(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(actor.actorId.ne(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        actor.actorId.between(
                            Integer.valueOf(details.getValue().getStartingValue()),
                            Integer.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        actor.actorId.goe(Integer.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        actor.actorId.loe(Integer.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("firstName")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    actor.firstName.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    actor.firstName.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    actor.firstName.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("lastName")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    actor.lastName.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    actor.lastName.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    actor.lastName.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("lastUpdate")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    actor.lastUpdate.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    actor.lastUpdate.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (details.getValue().getOperator().equals("range")) {
                    LocalDateTime startLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getStartingValue()
                    );
                    LocalDateTime endLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getEndingValue()
                    );
                    if (startLocalDateTime != null && endLocalDateTime != null) builder.and(
                        actor.lastUpdate.between(startLocalDateTime, endLocalDateTime)
                    ); else if (endLocalDateTime != null) builder.and(actor.lastUpdate.loe(endLocalDateTime)); else if (
                        startLocalDateTime != null
                    ) builder.and(actor.lastUpdate.goe(startLocalDateTime));
                }
            }
        }

        return builder;
    }

    public Map<String, String> parseFilmActorsJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("actorId", keysString);

        return joinColumnMap;
    }
}
