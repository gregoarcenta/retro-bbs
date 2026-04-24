package com.retrobbs.backend.infrastructure.adapters.in.rest.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private final String detail;
    private final Map<String, String> fields;
    private String code;

    public static ApiError of(String code, String detail) {
        return ApiError.builder()
                .code(code)
                .detail(detail)
                .build();
    }

    public static ApiError withFields(String detail, Map<String, String> fields) {
        return ApiError.builder()
                .code("VALIDATION_ERROR")
                .detail(detail)
                .fields(fields)
                .build();
    }
}