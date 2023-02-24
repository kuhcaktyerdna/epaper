package com.example.epaper.model.dto;

import lombok.Builder;
import lombok.Data;

/**
 * DTO for error messages
 */
@Data
@Builder
public class ErrorResponseDto {

    private String message;

}
