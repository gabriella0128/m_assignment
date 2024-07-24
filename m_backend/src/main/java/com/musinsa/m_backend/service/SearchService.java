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

            Map<Long,List<ProductDto.Info>> cheapestProductsMapGroupedByBrandIdx = new HashMap<>();

            brandsWithAllCategories.forEach(brandIdx -> cheapestProductsMapGroupedByBrandIdx.put(brandIdx, categoryIdxs.stream().map(categoryIdx -> productService.findMinPriceProductByBrandIdxAndCategoryIdx(brandIdx, categoryIdx)).toList()));

            List<Map.Entry<Long, List<ProductDto.Info>>> entryList = cheapestProductsMapGroupedByBrandIdx.entrySet().stream()
                    .toList();


            Optional<Map.Entry<Long, List<ProductDto.Info>>> lowestTotalPriceEntry = entryList.stream()
                    .min(Comparator.comparingDouble(entry -> entry.getValue().stream()
                            .mapToDouble(ProductDto.Info::getProductPrice)
                            .sum()));



            if (lowestTotalPriceEntry.isPresent()) {
                Long lowestTotalPriceBrandIdx = lowestTotalPriceEntry.get().getKey();
                BrandDto.Info lowestTotalPriceBrand = brandService.findBrandByBrandIdx(lowestTotalPriceBrandIdx);

                double lowestTotalPrice = lowestTotalPriceEntry.get().getValue().stream()
                        .mapToDouble(ProductDto.Info::getProductPrice)
                        .sum();
                List<ProductDto.Info> productList = lowestTotalPriceEntry.get().getValue();

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

            } else {
                return SearchDto.SearchCheapestCodiBrandResponse
                        .builder()
                        .result(false)
                        .reason("No Codi Found")
                        .build();
            }




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

            List<ProductDto.Info> productList = categoryIdxs.stream().map(productService::findMinPriceProductByCategoryIdx).toList();

            Double totalPrice = productList.stream().mapToDouble(ProductDto.Info::getProductPrice).sum();

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

    private List<ProductDto.Info> findMinPriceProductsByBrandIdx(Long brandIdx, Set<Long> categoryIdxs){

       return categoryIdxs.stream().map(categoryIdx -> productService.findMinPriceProductByBrandIdxAndCategoryIdx(brandIdx, categoryIdx))
                .collect(Collectors.toList());
    }






}
