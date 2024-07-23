package com.musinsa.m_backend.dto.base;

import lombok.*;

import java.time.LocalDateTime;

public class BrandDto {
    @Getter
    @ToString
    @Builder(toBuilder = true)
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Info {
        private Long brandIdx;
        private String brandName;
        private String brandDesc;
        private String brandImage;
        private Boolean useYn;
        private LocalDateTime deleteDt;
        private long version;

    }
}
