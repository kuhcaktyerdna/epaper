package com.example.epaper.model.dto;

import lombok.Data;

/**
 * DTO for representing filtering, sorting and paging params
 */
@Data
public class SearchParamsDto {

    private Filter filter;
    private Sorting sorting;
    private Pagination pagination;

    @Data
    public static class Sorting {
        private String prop;
        private String direction;
    }

    @Data
    public static class Pagination {
        private int pageNumber;
        private int pageSize;
    }

    @Data
    public static class Filter {
        private String paramName;
        private String paramValue;
    }

}

