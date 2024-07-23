package com.musinsa.m_backend.dto.base;

import lombok.*;

public class ProductDto {

    @Getter
    @ToString
    @Builder(toBuilder = true)
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Info {
        private Long productIdx;
        private String productName;
        private String productDesc;
        private String productImage;
        private Double productPrice;
        private Long brandIdx;
        private Long categoryIdx;
        private long version;
    }


}
