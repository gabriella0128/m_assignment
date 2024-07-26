package com.musinsa.m_backend.dto;

import lombok.*;

import java.util.List;

public class CategoryReadDto {
    @Getter
    @ToString
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryReadListSelectResponse{
        private Boolean result;
        private String reason;
        private List<CategoryReadDto.CategoryReadSelectItem> categorySelectList;
    }

    @Getter
    @ToString
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryReadSelectItem{
        private Long categoryIdx;
        private String categoryName;
    }
}
