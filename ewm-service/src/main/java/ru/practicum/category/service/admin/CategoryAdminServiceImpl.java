package ru.practicum.category.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryAdminServiceImpl implements CategoryAdminService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository repository;
    private final EventRepository eventRepository;

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategory) {
        Category category = categoryMapper.toCategory(newCategory);
        return categoryMapper.toCategoryDto(repository.save(category));
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = repository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category with id " + categoryId + " not found"));

        List<Event> events = eventRepository.findEventsByCategoryId(categoryId);
        if (!events.isEmpty()) {
            throw new ConflictException("Category with id " + categoryId + " has connected events");
        }
        
        repository.deleteById(categoryId);
    }

    @Override
    public CategoryDto updateCategory(int id, CategoryDto newCategory) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id " + id + " not found"));

        if (category.getName().equals(newCategory.getName())) {
            return categoryMapper.toCategoryDto(category);
        } else {
            return categoryMapper.toCategoryDto(repository.save(categoryMapper.toCategory(newCategory)));
        }

    }
}
