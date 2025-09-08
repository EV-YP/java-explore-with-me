package ru.practicum.category.service.external;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryExternalServiceImpl implements CategoryExternalService {
    private final CategoryRepository repository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto getCategory(Integer catId) {
        Category category = repository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Category with id " + catId + " not found"));
        return categoryMapper.toCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size, Sort.by("id").ascending());
        Page<Category> categories = repository.findAll(pageable);
        return categories.getContent().stream()
                .map(categoryMapper::toCategoryDto)
                .toList();
    }
}
