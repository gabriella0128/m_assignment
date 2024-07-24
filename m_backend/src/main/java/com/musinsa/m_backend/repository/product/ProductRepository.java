package com.musinsa.m_backend.repository.product;

import com.musinsa.m_backend.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Query("SELECT p FROM ProductEntity p WHERE p.productIdx = :productIdx")
    Optional<ProductEntity> findProductByProductIdx(@Param("productIdx") Long productIdx);

    @Query("SELECT p FROM ProductEntity p WHERE p.brand.brandIdx = :brandIdx")
    List<ProductEntity> findProductByBrandIdx(@Param("brandIdx") Long brandIdx);

    @Query("SELECT p FROM ProductEntity p WHERE p.category.categoryIdx = :categoryIdx ORDER BY p.productPrice DESC LIMIT 1")
    Optional<ProductEntity> findMaxPriceProductByCategoryIdx(@Param("categoryIdx") Long categoryIdx);

    @Query("SELECT p FROM ProductEntity p WHERE p.category.categoryIdx = :categoryIdx ORDER BY p.productPrice ASC LIMIT 1")
    Optional<ProductEntity> findMinPriceProductByCategoryIdx(@Param("categoryIdx") Long categoryIdx);

}
