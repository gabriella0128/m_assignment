package com.musinsa.m_backend.repository.category;

import com.musinsa.m_backend.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Query("SELECT c FROM CategoryEntity c WHERE c.categoryIdx = :categoryIdx")
    Optional<CategoryEntity> findCategoryByCategoryIdx(@Param("categoryIdx") Long categoryIdx);

    @Query("SELECT c FROM CategoryEntity c WHERE c.categoryName = :categoryName")
    Optional<CategoryEntity> findCategoryByCategoryName(@Param("categoryName") String categoryName);

    //For Test Use Only
    // @Modifying(clearAutomatically = true, flushAutomatically = true)
    // @Query(value = "DELETE FROM CategoryEntity c WHERE c.categoryIdx = :categoryIdx")
    // void deleteCategoryCompletelyByCategoryIdx(@Param("categoryIdx") Long categoryIdx);
}
