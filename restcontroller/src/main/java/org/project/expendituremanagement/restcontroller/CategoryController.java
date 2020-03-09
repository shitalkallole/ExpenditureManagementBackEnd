package org.project.expendituremanagement.restcontroller;

import org.project.expendituremanagement.dto.CategoryDTO;
import org.project.expendituremanagement.entity.Category;
import org.project.expendituremanagement.serviceinterface.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value ="/{userId}",method = RequestMethod.POST)
    public Category createCategory(@RequestBody CategoryDTO categoryDTO,
                                   @PathVariable(name = "userId") String userId)
    {
        return categoryService.createCategory(categoryDTO,userId);
    }

    @RequestMapping(value="/{userId}",method = RequestMethod.GET)
    public List<Category> getCategories(@PathVariable(name="userId") String userId){

        return categoryService.getCategories(userId);
    }

    @RequestMapping(value = "/{categoryId}",method = RequestMethod.PUT)
    public Category updateCategory(@RequestBody CategoryDTO categoryDTO,
                                   @PathVariable(name="categoryId") UUID categoryId){
        return categoryService.updateCategory(categoryDTO, categoryId);

    }

    @RequestMapping (value = "/{categoryId}",method = RequestMethod.DELETE)
    public void deleteCategory(@PathVariable(name="categoryId")UUID categoryId)
    {
        categoryService.deleteCategory(categoryId);
    }
}
