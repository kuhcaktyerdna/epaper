package com.example.epaper.service.impl;

import com.example.epaper.exception.NotFoundException;
import com.example.epaper.model.Newspaper;
import com.example.epaper.model.dto.SearchParamsDto;
import com.example.epaper.repository.NewspaperRepository;
import com.example.epaper.service.NewspaperService;
import com.example.epaper.xsd.App;
import com.example.epaper.xsd.Epaper;
import com.example.epaper.xsd.Screen;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * {@inheritDoc}
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NewspaperServiceImpl implements NewspaperService {

    private static final String NOT_FOUND_MSG = "No newspaper found for id %s";

    private final NewspaperRepository newspaperRepository;
    private final EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public Newspaper create(final Epaper epaper, final String filename) {
        log.debug("Persisting entity from file {}: {}", filename, epaper);
        final Screen screenInfo = epaper.getDeviceInfo().getScreenInfo();
        final App appInfo = epaper.getDeviceInfo().getAppInfo();
        final Newspaper newspaper = Newspaper
                .builder()
                .newspaperName(appInfo.getNewspaperName())
                .deviceHeight(screenInfo.getHeight())
                .deviceWidth(screenInfo.getWidth())
                .deviceDpi(screenInfo.getDpi())
                .filename(filename)
                .uploadTime(LocalDateTime.now())
                .build();
        return newspaperRepository.save(newspaper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Newspaper> findAll() {
        return newspaperRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Newspaper> findBy(final SearchParamsDto searchParams) {
        log.debug("Search for the entity with params: {}", searchParams);
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Newspaper> criteriaQuery = criteriaBuilder.createQuery(Newspaper.class);
        final Root<Newspaper> root = criteriaQuery.from(Newspaper.class);
        final List<Predicate> predicates = new ArrayList<>();

        Optional.ofNullable(searchParams.getFilter())
                .ifPresent(filter ->
                        predicates.add(criteriaBuilder.equal(root.get(filter.getParamName()), filter.getParamValue())));

        Optional.ofNullable(searchParams.getSorting())
                .ifPresent(sorting -> {
                    final Path sortingProp = root.get(sorting.getProp());
                    final Order order = "ASC".equals(sorting.getDirection())
                            ? criteriaBuilder.asc(sortingProp)
                            : criteriaBuilder.desc(sortingProp);
                    criteriaQuery.orderBy(order);
                });

        criteriaQuery
                .select(root)
                .where(predicates.toArray(new Predicate[]{}));

        final TypedQuery<Newspaper> query = entityManager.createQuery(criteriaQuery);

        Optional
                .ofNullable(searchParams.getPagination())
                .ifPresent(pagination -> {
                    query.setFirstResult(pagination.getPageNumber() * pagination.getPageSize());
                    query.setMaxResults(pagination.getPageSize());
                });

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Newspaper getById(final Long id) {
        log.debug("Getting the entity with id: {}", id);
        return newspaperRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_MSG, id)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(final Long id) {
        log.debug("Deleting the entity with id: {}", id);
        try {
            newspaperRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(String.format(NOT_FOUND_MSG, id));
        }
    }


}
