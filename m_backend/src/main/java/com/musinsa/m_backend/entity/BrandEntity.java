package com.musinsa.m_backend.entity;

import com.musinsa.m_backend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;


@Entity
@Table(name = "brand")
@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("use_yn = true")
public class BrandEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_idx")
    private Long brandIdx;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "brand_desc")
    private String brandDesc;

    @Column(name = "brand_image")
    private String brandImage;



}
