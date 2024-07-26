package com.musinsa.m_backend.service;

import com.musinsa.m_backend.dto.CategoryReadDto;
import com.musinsa.m_backend.dto.base.CategoryDto;
import com.musinsa.m_backend.service.dtoService.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryReadService {
    private final CategoryService categoryService;

    public CategoryReadDto.CategoryReadListSelectResponse readCategorySelectList(){
        try {
            List<CategoryDto.Info> categorySelectList = categoryService.findAll();
            List<CategoryReadDto.CategoryReadSelectItem> categoryItemSelectList = categorySelectList.stream().map(category -> CategoryReadDto.CategoryReadSelectItem.builder()
                    .categoryIdx(category.getCategoryIdx())
                    .categoryName(category.getCategoryName())
                    .build()).toList();

            return CategoryReadDto.CategoryReadListSelectResponse.builder()
                    .result(true)
                    .reason("SUCCESS")
                    .categorySelectList(categoryItemSelectList)
                    .build();
        } catch (Exception e){
            e.printStackTrace();
        }

        return CategoryReadDto.CategoryReadListSelectResponse.builder()
                .result(false)
                .reason("Unexpected Error")
                .build();
    }
}
