package org.project.expendituremanagement.serviceimpl;

import org.project.expendituremanagement.dto.CategoryDTO;
import org.project.expendituremanagement.entity.Category;
import org.project.expendituremanagement.entity.UserInformation;
import org.project.expendituremanagement.repository.CategoryRepository;
import org.project.expendituremanagement.serviceinterface.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Category createCategory(CategoryDTO categoryDTO, String userId) {
        Category category=new Category();
        category.setCategoryName(categoryDTO.getCategoryName());

        UserInformation userInformation=new UserInformation();
        userInformation.setUserId(userId);

        category.setUserInformation(userInformation);
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getCategories(String userId) {
        UserInformation userInformation=new UserInformation();
        userInformation.setUserId(userId);

        return categoryRepository.findByUserInformation(userInformation);
    }

    @Override
    public Category updateCategory(CategoryDTO categoryDTO, UUID categoryId) {
        if(categoryRepository.existsById(categoryId)) {
            Category category=categoryRepository.getOne(categoryId);
            category.setCategoryName(categoryDTO.getCategoryName());

            return categoryRepository.save(category);
        }
        return null;
    }

    @Override
    public void deleteCategory(UUID categoryId)
    {
        if(categoryRepository.existsById(categoryId))
            categoryRepository.deleteById(categoryId);
    }
}
