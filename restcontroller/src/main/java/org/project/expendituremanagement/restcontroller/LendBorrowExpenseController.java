package org.project.expendituremanagement.restcontroller;

import org.project.expendituremanagement.dto.*;
import org.project.expendituremanagement.entity.LendBorrowExpense;
import org.project.expendituremanagement.serviceinterface.LendBorrowExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/lendborrowexpense")
public class LendBorrowExpenseController {

    @Autowired
    private LendBorrowExpenseService lendBorrowExpenseService;
    private String errorText="something wrong went";

    @RequestMapping(value="/{userId}",method = RequestMethod.POST)
    public ResponseEntity<Object> createEntryInLendBorrowExpense(@RequestBody LendBorrowExpenseDTO lendBorrowExpenseDTO,
                                                            @PathVariable(name = "userId") String userId)
    {
        LendBorrowExpense response = lendBorrowExpenseService.createEntryInLendBorrowExpense(lendBorrowExpenseDTO,userId);
        if(response!=null)
        {
            StatusResponse statusResponse=new StatusResponse();
            statusResponse.setSuccessMessage("Successfully Created");

            return new ResponseEntity<>(statusResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(errorText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/show/{startDate}/{endDate}/{userId}",method = RequestMethod.GET)
    public ResponseEntity<Object> getAllLendBorrowExpensesBetweenDates(@PathVariable(name="startDate") String startDateInString,
                                                                        @PathVariable(name="endDate") String endDateInString,
                                                                        @PathVariable(name="userId") String userID){
        List<LendBorrowExpense> response=lendBorrowExpenseService.getAllLendBorrowExpensesBetweenDates(startDateInString, endDateInString, userID);
        if(response!=null)
            return new ResponseEntity<>(response,HttpStatus.OK);
        return new ResponseEntity<>(errorText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/delete/{startDate}/{endDate}/{userId}",method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteAllLendBorrowExpensesBetweenDates(@PathVariable(name="startDate") String startDateInString,
                                                        @PathVariable(name="endDate") String endDateInString,
                                                        @PathVariable(name="userId") String userID){
        if(lendBorrowExpenseService.deleteAllLendBorrowExpensesBetweenDates(startDateInString, endDateInString, userID)){
            StatusResponse statusResponse=new StatusResponse();
            statusResponse.setSuccessMessage("All records between dates deleted successfully");

            return new ResponseEntity<>(statusResponse,HttpStatus.OK);
        }
        return new ResponseEntity<>(errorText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/{transactionId}",method = RequestMethod.PUT)
    public ResponseEntity<Object> updateEntryInLendBorrowExpense(@RequestBody LendBorrowExpenseDTO lendBorrowExpenseDTO,
                                                            @PathVariable(name="transactionId") UUID transactionId){
        LendBorrowExpense response=lendBorrowExpenseService.updateEntryInLendBorrowExpense(lendBorrowExpenseDTO, transactionId);
        if(response!=null)
            return new ResponseEntity<>(response,HttpStatus.OK);
        return new ResponseEntity<>(errorText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/{transactionId}",method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteEntryFromLendBorrowExpense(@PathVariable(name="transactionId") UUID transactionId){
        if(lendBorrowExpenseService.deleteEntryFromLendBorrowExpense(transactionId)){
            StatusResponse statusResponse=new StatusResponse();
            statusResponse.setSuccessMessage("Deleted successfully");

            return new ResponseEntity<>(statusResponse,HttpStatus.OK);
        }
        return new ResponseEntity<>(errorText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/show/{startDate}/{endDate}/{friendId}/{userId}",method = RequestMethod.GET)
    public ResponseEntity<Object> getAllLendBorrowExpensesOfFriendBetweenDates(@PathVariable(name="startDate") String startDateInString,
                                                                                @PathVariable(name="endDate") String endDateInString,
                                                                                @PathVariable(name="friendId") UUID friendId,
                                                                                @PathVariable(name="userId") String userID){
        List<LendBorrowExpense> response=lendBorrowExpenseService.getAllLendBorrowExpensesOfFriendBetweenDates(startDateInString, endDateInString, friendId, userID);
        if(response!=null)
            return new ResponseEntity<>(response,HttpStatus.OK);
        return new ResponseEntity<>(errorText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/delete/{startDate}/{endDate}/{friendId}/{userId}",method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteAllLendBorrowExpensesOfFriendBetweenDates(@PathVariable(name="startDate") String startDateInString,
                                                                @PathVariable(name="endDate") String endDateInString,
                                                                @PathVariable(name="friendId") UUID friendId,
                                                                @PathVariable(name="userId") String userID){

        if(lendBorrowExpenseService.deleteAllLendBorrowExpensesOfFriendBetweenDates(startDateInString, endDateInString, friendId, userID)){
            StatusResponse statusResponse=new StatusResponse();
            statusResponse.setSuccessMessage("All records between dates of your friend deleted successfully");

            return new ResponseEntity<>(statusResponse,HttpStatus.OK);
        }
        return new ResponseEntity<>(errorText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/calculate/all/{startDate}/{endDate}/{userId}",method = RequestMethod.GET)
    public ResponseEntity<Object> calculateLendBorrowExpenseForEachFriend(@PathVariable(name="startDate") String startDateInString,
                                                                                                    @PathVariable(name="endDate") String endDateInString,
                                                                                                    @PathVariable(name="userId") String userID){

        List<FinalResultOfLendBorrowExpenseForFriendDTO> response=lendBorrowExpenseService.calculateLendBorrowExpenseForEachFriend(startDateInString, endDateInString, userID);
        if(response!=null)
            return new ResponseEntity<>(response,HttpStatus.OK);
        return new ResponseEntity<>(errorText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/calculate/{startDate}/{endDate}/{friendId}",method = RequestMethod.GET)
    public ResponseEntity<Object> calculateLendBorrowExpenseForSingleFriend(@PathVariable(name="startDate") String startDateInString,
                                                                                                @PathVariable(name="endDate") String endDateInString,
                                                                                                @PathVariable(name="friendId") UUID friendId){
        FinalResultOfLendBorrowExpenseForFriendDTO response=lendBorrowExpenseService.calculateLendBorrowExpenseForSingleFriend(startDateInString, endDateInString, friendId);
        if(response!=null)
            return new ResponseEntity<>(response,HttpStatus.OK);
        return new ResponseEntity<>(errorText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/calculate/category/{startDate}/{endDate}/{categoryId}/{userId}",method = RequestMethod.GET)
    public ResponseEntity<Object> calculateLendExpenseForCategory(@PathVariable(name="startDate") String startDateInString,
                                                                                             @PathVariable(name="endDate") String endDateInString,
                                                                                             @PathVariable(name="categoryId") UUID categoryId,
                                                                                             @PathVariable(name="userId") String userID){
        List<FinalResultOfLendExpenseForCategoryDTO> response=lendBorrowExpenseService.calculateLendExpenseForCategory(startDateInString, endDateInString, categoryId,userID);
        if(response!=null)
            return new ResponseEntity<>(response,HttpStatus.OK);
        return new ResponseEntity<>(errorText,HttpStatus.BAD_REQUEST);
    }
}
