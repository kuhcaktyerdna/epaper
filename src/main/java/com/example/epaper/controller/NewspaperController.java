package com.example.epaper.controller;

import com.example.epaper.exception.InvalidRequestBodyException;
import com.example.epaper.model.Newspaper;
import com.example.epaper.model.dto.NewspaperResponseDto;
import com.example.epaper.model.dto.SearchParamsDto;
import com.example.epaper.service.JaxbParseService;
import com.example.epaper.service.NewspaperService;
import com.example.epaper.xsd.Epaper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

/**
 * API controller for managing {@link Newspaper}s
 */
@RestController
@RequestMapping("/newspaper")
@RequiredArgsConstructor
public class NewspaperController {

    private final JaxbParseService jaxbParseService;
    private final NewspaperService newspaperService;

    /**
     * Creates a new entity of {@link Newspaper} based on data from XML file
     *
     * @param file stores information about {@link Newspaper} to create
     * @return created entity
     */
    @PostMapping
    public ResponseEntity<Newspaper> create(@RequestBody final MultipartFile file) {
        try {
            jaxbParseService.validate(new ByteArrayInputStream(file.getBytes()));
            final Epaper epaper = jaxbParseService.parseFromFile(new ByteArrayInputStream(file.getBytes()));
            final Newspaper newspaper = newspaperService.create(epaper, file.getOriginalFilename());
            return ResponseEntity.ok(newspaper);
        } catch (IOException | JAXBException e) {
            throw new InvalidRequestBodyException("Invalid file provided");
        }
    }

    /**
     * Returns all {@link Newspaper}s available
     *
     * @return all entities from DB
     */
    @GetMapping("/all")
    public ResponseEntity<NewspaperResponseDto> findAll() {
        final List<Newspaper> all = newspaperService.findAll();
        final NewspaperResponseDto newspaperResponseDto = NewspaperResponseDto.builder()
                .data(all)
                .count(all.size())
                .build();
        return ResponseEntity.ok(newspaperResponseDto);
    }

    /**
     * Retrieves a specific {@link Newspaper} by id
     *
     * @param id of {@link Newspaper}
     * @return corresponding {@link Newspaper}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Newspaper> getById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(newspaperService.getById(id));
    }

    /**
     * Retrieves {@link Newspaper}s based on softing, paging and filtering params
     *
     * @param searchParams stores parameters for retrieving
     * @return a {@link List} of {@link Newspaper}s
     */
    @GetMapping("/filtered")
    public ResponseEntity<NewspaperResponseDto> findAll(@RequestBody final SearchParamsDto searchParams) {
        final List<Newspaper> all = newspaperService.findBy(searchParams);
        final NewspaperResponseDto newspaperResponseDto = NewspaperResponseDto.builder()
                .data(all)
                .count(all.size())
                .build();
        return ResponseEntity.ok(newspaperResponseDto);
    }

    /**
     * Deletes {@link Newspaper} with corresponding id
     *
     * @param id of {@link Newspaper} to delete
     * @return {@link ResponseEntity<Void>}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") final Long id) {
        newspaperService.deleteById(id);
        return ResponseEntity
                .ok()
                .build();
    }

}
