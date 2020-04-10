package org.project.expendituremanagement.restcontroller;

import org.project.expendituremanagement.constants.API;
import org.project.expendituremanagement.constants.ErrorText;
import org.project.expendituremanagement.constants.SuccessText;
import org.project.expendituremanagement.dto.CategoryDTO;
import org.project.expendituremanagement.dto.StatusResponse;
import org.project.expendituremanagement.entity.Category;
import org.project.expendituremanagement.serviceinterface.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(API.CATEGORY_PATH)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> createCategory(@RequestBody CategoryDTO categoryDTO,
                                                 @RequestHeader(name = "userId") String userId)
    {
        Category response=categoryService.createCategory(categoryDTO,userId);
        if(response!=null)
            return new ResponseEntity<>(response,HttpStatus.OK);
        return new ResponseEntity<>(ErrorText.somethingWentWrongText, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getCategories(@RequestHeader(name="userId") String userId){
        List<Category> response=categoryService.getCategories(userId);
        if(response!=null)
            return new ResponseEntity<>(response,HttpStatus.OK);
        return new ResponseEntity<>(ErrorText.somethingWentWrongText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = API.CATEGORY_ID_PARAM_PATH,method = RequestMethod.PUT)
    public ResponseEntity<Object> updateCategory(@RequestBody CategoryDTO categoryDTO,
                                                 @PathVariable(name="categoryId") UUID categoryId,
                                                 @RequestHeader(name = "userId") String userId){
        String ownerId=categoryService.getUserIdByCategoryId(categoryId);
        if(userId.equals(ownerId)) {
            Category response = categoryService.updateCategory(categoryDTO, categoryId);
            if (response != null)
                return new ResponseEntity<>(response, HttpStatus.OK);
            return new ResponseEntity<>(ErrorText.somethingWentWrongText, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ErrorText.unauthorizedText,HttpStatus.UNAUTHORIZED);

    }

    @RequestMapping (value = API.CATEGORY_ID_PARAM_PATH,method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteCategory(@PathVariable(name="categoryId")UUID categoryId,
                                                 @RequestHeader(name = "userId") String userId)
    {
        String ownerId=categoryService.getUserIdByCategoryId(categoryId);
        if(userId.equals(ownerId)) {
            if (categoryService.deleteCategory(categoryId)) {
                StatusResponse statusResponse = new StatusResponse();
                statusResponse.setSuccessMessage(SuccessText.successfullyDeleted);

                return new ResponseEntity<>(statusResponse, HttpStatus.OK);
            }
            return new ResponseEntity<>(ErrorText.somethingWentWrongText, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ErrorText.unauthorizedText,HttpStatus.UNAUTHORIZED);
    }
}
