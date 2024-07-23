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
@Table(name = "product")
@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("use_yn = true")
public class ProductEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_idx")
    private Long productIdx;

    @JoinColumn(name = "brand_idx")
    @ManyToOne(fetch = FetchType.LAZY)
    private BrandEntity brand;

    @JoinColumn(name = "category_idx")
    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryEntity category;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_desc")
    private String productDesc;

    @Column(name = "product_price")
    private Double productPrice;

    @Column(name = "product_image")
    private String productImage;


}
