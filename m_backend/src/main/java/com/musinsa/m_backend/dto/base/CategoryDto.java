package com.musinsa.m_backend.dto.base;

import lombok.*;

public class CategoryDto {
    @Getter
    @ToString
    @Builder(toBuilder = true)
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Info {
        private Long categoryIdx;
        private String categoryName;
    }
}
