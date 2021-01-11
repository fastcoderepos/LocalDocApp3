package com.fastcode.example.application.core.film;

import com.fastcode.example.application.core.film.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.domain.core.film.FilmEntity;
import com.fastcode.example.domain.core.film.IFilmRepository;
import com.fastcode.example.domain.core.film.QFilmEntity;
import com.fastcode.example.domain.core.language.ILanguageRepository;
import com.fastcode.example.domain.core.language.LanguageEntity;
import com.querydsl.core.BooleanBuilder;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("filmAppService")
@RequiredArgsConstructor
public class FilmAppService implements IFilmAppService {

    @Qualifier("filmRepository")
    @NonNull
    protected final IFilmRepository _filmRepository;

    @Qualifier("languageRepository")
    @NonNull
    protected final ILanguageRepository _languageRepository;

    @Qualifier("IFilmMapperImpl")
    @NonNull
    protected final IFilmMapper mapper;

    @NonNull
    protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateFilmOutput create(CreateFilmInput input) {
        FilmEntity film = mapper.createFilmInputToFilmEntity(input);
        LanguageEntity foundLanguage = null;
        if (input.getLanguageId() != null) {
            foundLanguage = _languageRepository.findById(input.getLanguageId()).orElse(null);

            if (foundLanguage != null) {
                film.setLanguage(foundLanguage);
            } else {
                return null;
            }
        } else {
            return null;
        }

        FilmEntity createdFilm = _filmRepository.save(film);
        return mapper.filmEntityToCreateFilmOutput(createdFilm);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateFilmOutput update(Integer filmId, UpdateFilmInput input) {
        FilmEntity film = mapper.updateFilmInputToFilmEntity(input);
        LanguageEntity foundLanguage = null;

        if (input.getLanguageId() != null) {
            foundLanguage = _languageRepository.findById(input.getLanguageId()).orElse(null);

            if (foundLanguage != null) {
                film.setLanguage(foundLanguage);
            } else {
                return null;
            }
        } else {
            return null;
        }

        FilmEntity updatedFilm = _filmRepository.save(film);
        return mapper.filmEntityToUpdateFilmOutput(updatedFilm);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Integer filmId) {
        FilmEntity existing = _filmRepository.findById(filmId).orElse(null);
        _filmRepository.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindFilmByIdOutput findById(Integer filmId) {
        FilmEntity foundFilm = _filmRepository.findById(filmId).orElse(null);
        if (foundFilm == null) return null;

        return mapper.filmEntityToFindFilmByIdOutput(foundFilm);
    }

    //Language
    // ReST API Call - GET /film/1/language
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetLanguageOutput getLanguage(Integer filmId) {
        FilmEntity foundFilm = _filmRepository.findById(filmId).orElse(null);
        if (foundFilm == null) {
            logHelper.getLogger().error("There does not exist a film wth a id=%s", filmId);
            return null;
        }
        LanguageEntity re = foundFilm.getLanguage();
        return mapper.languageEntityToGetLanguageOutput(re, foundFilm);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindFilmByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<FilmEntity> foundFilm = _filmRepository.findAll(search(search), pageable);
        List<FilmEntity> filmList = foundFilm.getContent();
        Iterator<FilmEntity> filmIterator = filmList.iterator();
        List<FindFilmByIdOutput> output = new ArrayList<>();

        while (filmIterator.hasNext()) {
            FilmEntity film = filmIterator.next();
            output.add(mapper.filmEntityToFindFilmByIdOutput(film));
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QFilmEntity film = QFilmEntity.filmEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(film, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    list.get(i).replace("%20", "").trim().equals("languageId") ||
                    list.get(i).replace("%20", "").trim().equals("description") ||
                    list.get(i).replace("%20", "").trim().equals("filmId") ||
                    list.get(i).replace("%20", "").trim().equals("lastUpdate") ||
                    list.get(i).replace("%20", "").trim().equals("length") ||
                    list.get(i).replace("%20", "").trim().equals("rating") ||
                    list.get(i).replace("%20", "").trim().equals("releaseYear") ||
                    list.get(i).replace("%20", "").trim().equals("rentalDuration") ||
                    list.get(i).replace("%20", "").trim().equals("rentalRate") ||
                    list.get(i).replace("%20", "").trim().equals("replacementCost") ||
                    list.get(i).replace("%20", "").trim().equals("title")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QFilmEntity film,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("description")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    film.description.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    film.description.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    film.description.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("filmId")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(film.filmId.eq(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(film.filmId.ne(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        film.filmId.between(
                            Integer.valueOf(details.getValue().getStartingValue()),
                            Integer.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        film.filmId.goe(Integer.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        film.filmId.loe(Integer.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("lastUpdate")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    film.lastUpdate.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    film.lastUpdate.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (details.getValue().getOperator().equals("range")) {
                    LocalDateTime startLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getStartingValue()
                    );
                    LocalDateTime endLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getEndingValue()
                    );
                    if (startLocalDateTime != null && endLocalDateTime != null) builder.and(
                        film.lastUpdate.between(startLocalDateTime, endLocalDateTime)
                    ); else if (endLocalDateTime != null) builder.and(film.lastUpdate.loe(endLocalDateTime)); else if (
                        startLocalDateTime != null
                    ) builder.and(film.lastUpdate.goe(startLocalDateTime));
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("length")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(film.length.eq(Short.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(film.length.ne(Short.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        film.length.between(
                            Short.valueOf(details.getValue().getStartingValue()),
                            Short.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        film.length.goe(Short.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        film.length.loe(Short.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("rating")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    film.rating.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    film.rating.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    film.rating.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("releaseYear")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(film.releaseYear.eq(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(film.releaseYear.ne(Integer.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        film.releaseYear.between(
                            Integer.valueOf(details.getValue().getStartingValue()),
                            Integer.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        film.releaseYear.goe(Integer.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        film.releaseYear.loe(Integer.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("rentalDuration")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(film.rentalDuration.eq(Short.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(film.rentalDuration.ne(Short.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        film.rentalDuration.between(
                            Short.valueOf(details.getValue().getStartingValue()),
                            Short.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        film.rentalDuration.goe(Short.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        film.rentalDuration.loe(Short.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("rentalRate")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    NumberUtils.isCreatable(details.getValue().getSearchValue())
                ) builder.and(film.rentalRate.eq(new BigDecimal(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    NumberUtils.isCreatable(details.getValue().getSearchValue())
                ) builder.and(film.rentalRate.ne(new BigDecimal(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        NumberUtils.isCreatable(details.getValue().getStartingValue()) &&
                        NumberUtils.isCreatable(details.getValue().getEndingValue())
                    ) builder.and(
                        film.rentalRate.between(
                            new BigDecimal(details.getValue().getStartingValue()),
                            new BigDecimal(details.getValue().getEndingValue())
                        )
                    ); else if (NumberUtils.isCreatable(details.getValue().getStartingValue())) builder.and(
                        film.rentalRate.goe(new BigDecimal(details.getValue().getStartingValue()))
                    ); else if (NumberUtils.isCreatable(details.getValue().getEndingValue())) builder.and(
                        film.rentalRate.loe(new BigDecimal(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("replacementCost")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    NumberUtils.isCreatable(details.getValue().getSearchValue())
                ) builder.and(film.replacementCost.eq(new BigDecimal(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    NumberUtils.isCreatable(details.getValue().getSearchValue())
                ) builder.and(film.replacementCost.ne(new BigDecimal(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        NumberUtils.isCreatable(details.getValue().getStartingValue()) &&
                        NumberUtils.isCreatable(details.getValue().getEndingValue())
                    ) builder.and(
                        film.replacementCost.between(
                            new BigDecimal(details.getValue().getStartingValue()),
                            new BigDecimal(details.getValue().getEndingValue())
                        )
                    ); else if (NumberUtils.isCreatable(details.getValue().getStartingValue())) builder.and(
                        film.replacementCost.goe(new BigDecimal(details.getValue().getStartingValue()))
                    ); else if (NumberUtils.isCreatable(details.getValue().getEndingValue())) builder.and(
                        film.replacementCost.loe(new BigDecimal(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("title")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    film.title.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    film.title.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    film.title.ne(details.getValue().getSearchValue())
                );
            }
        }

        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("languageId")) {
                builder.and(film.language.languageId.eq(Integer.parseInt(joinCol.getValue())));
            }
        }
        return builder;
    }

    public Map<String, String> parseFilmActorsJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("filmId", keysString);

        return joinColumnMap;
    }

    public Map<String, String> parseFilmCategorysJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("filmId", keysString);

        return joinColumnMap;
    }

    public Map<String, String> parseInventorysJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("filmId", keysString);

        return joinColumnMap;
    }
}
