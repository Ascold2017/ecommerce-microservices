package com.ecommerce.authservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL) // не выводить null-поля в JSON
public record ApiError(
        int status,
        String message,
        Map<String, String> fieldErrors, // заполняется только для ошибок валидации
        Instant timestamp
) {}