package com.musinsa.m_backend.controller;

import com.musinsa.m_backend.dto.SearchDto;
import com.musinsa.m_backend.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/cheapestCodiBrand")
    public ResponseEntity<SearchDto.SearchCheapestCodiBrandResponse> getCheapestCodiBrand() {
        return ResponseEntity.ok().body(searchService.searchCheapestCodiBrand());
    }

    @GetMapping("/cheapestProductsPerCategory")
    public ResponseEntity<SearchDto.SearchCheapestProductsPerCategoryResponse> getCheapestProductsPerCategory() {
        return ResponseEntity.ok().body(searchService.searchCheapestProductsPerCategory());
    }

    @GetMapping("/lowestAndHighestPricesForACategory")
    public ResponseEntity<SearchDto.SearchHighestAndLowestPriceProductsForCategoryResponse> getLowestAndHighestPriceProductsForCategory(@RequestParam("categoryName") final String categoryName) {
        System.out.println(" ::::: " + categoryName);
        return ResponseEntity.ok().body(searchService.searchHighestAndLowestPriceProductsForCategory(categoryName));

    }
}
