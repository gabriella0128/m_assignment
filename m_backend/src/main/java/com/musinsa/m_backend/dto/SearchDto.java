package com.musinsa.m_backend.dto;

import lombok.*;

import java.util.List;

public class SearchDto {
    @Getter
    @ToString
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchCheapestProductsPerCategoryResponse {
        private Boolean result;
        private String reason;

        List<CheapestProductsPerCategoryItem> items;
        private Double totalPrice;

    }
    @Getter
    @ToString
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheapestProductsPerCategoryItem {
        private Long categoryIdx;
        private String categoryName;
        private Long brandIdx;
        private String brandName;
        private Long productIdx;
        private String productName;
        private Double productPrice;
    }

    @Getter
    @ToString
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchHighestAndLowestPriceProductsForCategoryResponse {
        private Boolean result;
        private String reason;

        private Long categoryIdx;
        private String categoryName;
        private HighestOrLowestPriceItem highestPriceItem;
        private HighestOrLowestPriceItem lowestPriceItem;

    }
    @Getter
    @ToString
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HighestOrLowestPriceItem {
        private Long brandIdx;
        private String brandName;
        private Long productIdx;
        private String productName;
        private Double productPrice;
    }


    @Getter
    @ToString
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchCheapestCodiBrandResponse {
        private Boolean result;
        private String reason;

        private Long brandIdx;
        private String brandName;
        List<CheapestCodiBrandItem> items;
        private Double totalPrice;

    }

    @Getter
    @ToString
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheapestCodiBrandItem {
        private Long categoryIdx;
        private String categoryName;
        private Long productIdx;
        private String productName;
        private String productDesc;
        private String productImage;
        private Double productPrice;

    }

}
