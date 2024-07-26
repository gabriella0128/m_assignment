package com.musinsa.m_backend.service;

import com.musinsa.m_backend.dto.ProductReadDto;
import com.musinsa.m_backend.dto.base.ProductDto;
import com.musinsa.m_backend.service.dtoService.BrandService;
import com.musinsa.m_backend.service.dtoService.CategoryService;
import com.musinsa.m_backend.service.dtoService.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductReadService {
    private final ProductService productService;
    private final BrandService brandService;
    private final CategoryService categoryService;

    public ProductReadDto.ProductReadListResponse readProductList() {
        try {
            List<ProductDto.Info> productList = productService.findAll();
            List<ProductReadDto.ProductReadListItem> productItemList = productList.stream()
                    .map(product -> ProductReadDto.ProductReadListItem.builder()
                            .productIdx(product.getProductIdx())
                            .productName(product.getProductName())
                            .productPrice(product.getProductPrice())
                            .brandName(brandService.findBrandByBrandIdx(product.getBrandIdx()).getBrandName())
                            .categoryName(categoryService.findCategoryByCategoryIdx(product.getCategoryIdx()).getCategoryName())
                            .build()).toList();

            return ProductReadDto.ProductReadListResponse.builder()
                    .result(true)
                    .reason("SUCCESS")
                    .productList(productItemList)
                    .build();


        }catch (Exception e) {
            e.printStackTrace();
        }
        return ProductReadDto.ProductReadListResponse.builder()
                .result(false)
                .reason("Unexpected Error")
                .build();
    }

    public ProductReadDto.ProductReadDetailResponse readProductDetail(Long productIdx){
        try {
            ProductDto.Info foundProduct = productService.findProductByProductIdx(productIdx);
            if(Objects.isNull(foundProduct)){
                return ProductReadDto.ProductReadDetailResponse.builder()
                        .result(false)
                        .reason("Product Not Found")
                        .build();
            }

            ProductReadDto.ProductReadDetailItem productDetail = ProductReadDto.ProductReadDetailItem.builder()
                    .productIdx(foundProduct.getProductIdx())
                    .brandIdx(foundProduct.getBrandIdx())
                    .brandName(brandService.findBrandByBrandIdx(foundProduct.getBrandIdx()).getBrandName())
                    .categoryIdx(foundProduct.getCategoryIdx())
                    .categoryName(categoryService.findCategoryByCategoryIdx(foundProduct.getCategoryIdx()).getCategoryName())
                    .productName(foundProduct.getProductName())
                    .productPrice(foundProduct.getProductPrice())
                    .productDesc(foundProduct.getProductDesc())
                    .productImage(foundProduct.getProductImage())
                    .build();

            return ProductReadDto.ProductReadDetailResponse.builder()
                    .result(true)
                    .reason("SUCCESS")
                    .productDetail(productDetail)
                    .build();

        }catch (Exception e){
            e.printStackTrace();
        }
        return ProductReadDto.ProductReadDetailResponse.builder()
                .result(false)
                .reason("Unexpected Error")
                .build();
    }


}
