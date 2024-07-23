package com.musinsa.m_backend.dto;

import lombok.*;

public class BrandProcessDto {

    @Getter
    @ToString
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BrandUpsertRequest {
        private Long brandIdx;
        private String brandName;
        private String brandImage;
        private String brandDesc;

    }

    @Getter
    @ToString
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BrandDeleteRequest {
        private Long brandIdx;
    }

    @Getter
    @ToString
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BrandProcessResponse {
        private Boolean result;
        private String reason;
    }
}
