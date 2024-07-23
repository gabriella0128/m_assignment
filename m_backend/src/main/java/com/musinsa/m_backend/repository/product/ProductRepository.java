package com.musinsa.m_backend.repository.product;

import com.musinsa.m_backend.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

}
