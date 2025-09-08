package ru.practicum.category.service.external;

import ru.practicum.category.dto.CategoryDto;

import java.util.List;

public interface CategoryExternalService {
    CategoryDto getCategory(Integer catId);

    List<CategoryDto> getCategories(Integer from, Integer size);
}
