package com.musinsa.m_backend.service;

import com.musinsa.m_backend.dto.ProductProcessDto;
import com.musinsa.m_backend.dto.base.BrandDto;
import com.musinsa.m_backend.dto.base.CategoryDto;
import com.musinsa.m_backend.dto.base.ProductDto;
import com.musinsa.m_backend.service.dtoService.BrandService;
import com.musinsa.m_backend.service.dtoService.CategoryService;
import com.musinsa.m_backend.service.dtoService.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductProcessService {

    private final ProductService productService;
    private final BrandService brandService;
    private final CategoryService categoryService;

    public ProductProcessDto.ProductProcessResponse insertProduct(ProductProcessDto.ProductUpsertRequest request) {
        try {

            BrandDto.Info brand = brandService.findBrandByBrandIdx(request.getBrandIdx());

            if(Objects.isNull(brand)){
                return ProductProcessDto.ProductProcessResponse.builder()
                        .result(false)
                        .reason("Brand Not Found").build();
            }

            CategoryDto.Info category = categoryService.findCategoryByCategoryIdx(request.getCategoryIdx());

            if(Objects.isNull(category)){
                return ProductProcessDto.ProductProcessResponse.builder()
                        .result(false)
                        .reason("Category Not Found").build();
            }

            ProductDto.Info product = ProductDto.Info.builder()
                    .brandIdx(brand.getBrandIdx())
                    .categoryIdx(category.getCategoryIdx())
                    .productName(request.getProductName())
                    .productDesc(request.getProductDesc())
                    .productPrice(request.getProductPrice())
                    .productImage(request.getProductImage())
                    .build();

            productService.save(product);

            return ProductProcessDto.ProductProcessResponse.builder()
                    .result(true)
                    .reason("SUCCESS")
                    .build();



        }catch (Exception e){
            e.printStackTrace();
        }

        return ProductProcessDto.ProductProcessResponse.builder()
                .result(false)
                .reason("Unexpected Error").build();
    }

    public ProductProcessDto.ProductProcessResponse updateProduct(ProductProcessDto.ProductUpsertRequest request) {
        try {

            ProductDto.Info foundProduct = productService.findProductByProductIdx(request.getProductIdx());

            if(Objects.isNull(foundProduct)){
                return ProductProcessDto.ProductProcessResponse.builder()
                        .result(false)
                        .reason("Product Not Found").build();
            }

            BrandDto.Info brand = brandService.findBrandByBrandIdx(request.getBrandIdx());

            if(Objects.isNull(brand)){
                return ProductProcessDto.ProductProcessResponse.builder()
                        .result(false)
                        .reason("Brand Not Found").build();
            }

            CategoryDto.Info category = categoryService.findCategoryByCategoryIdx(request.getCategoryIdx());

            if(Objects.isNull(category)){
                return ProductProcessDto.ProductProcessResponse.builder()
                        .result(false)
                        .reason("Category Not Found").build();
            }

            productService.save(foundProduct.toBuilder()
                    .brandIdx(brand.getBrandIdx())
                    .categoryIdx(category.getCategoryIdx())
                    .productName(request.getProductName())
                    .productDesc(request.getProductDesc())
                    .productImage(request.getProductImage())
                    .productPrice(request.getProductPrice())
                    .build());

            return ProductProcessDto.ProductProcessResponse.builder()
                    .result(true)
                    .reason("SUCCESS")
                    .build();


        }catch (Exception e){
            e.printStackTrace();
        }

        return ProductProcessDto.ProductProcessResponse.builder()
                .result(false)
                .reason("Unexpected Error").build();

    }

    public ProductProcessDto.ProductProcessResponse deleteProduct(ProductProcessDto.ProductDeleteRequest request) {
        return ProductProcessDto.ProductProcessResponse.builder()
                .result(false)
                .reason("Unexpected Error").build();
    }




}
