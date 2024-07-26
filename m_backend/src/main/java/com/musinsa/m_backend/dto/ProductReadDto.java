package com.musinsa.m_backend.dto;

import lombok.*;

import java.util.List;

public class ProductReadDto {

    @Getter
    @ToString
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductReadListResponse{
        private Boolean result;
        private String reason;
        private List<ProductReadDto.ProductReadListItem> productList;
    }

    @Getter
    @ToString
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductReadDetailResponse{
        private Boolean result;
        private String reason;
        private ProductReadDto.ProductReadDetailItem productDetail;
    }

    @Getter
    @ToString
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductReadDetailItem {
        private Long brandIdx;
        private String brandName;
        private Long categoryIdx;
        private String categoryName;
        private Long productIdx;
        private String productName;
        private Double productPrice;
        private String productImage;
        private String productDesc;
    }

    @Getter
    @ToString
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductReadListItem {
        private String brandName;
        private String categoryName;
        private Long productIdx;
        private String productName;
        private Double productPrice;
        private String productImage;
    }

}
