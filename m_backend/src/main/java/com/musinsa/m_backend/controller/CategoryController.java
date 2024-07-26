package com.musinsa.m_backend.controller;
import com.musinsa.m_backend.dto.CategoryReadDto;
import com.musinsa.m_backend.service.CategoryReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryReadService categoryReadService;

    @GetMapping("/list/select")
    public ResponseEntity<CategoryReadDto.CategoryReadListSelectResponse> readCategoryListSelect(){
        return ResponseEntity.ok().body(categoryReadService.readCategorySelectList());
    }
}
