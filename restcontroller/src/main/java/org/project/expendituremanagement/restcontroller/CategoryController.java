package org.project.expendituremanagement.restcontroller;

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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    private String errorText="something wrong went";

    @RequestMapping(value ="/{userId}",method = RequestMethod.POST)
    public ResponseEntity<Object> createCategory(@RequestBody CategoryDTO categoryDTO,
                                   @PathVariable(name = "userId") String userId)
    {
        Category response=categoryService.createCategory(categoryDTO,userId);
        if(response!=null)
            return new ResponseEntity<>(response,HttpStatus.OK);
        return new ResponseEntity<>(errorText, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/{userId}",method = RequestMethod.GET)
    public ResponseEntity<Object> getCategories(@PathVariable(name="userId") String userId){
        List<Category> response=categoryService.getCategories(userId);
        if(response!=null)
            return new ResponseEntity<>(response,HttpStatus.OK);
        return new ResponseEntity<>(errorText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{categoryId}",method = RequestMethod.PUT)
    public ResponseEntity<Object> updateCategory(@RequestBody CategoryDTO categoryDTO,
                                   @PathVariable(name="categoryId") UUID categoryId){
        Category response=categoryService.updateCategory(categoryDTO, categoryId);
        if(response!=null)
            return new ResponseEntity<>(response,HttpStatus.OK);
        return new ResponseEntity<>(errorText,HttpStatus.BAD_REQUEST);

    }

    @RequestMapping (value = "/{categoryId}",method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteCategory(@PathVariable(name="categoryId")UUID categoryId)
    {
        if(categoryService.deleteCategory(categoryId)){
            StatusResponse statusResponse=new StatusResponse();
            statusResponse.setSuccessMessage("Deleted Successfully");

            return new ResponseEntity<>(statusResponse,HttpStatus.OK);
        }
        return new ResponseEntity<>(errorText,HttpStatus.BAD_REQUEST);
    }
}
