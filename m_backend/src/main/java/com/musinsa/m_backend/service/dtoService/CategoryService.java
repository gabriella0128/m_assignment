package com.musinsa.m_backend.service.dtoService;

import com.musinsa.m_backend.dto.base.CategoryDto;
import com.musinsa.m_backend.mapper.CategoryMapper;
import com.musinsa.m_backend.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public CategoryDto.Info findCategoryByCategoryIdx(Long categoryIdx) {
        return categoryMapper.toInfoDto(categoryRepository.findCategoryByCategoryIdx(categoryIdx).orElse(null));
    }

}
