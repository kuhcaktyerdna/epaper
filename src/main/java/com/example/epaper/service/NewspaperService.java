package com.example.epaper.service;

import com.example.epaper.model.Newspaper;
import com.example.epaper.model.dto.SearchParamsDto;
import com.example.epaper.xsd.Epaper;

import java.util.List;

/**
 * Service for interacting with {@link Newspaper}s
 */
public interface NewspaperService {

    /**
     * Creates a new entity of {@link Newspaper} based on data from {@link Epaper} object
     *
     * @param epaper stores information about {@link Newspaper} to create
     * @return created entity
     */
    Newspaper create(final Epaper epaper, final String filename);

    /**
     * Returns all {@link Newspaper}s available
     *
     * @return all entities from DB
     */
    List<Newspaper> findAll();

    /**
     * Retrieves {@link Newspaper}s based on softing, paging and filtering params
     *
     * @param searchParams stores parameters for retrieving
     * @return a {@link List} of {@link Newspaper}s
     */
    List<Newspaper> findBy(final SearchParamsDto searchParams);

    /**
     * Retrieves a specific {@link Newspaper} by id
     *
     * @param id of {@link Newspaper}
     * @return corresponding {@link Newspaper}
     */
    Newspaper getById(final Long id);


    /**
     * Deletes {@link Newspaper} with corresponding id
     *
     * @param id of {@link Newspaper} to delete
     */
    void deleteById(final Long id);
}
