package com.example.epaper.model.dto;

import com.example.epaper.model.Newspaper;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * DTO for passing {@link Newspaper}s to client
 */
@Data
@Builder
public class NewspaperResponseDto {

    private List<Newspaper> data;
    private int count;

}
