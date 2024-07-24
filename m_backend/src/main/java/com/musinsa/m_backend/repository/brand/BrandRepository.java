package com.musinsa.m_backend.repository.brand;

import com.musinsa.m_backend.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<BrandEntity, Long> {


    @Query("SELECT b FROM BrandEntity b WHERE b.brandIdx = :brandIdx")
    Optional<BrandEntity> findBrandByBrandIdx(@Param("brandIdx") Long brandIdx);

    @Modifying
    @Query("UPDATE BrandEntity b SET b.useYn = false, b.deleteDt = CURRENT_TIMESTAMP WHERE b.brandIdx = :brandIdx")
    void deleteBrandByBrandIdx(@Param("brandIdx") Long brandIdx);
}
