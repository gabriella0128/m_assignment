package com.musinsa.m_backend.service;

import com.musinsa.m_backend.dto.SearchDto;
import com.musinsa.m_backend.dto.base.BrandDto;
import com.musinsa.m_backend.dto.base.CategoryDto;
import com.musinsa.m_backend.dto.base.ProductDto;
import com.musinsa.m_backend.service.dtoService.BrandService;
import com.musinsa.m_backend.service.dtoService.CategoryService;
import com.musinsa.m_backend.service.dtoService.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ProductService productService;
    private final BrandService brandService;
    private final CategoryService categoryService;

    public SearchDto.SearchCheapestCodiBrandResponse searchCheapestCodiBrand(){
        try {
            List<BrandDto.Info> brands = brandService.findAll();
            List<CategoryDto.Info> categories = categoryService.findAll();

            Set<Long> categoryIdxs = categories.stream()
                    .map(CategoryDto.Info::getCategoryIdx)
                    .collect(Collectors.toSet());

            List<Long> brandsWithAllCategories = findBrandsWithAllCategories(brands, categoryIdxs);

            if (brandsWithAllCategories.isEmpty()) {
                return SearchDto.SearchCheapestCodiBrandResponse.builder()
                        .result(false)
                        .reason("No Matching Brand")
                        .build();
            }

            Map<Long, List<ProductDto.Info>> cheapestProductsMapGroupedByBrandIdx = brandsWithAllCategories.stream()
                .collect(Collectors.toMap(
                    brandIdx -> brandIdx,
                    brandIdx -> categoryIdxs.stream()
                        .map(categoryIdx -> productService.findMinPriceProductByBrandIdxAndCategoryIdx(brandIdx, categoryIdx))
                        .toList()
                ));

            Optional<Map.Entry<Long, List<ProductDto.Info>>> lowestTotalPriceEntryOptional = cheapestProductsMapGroupedByBrandIdx.entrySet().stream()
                .min(Comparator.comparingDouble(entry -> entry.getValue().stream()
                    .mapToDouble(ProductDto.Info::getProductPrice)
                    .sum()));

            Map.Entry<Long, List<ProductDto.Info>> lowestTotalPriceEntry = lowestTotalPriceEntryOptional.orElseThrow();

            Long lowestTotalPriceBrandIdx = lowestTotalPriceEntry.getKey();
            BrandDto.Info lowestTotalPriceBrand = brandService.findBrandByBrandIdx(lowestTotalPriceBrandIdx);

            double lowestTotalPrice = lowestTotalPriceEntry.getValue().stream()
                        .mapToDouble(ProductDto.Info::getProductPrice)
                        .sum();
            List<ProductDto.Info> productList = lowestTotalPriceEntry.getValue();

            List<SearchDto.CheapestCodiBrandItem> items = productList.stream().map(product -> SearchDto.CheapestCodiBrandItem.builder()
                        .productIdx(product.getProductIdx())
                        .productName(product.getProductName())
                        .productPrice(product.getProductPrice())
                        .productDesc(product.getProductDesc())
                        .categoryIdx(product.getCategoryIdx())
                        .categoryName(categoryService.findCategoryByCategoryIdx(product.getCategoryIdx()).getCategoryName())
                        .build()).toList();

            return SearchDto.SearchCheapestCodiBrandResponse.builder()
                        .result(true)
                        .reason("SUCCESS")
                        .brandIdx(lowestTotalPriceBrandIdx)
                        .brandName(lowestTotalPriceBrand.getBrandName())
                        .totalPrice(lowestTotalPrice)
                        .items(items).build();

        }catch (Exception e){
            e.printStackTrace();
        }

        return SearchDto.SearchCheapestCodiBrandResponse
                .builder()
                .result(false)
                .reason("Unexpected Error")
                .build();

    }

    public SearchDto.SearchCheapestProductsPerCategoryResponse searchCheapestProductsPerCategory(){
        try {
            List<CategoryDto.Info> categories = categoryService.findAll();

            Set<Long> categoryIdxs = categories.stream()
                    .map(CategoryDto.Info::getCategoryIdx)
                    .collect(Collectors.toSet());

            List<ProductDto.Info> productList = categoryIdxs.stream()
                .map(productService::findMinPriceProductByCategoryIdx)
                .filter(Objects::nonNull)
                .toList();

            Double totalPrice = productList.stream()
                .mapToDouble(ProductDto.Info::getProductPrice).sum();

            List<SearchDto.CheapestProductsPerCategoryItem> cheapestProductsPerCategoryItems = productList.stream().map(product -> SearchDto.CheapestProductsPerCategoryItem.builder()
                    .categoryIdx(product.getCategoryIdx())
                    .categoryName(categoryService.findCategoryByCategoryIdx(product.getCategoryIdx()).getCategoryName())
                    .brandIdx(product.getBrandIdx())
                    .brandName(brandService.findBrandByBrandIdx(product.getBrandIdx()).getBrandName())
                    .productIdx(product.getProductIdx())
                    .productName(product.getProductName())
                    .productPrice(product.getProductPrice())
                    .build()).toList();

            return SearchDto.SearchCheapestProductsPerCategoryResponse.builder()
                    .result(true)
                    .reason("SUCCESS")
                    .items(cheapestProductsPerCategoryItems)
                    .totalPrice(totalPrice)
                    .build();

        }catch (Exception e){
            e.printStackTrace();
        }
        return SearchDto.SearchCheapestProductsPerCategoryResponse
                .builder()
                .result(false)
                .reason("Unexpected Error")
                .build();


    }

    public SearchDto.SearchHighestAndLowestPriceProductsForCategoryResponse searchHighestAndLowestPriceProductsForCategory(String categoryName){
        try {
            CategoryDto.Info category = categoryService.findCategoryByCategoryName(categoryName);

            if(Objects.isNull(category)){
                return SearchDto.SearchHighestAndLowestPriceProductsForCategoryResponse.builder()
                        .result(false)
                        .reason("No Matching Category")
                        .build();
            }

            ProductDto.Info minPriceProduct = productService.findMinPriceProductByCategoryIdx(category.getCategoryIdx());
            ProductDto.Info maxPriceProduct = productService.findMaxPriceProductByCategoryIdx(category.getCategoryIdx());

            if(Objects.isNull(minPriceProduct) || Objects.isNull(maxPriceProduct)){
                return SearchDto.SearchHighestAndLowestPriceProductsForCategoryResponse.builder()
                    .result(false)
                    .reason("No Product Found")
                    .build();
            }

            SearchDto.HighestOrLowestPriceItem lowestPriceItem = SearchDto.HighestOrLowestPriceItem.builder()
                    .brandIdx(minPriceProduct.getBrandIdx())
                    .brandName(brandService.findBrandByBrandIdx(minPriceProduct.getBrandIdx()).getBrandName())
                    .productIdx(minPriceProduct.getProductIdx())
                    .productName(minPriceProduct.getProductName())
                    .productPrice(minPriceProduct.getProductPrice())
                    .build();

            SearchDto.HighestOrLowestPriceItem highestPriceItem = SearchDto.HighestOrLowestPriceItem.builder()
                    .brandIdx(maxPriceProduct.getBrandIdx())
                    .brandName(brandService.findBrandByBrandIdx(maxPriceProduct.getBrandIdx()).getBrandName())
                    .productIdx(maxPriceProduct.getProductIdx())
                    .productName(maxPriceProduct.getProductName())
                    .productPrice(maxPriceProduct.getProductPrice())
                    .build();

            return SearchDto.SearchHighestAndLowestPriceProductsForCategoryResponse.builder()
                    .result(true)
                    .reason("SUCCESS")
                    .categoryIdx(category.getCategoryIdx())
                    .categoryName(category.getCategoryName())
                    .lowestPriceItem(lowestPriceItem)
                    .highestPriceItem(highestPriceItem)
                    .build();

        }catch (Exception e){
            e.printStackTrace();
        }

        return SearchDto.SearchHighestAndLowestPriceProductsForCategoryResponse
                .builder()
                .result(false)
                .reason("Unexpected Error")
                .build();



    }

    private List<Long> findBrandsWithAllCategories(List<BrandDto.Info> brands, Set<Long> categoryIdxs){

        return brands.stream()
                .map(BrandDto.Info::getBrandIdx)
                .filter(brandIdx -> hasProductInEveryCategory(brandIdx, categoryIdxs))
                .collect(Collectors.toList());

    }

    private boolean hasProductInEveryCategory(Long brandIdx, Set<Long> categoryIds) {
        return categoryIds.stream()
                .allMatch(categoryIdx -> productService.existProductByBrandIdxAndCategoryIdx(brandIdx, categoryIdx));
    }

}
