package com.fastcode.example.application.core.filmactor;

import com.fastcode.example.application.core.filmactor.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.domain.core.actor.ActorEntity;
import com.fastcode.example.domain.core.actor.IActorRepository;
import com.fastcode.example.domain.core.film.FilmEntity;
import com.fastcode.example.domain.core.film.IFilmRepository;
import com.fastcode.example.domain.core.filmactor.FilmActorEntity;
import com.fastcode.example.domain.core.filmactor.FilmActorId;
import com.fastcode.example.domain.core.filmactor.IFilmActorRepository;
import com.fastcode.example.domain.core.filmactor.QFilmActorEntity;
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

@Service("filmActorAppService")
@RequiredArgsConstructor
public class FilmActorAppService implements IFilmActorAppService {

    @Qualifier("filmActorRepository")
    @NonNull
    protected final IFilmActorRepository _filmActorRepository;

    @Qualifier("actorRepository")
    @NonNull
    protected final IActorRepository _actorRepository;

    @Qualifier("filmRepository")
    @NonNull
    protected final IFilmRepository _filmRepository;

    @Qualifier("IFilmActorMapperImpl")
    @NonNull
    protected final IFilmActorMapper mapper;

    @NonNull
    protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateFilmActorOutput create(CreateFilmActorInput input) {
        FilmActorEntity filmActor = mapper.createFilmActorInputToFilmActorEntity(input);
        ActorEntity foundActor = null;
        FilmEntity foundFilm = null;
        if (input.getActorId() != null) {
            foundActor = _actorRepository.findById(input.getActorId()).orElse(null);

            if (foundActor != null) {
                filmActor.setActor(foundActor);
            }
        }
        if (input.getFilmId() != null) {
            foundFilm = _filmRepository.findById(input.getFilmId()).orElse(null);

            if (foundFilm != null) {
                filmActor.setFilm(foundFilm);
            }
        }

        FilmActorEntity createdFilmActor = _filmActorRepository.save(filmActor);
        return mapper.filmActorEntityToCreateFilmActorOutput(createdFilmActor);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateFilmActorOutput update(FilmActorId filmActorId, UpdateFilmActorInput input) {
        FilmActorEntity filmActor = mapper.updateFilmActorInputToFilmActorEntity(input);
        ActorEntity foundActor = null;
        FilmEntity foundFilm = null;

        if (input.getActorId() != null) {
            foundActor = _actorRepository.findById(input.getActorId()).orElse(null);

            if (foundActor != null) {
                filmActor.setActor(foundActor);
            }
        }

        if (input.getFilmId() != null) {
            foundFilm = _filmRepository.findById(input.getFilmId()).orElse(null);

            if (foundFilm != null) {
                filmActor.setFilm(foundFilm);
            }
        }

        FilmActorEntity updatedFilmActor = _filmActorRepository.save(filmActor);
        return mapper.filmActorEntityToUpdateFilmActorOutput(updatedFilmActor);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(FilmActorId filmActorId) {
        FilmActorEntity existing = _filmActorRepository.findById(filmActorId).orElse(null);
        _filmActorRepository.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindFilmActorByIdOutput findById(FilmActorId filmActorId) {
        FilmActorEntity foundFilmActor = _filmActorRepository.findById(filmActorId).orElse(null);
        if (foundFilmActor == null) return null;

        return mapper.filmActorEntityToFindFilmActorByIdOutput(foundFilmActor);
    }

    //Actor
    // ReST API Call - GET /filmActor/1/actor
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetActorOutput getActor(FilmActorId filmActorId) {
        FilmActorEntity foundFilmActor = _filmActorRepository.findById(filmActorId).orElse(null);
        if (foundFilmActor == null) {
            logHelper.getLogger().error("There does not exist a filmActor wth a id=%s", filmActorId);
            return null;
        }
        ActorEntity re = foundFilmActor.getActor();
        return mapper.actorEntityToGetActorOutput(re, foundFilmActor);
    }

    //Film
    // ReST API Call - GET /filmActor/1/film
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetFilmOutput getFilm(FilmActorId filmActorId) {
        FilmActorEntity foundFilmActor = _filmActorRepository.findById(filmActorId).orElse(null);
        if (foundFilmActor == null) {
            logHelper.getLogger().error("There does not exist a filmActor wth a id=%s", filmActorId);
            return null;
        }
        FilmEntity re = foundFilmActor.getFilm();
        return mapper.filmEntityToGetFilmOutput(re, foundFilmActor);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindFilmActorByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<FilmActorEntity> foundFilmActor = _filmActorRepository.findAll(search(search), pageable);
        List<FilmActorEntity> filmActorList = foundFilmActor.getContent();
        Iterator<FilmActorEntity> filmActorIterator = filmActorList.iterator();
        List<FindFilmActorByIdOutput> output = new ArrayList<>();

        while (filmActorIterator.hasNext()) {
            FilmActorEntity filmActor = filmActorIterator.next();
            output.add(mapper.filmActorEntityToFindFilmActorByIdOutput(filmActor));
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QFilmActorEntity filmActor = QFilmActorEntity.filmActorEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(filmActor, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    list.get(i).replace("%20", "").trim().equals("actorId") ||
                    list.get(i).replace("%20", "").trim().equals("filmId") ||
                    list.get(i).replace("%20", "").trim().equals("lastUpdate")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QFilmActorEntity filmActor,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("actorId")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(filmActor.actorId.eq(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(filmActor.actorId.ne(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        filmActor.actorId.between(
                            Integer.valueOf(details.getValue().getStartingValue()),
                            Integer.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        filmActor.actorId.goe(Integer.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        filmActor.actorId.loe(Integer.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("filmId")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(filmActor.filmId.eq(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(filmActor.filmId.ne(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        filmActor.filmId.between(
                            Integer.valueOf(details.getValue().getStartingValue()),
                            Integer.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        filmActor.filmId.goe(Integer.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        filmActor.filmId.loe(Integer.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("lastUpdate")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    filmActor.lastUpdate.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    filmActor.lastUpdate.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (details.getValue().getOperator().equals("range")) {
                    LocalDateTime startLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getStartingValue()
                    );
                    LocalDateTime endLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getEndingValue()
                    );
                    if (startLocalDateTime != null && endLocalDateTime != null) builder.and(
                        filmActor.lastUpdate.between(startLocalDateTime, endLocalDateTime)
                    ); else if (endLocalDateTime != null) builder.and(
                        filmActor.lastUpdate.loe(endLocalDateTime)
                    ); else if (startLocalDateTime != null) builder.and(filmActor.lastUpdate.goe(startLocalDateTime));
                }
            }
        }

        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("actorId")) {
                builder.and(filmActor.actor.actorId.eq(Integer.parseInt(joinCol.getValue())));
            }
        }
        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("filmId")) {
                builder.and(filmActor.film.filmId.eq(Integer.parseInt(joinCol.getValue())));
            }
        }
        return builder;
    }

    public FilmActorId parseFilmActorKey(String keysString) {
        String[] keyEntries = keysString.split(",");
        FilmActorId filmActorId = new FilmActorId();

        Map<String, String> keyMap = new HashMap<String, String>();
        if (keyEntries.length > 1) {
            for (String keyEntry : keyEntries) {
                String[] keyEntryArr = keyEntry.split("=");
                if (keyEntryArr.length > 1) {
                    keyMap.put(keyEntryArr[0], keyEntryArr[1]);
                } else {
                    return null;
                }
            }
        } else {
            String[] keyEntryArr = keysString.split("=");
            if (keyEntryArr.length > 1) {
                keyMap.put(keyEntryArr[0], keyEntryArr[1]);
            } else return null;
        }

        filmActorId.setActorId(Integer.valueOf(keyMap.get("actorId")));
        filmActorId.setFilmId(Integer.valueOf(keyMap.get("filmId")));
        return filmActorId;
    }
}
