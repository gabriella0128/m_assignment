package com.musinsa.m_backend.dto;

import lombok.*;

import java.util.List;

public class BrandReadDto {
    @Getter
    @ToString
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BrandReadListResponse{
        private Boolean result;
        private String reason;
        private List<BrandReadItem> brandList;
    }

    @Getter
    @ToString
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BrandReadListSelectResponse{
        private Boolean result;
        private String reason;
        private List<BrandReadSelectItem> brandSelectList;
    }

    @Getter
    @ToString
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BrandReadSelectItem{
        private Long brandIdx;
        private String brandName;
    }

    @Getter
    @ToString
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BrandReadDetailResponse{
        private Boolean result;
        private String reason;
        private BrandReadItem brandDetail;
    }

    @Getter
    @ToString
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BrandReadItem {
        private Long brandIdx;
        private String brandName;
        private String brandImage;
        private String brandDesc;
    }
}
