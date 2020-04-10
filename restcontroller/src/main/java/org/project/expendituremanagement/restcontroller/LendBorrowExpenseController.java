package org.project.expendituremanagement.restcontroller;

import org.project.expendituremanagement.constants.API;
import org.project.expendituremanagement.constants.ErrorText;
import org.project.expendituremanagement.constants.SuccessText;
import org.project.expendituremanagement.dto.*;
import org.project.expendituremanagement.entity.LendBorrowExpense;
import org.project.expendituremanagement.serviceinterface.CategoryService;
import org.project.expendituremanagement.serviceinterface.FriendService;
import org.project.expendituremanagement.serviceinterface.LendBorrowExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(API.LEND_BORROW_EXPENSE_PATH)
public class LendBorrowExpenseController {

    @Autowired
    private LendBorrowExpenseService lendBorrowExpenseService;
    @Autowired
    private FriendService friendService;
    @Autowired
    private CategoryService categoryService;


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> createEntryInLendBorrowExpense(@RequestBody LendBorrowExpenseDTO lendBorrowExpenseDTO,
                                                                 @RequestHeader(name = "userId") String userId)
    {
        LendBorrowExpense response = lendBorrowExpenseService.createEntryInLendBorrowExpense(lendBorrowExpenseDTO,userId);
        if(response!=null)
        {
            StatusResponse statusResponse=new StatusResponse();
            statusResponse.setSuccessMessage(SuccessText.successfullyCreated);

            return new ResponseEntity<>(statusResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(ErrorText.somethingWentWrongText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value=API.SHOW_PATH+API.START_DATE_PARAM_PATH+API.END_DATE_PARAM_PATH,method = RequestMethod.GET)
    public ResponseEntity<Object> getAllLendBorrowExpensesBetweenDates(@PathVariable(name="startDate") String startDateInString,
                                                                        @PathVariable(name="endDate") String endDateInString,
                                                                        @RequestHeader(name="userId") String userId){
        List<LendBorrowExpense> response=lendBorrowExpenseService.getAllLendBorrowExpensesBetweenDates(startDateInString, endDateInString, userId);
        if(response!=null)
            return new ResponseEntity<>(response,HttpStatus.OK);
        return new ResponseEntity<>(ErrorText.somethingWentWrongText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value=API.DELETE_PATH+API.START_DATE_PARAM_PATH+API.END_DATE_PARAM_PATH,method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteAllLendBorrowExpensesBetweenDates(@PathVariable(name="startDate") String startDateInString,
                                                                          @PathVariable(name="endDate") String endDateInString,
                                                                          @RequestHeader(name="userId") String userId){
        if(lendBorrowExpenseService.deleteAllLendBorrowExpensesBetweenDates(startDateInString, endDateInString, userId)){
            StatusResponse statusResponse=new StatusResponse();
            statusResponse.setSuccessMessage(SuccessText.allRecordsBetweenDatesSuccessfullyDeleted);

            return new ResponseEntity<>(statusResponse,HttpStatus.OK);
        }
        return new ResponseEntity<>(ErrorText.somethingWentWrongText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value=API.TRANSACTION_ID_PARAM_PATH,method = RequestMethod.PUT)
    public ResponseEntity<Object> updateEntryInLendBorrowExpense(@RequestBody LendBorrowExpenseDTO lendBorrowExpenseDTO,
                                                                 @PathVariable(name="transactionId") UUID transactionId,
                                                                 @RequestHeader(name="userId") String userId){
        String ownerId = lendBorrowExpenseService.getUserIdByTransactionId(transactionId);
        if(userId.equals(ownerId)) {
            LendBorrowExpense response = lendBorrowExpenseService.updateEntryInLendBorrowExpense(lendBorrowExpenseDTO, transactionId);
            if (response != null)
                return new ResponseEntity<>(response, HttpStatus.OK);
            return new ResponseEntity<>(ErrorText.somethingWentWrongText, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ErrorText.unauthorizedText,HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value=API.TRANSACTION_ID_PARAM_PATH,method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteEntryFromLendBorrowExpense(@PathVariable(name="transactionId") UUID transactionId,
                                                                   @RequestHeader(name="userId") String userId){
        String ownerId = lendBorrowExpenseService.getUserIdByTransactionId(transactionId);
        if(userId.equals(ownerId)) {
            if (lendBorrowExpenseService.deleteEntryFromLendBorrowExpense(transactionId)) {
                StatusResponse statusResponse = new StatusResponse();
                statusResponse.setSuccessMessage(SuccessText.successfullyDeleted);

                return new ResponseEntity<>(statusResponse, HttpStatus.OK);
            }
            return new ResponseEntity<>(ErrorText.somethingWentWrongText, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ErrorText.unauthorizedText,HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value=API.SHOW_PATH+API.START_DATE_PARAM_PATH+API.END_DATE_PARAM_PATH+API.FRIEND_ID_PARAM_PATH,method = RequestMethod.GET)
    public ResponseEntity<Object> getAllLendBorrowExpensesOfFriendBetweenDates(@PathVariable(name="startDate") String startDateInString,
                                                                               @PathVariable(name="endDate") String endDateInString,
                                                                               @PathVariable(name="friendId") UUID friendId,
                                                                               @RequestHeader(name="userId") String userId){
        String ownerId = friendService.getUserIdByFriendId(friendId);
        if(userId.equals(ownerId)) {
            List<LendBorrowExpense> response = lendBorrowExpenseService.getAllLendBorrowExpensesOfFriendBetweenDates(startDateInString, endDateInString, friendId, userId);
            if (response != null)
                return new ResponseEntity<>(response, HttpStatus.OK);
            return new ResponseEntity<>(ErrorText.somethingWentWrongText, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ErrorText.unauthorizedText,HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = API.DELETE_PATH+API.START_DATE_PARAM_PATH+API.END_DATE_PARAM_PATH+API.FRIEND_ID_PARAM_PATH,method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteAllLendBorrowExpensesOfFriendBetweenDates(@PathVariable(name="startDate") String startDateInString,
                                                                @PathVariable(name="endDate") String endDateInString,
                                                                @PathVariable(name="friendId") UUID friendId,
                                                                @RequestHeader(name="userId") String userId){
        String ownerId= friendService.getUserIdByFriendId(friendId);
        if(userId.equals(ownerId)) {
            if (lendBorrowExpenseService.deleteAllLendBorrowExpensesOfFriendBetweenDates(startDateInString, endDateInString, friendId, userId)) {
                StatusResponse statusResponse = new StatusResponse();
                statusResponse.setSuccessMessage(SuccessText.allRecordsBetweenDatesSuccessfullyDeleted);

                return new ResponseEntity<>(statusResponse, HttpStatus.OK);
            }
            return new ResponseEntity<>(ErrorText.somethingWentWrongText, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ErrorText.unauthorizedText,HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value=API.CALCULATE_PATH+API.ALL_PATH+API.START_DATE_PARAM_PATH+API.END_DATE_PARAM_PATH,method = RequestMethod.GET)
    public ResponseEntity<Object> calculateLendBorrowExpenseForEachFriend(@PathVariable(name="startDate") String startDateInString,
                                                                                                    @PathVariable(name="endDate") String endDateInString,
                                                                                                    @RequestHeader(name="userId") String userId){

        List<FinalResultOfLendBorrowExpenseForFriendDTO> response=lendBorrowExpenseService.calculateLendBorrowExpenseForEachFriend(startDateInString, endDateInString, userId);
        if(response!=null)
            return new ResponseEntity<>(response,HttpStatus.OK);
        return new ResponseEntity<>(ErrorText.somethingWentWrongText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value=API.CALCULATE_PATH+API.START_DATE_PARAM_PATH+API.END_DATE_PARAM_PATH+API.FRIEND_ID_PARAM_PATH,method = RequestMethod.GET)
    public ResponseEntity<Object> calculateLendBorrowExpenseForSingleFriend(@PathVariable(name="startDate") String startDateInString,
                                                                            @PathVariable(name="endDate") String endDateInString,
                                                                            @PathVariable(name="friendId") UUID friendId,
                                                                            @RequestHeader(name="userId") String userId){
        String ownerId = friendService.getUserIdByFriendId(friendId);
        if(userId.equals(ownerId)) {
            FinalResultOfLendBorrowExpenseForFriendDTO response = lendBorrowExpenseService.calculateLendBorrowExpenseForSingleFriend(startDateInString, endDateInString, friendId);
            if (response != null)
                return new ResponseEntity<>(response, HttpStatus.OK);
            return new ResponseEntity<>(ErrorText.somethingWentWrongText, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ErrorText.unauthorizedText,HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value=API.CALCULATE_PATH+API.CATEGORY_PATH+API.START_DATE_PARAM_PATH+API.END_DATE_PARAM_PATH+API.CATEGORY_ID_PARAM_PATH,method = RequestMethod.GET)
    public ResponseEntity<Object> calculateLendExpenseForCategory(@PathVariable(name="startDate") String startDateInString,
                                                                                             @PathVariable(name="endDate") String endDateInString,
                                                                                             @PathVariable(name="categoryId") UUID categoryId,
                                                                                             @RequestHeader(name="userId") String userId){
        String ownerId=categoryService.getUserIdByCategoryId(categoryId);
        if(userId.equals(ownerId)) {
            List<FinalResultOfLendExpenseForCategoryDTO> response = lendBorrowExpenseService.calculateLendExpenseForCategory(startDateInString, endDateInString, categoryId, userId);
            if (response != null)
                return new ResponseEntity<>(response, HttpStatus.OK);
            return new ResponseEntity<>(ErrorText.somethingWentWrongText, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ErrorText.unauthorizedText,HttpStatus.UNAUTHORIZED);
    }
}
