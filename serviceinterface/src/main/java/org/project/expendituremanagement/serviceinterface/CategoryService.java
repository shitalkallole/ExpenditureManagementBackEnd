package org.project.expendituremanagement.serviceinterface;

import org.project.expendituremanagement.dto.CategoryDTO;
import org.project.expendituremanagement.entity.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    Category createCategory(CategoryDTO categoryDTO,String userId);
    List<Category> getCategories(String userId);
    Category updateCategory(CategoryDTO categoryDTO,UUID categoryId);
    void deleteCategory(UUID categoryId);
}
