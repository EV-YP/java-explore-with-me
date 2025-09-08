package ru.practicum.category.service.admin;

import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;

public interface CategoryAdminService {
    CategoryDto addCategory(NewCategoryDto categoryDto);

    void deleteCategory(Integer catId);

    CategoryDto updateCategory(int id, CategoryDto categoryDto);
}
