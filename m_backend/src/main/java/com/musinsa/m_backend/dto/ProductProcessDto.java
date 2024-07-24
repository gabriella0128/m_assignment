package com.musinsa.m_backend.dto;

import lombok.*;

public class ProductProcessDto {

    @Getter
    @ToString
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductUpsertRequest {
        private Long productIdx;
        private String productName;
        private String productDesc;
        private Double productPrice;
        private String productImage;
        private Long brandIdx;
        private Long categoryIdx;
    }

    @Getter
    @ToString
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductDeleteRequest {
        private Long productIdx;
    }

    @Getter
    @ToString
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductProcessResponse {
        private Boolean result;
        private String reason;
    }
}
