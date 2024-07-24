package com.musinsa.m_backend.service.dtoService;

import com.musinsa.m_backend.dto.base.ProductDto;
import com.musinsa.m_backend.entity.ProductEntity;
import com.musinsa.m_backend.mapper.ProductMapper;
import com.musinsa.m_backend.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public ProductDto.Info findProductByProductIdx(Long productIdx) {
        return productMapper.toInfoDto(productRepository.findProductByProductIdx(productIdx).orElse(null));
    }

    @Transactional(readOnly = true)
    public List<ProductDto.Info> findProductByBrandIdx(Long brandIdx){
        return productMapper.toInfoDto(productRepository.findProductByBrandIdx(brandIdx));
    }

    @Transactional(readOnly = true)
    public ProductDto.Info findMaxPriceProductByCategoryIdx(Long categoryIdx){
        return productMapper.toInfoDto(productRepository.findMaxPriceProductByCategoryIdx(categoryIdx).orElse(null));
    }

    @Transactional(readOnly = true)
    public ProductDto.Info findMinPriceProductByCategoryIdx(Long categoryIdx){
        return productMapper.toInfoDto(productRepository.findMinPriceProductByCategoryIdx(categoryIdx).orElse(null));
    }

    @Transactional(readOnly = true)
    public Boolean existProductByBrandIdxAndCategoryIdx(Long brandIdx, Long categoryIdx){
        return productRepository.existsProductEntitiesByBrandIdxAndCategoryIdx(brandIdx, categoryIdx);
    }

    @Transactional(readOnly = true)
    public ProductDto.Info findMinPriceProductByBrandIdxAndCategoryIdx(Long brandIdx, Long categoryIdx){
        return productMapper.toInfoDto(productRepository.findMinPriceProductByBrandIdxAndCategoryIdx(brandIdx, categoryIdx).orElse(null));
    }

    @Transactional
    public ProductDto.Info save(ProductDto.Info product) {
        ProductEntity productEntity = productMapper.fromInfoToEntity(product);

        ProductEntity saved = productRepository.save(productEntity);

        return productMapper.toInfoDto(saved);
    }
}
