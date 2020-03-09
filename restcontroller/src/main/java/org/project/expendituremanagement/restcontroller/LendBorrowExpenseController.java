package org.project.expendituremanagement.restcontroller;

import org.project.expendituremanagement.dto.CalculateLendExpenseForCategoryDTO;
import org.project.expendituremanagement.dto.FinalResultOfLendBorrowExpenseForFriendDTO;
import org.project.expendituremanagement.dto.FinalResultOfLendExpenseForCategoryDTO;
import org.project.expendituremanagement.dto.LendBorrowExpenseDTO;
import org.project.expendituremanagement.entity.LendBorrowExpense;
import org.project.expendituremanagement.serviceinterface.LendBorrowExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @RequestMapping(value="/{userId}",method = RequestMethod.POST)
    public LendBorrowExpense createEntryInLendBorrowExpense(@RequestBody LendBorrowExpenseDTO lendBorrowExpenseDTO,
                                                            @PathVariable(name = "userId") String userId)
    {
        return lendBorrowExpenseService.createEntryInLendBorrowExpense(lendBorrowExpenseDTO,userId);
    }

    @RequestMapping(value="/show/{startDate}/{endDate}/{userId}",method = RequestMethod.GET)
    public List<LendBorrowExpense> getAllLendBorrowExpensesBetweenDates(@PathVariable(name="startDate") String startDateInString,
                                                                        @PathVariable(name="endDate") String endDateInString,
                                                                        @PathVariable(name="userId") String userID){
       return lendBorrowExpenseService.getAllLendBorrowExpensesBetweenDates(startDateInString, endDateInString, userID);
    }

    @RequestMapping(value="/delete/{startDate}/{endDate}/{userId}",method = RequestMethod.DELETE)
    public void deleteAllLendBorrowExpensesBetweenDates(@PathVariable(name="startDate") String startDateInString,
                                                        @PathVariable(name="endDate") String endDateInString,
                                                        @PathVariable(name="userId") String userID){
        lendBorrowExpenseService.deleteAllLendBorrowExpensesBetweenDates(startDateInString, endDateInString, userID);
    }

    @RequestMapping(value="/{transactionId}",method = RequestMethod.PUT)
    public LendBorrowExpense updateEntryInLendBorrowExpense(@RequestBody LendBorrowExpenseDTO lendBorrowExpenseDTO,
                                                            @PathVariable(name="transactionId") UUID transactionId){
        return lendBorrowExpenseService.updateEntryInLendBorrowExpense(lendBorrowExpenseDTO, transactionId);
    }

    @RequestMapping(value="/{transactionId}",method = RequestMethod.DELETE)
    public void deleteEntryFromLendBorrowExpense(@PathVariable(name="transactionId") UUID transactionId){
        lendBorrowExpenseService.deleteEntryFromLendBorrowExpense(transactionId);
    }

    @RequestMapping(value="/show/{startDate}/{endDate}/{friendId}/{userId}",method = RequestMethod.GET)
    public List<LendBorrowExpense> getAllLendBorrowExpensesOfFriendBetweenDates(@PathVariable(name="startDate") String startDateInString,
                                                                                @PathVariable(name="endDate") String endDateInString,
                                                                                @PathVariable(name="friendId") UUID friendId,
                                                                                @PathVariable(name="userId") String userID){
        return lendBorrowExpenseService.getAllLendBorrowExpensesOfFriendBetweenDates(startDateInString, endDateInString, friendId, userID);
    }

    @RequestMapping(value = "/delete/{startDate}/{endDate}/{friendId}/{userId}",method = RequestMethod.DELETE)
    public void deleteAllLendBorrowExpensesOfFriendBetweenDates(@PathVariable(name="startDate") String startDateInString,
                                                                @PathVariable(name="endDate") String endDateInString,
                                                                @PathVariable(name="friendId") UUID friendId,
                                                                @PathVariable(name="userId") String userID){
        lendBorrowExpenseService.deleteAllLendBorrowExpensesOfFriendBetweenDates(startDateInString, endDateInString, friendId, userID);
    }

    @RequestMapping(value="/calculate/all/{startDate}/{endDate}/{userId}",method = RequestMethod.GET)
    public List<FinalResultOfLendBorrowExpenseForFriendDTO> calculateLendBorrowExpenseForEachFriend(@PathVariable(name="startDate") String startDateInString,
                                                                                                    @PathVariable(name="endDate") String endDateInString,
                                                                                                    @PathVariable(name="userId") String userID){

        return lendBorrowExpenseService.calculateLendBorrowExpenseForEachFriend(startDateInString, endDateInString, userID);
    }

    @RequestMapping(value="/calculate/{startDate}/{endDate}/{friendId}",method = RequestMethod.GET)
    public FinalResultOfLendBorrowExpenseForFriendDTO calculateLendBorrowExpenseForSingleFriend(@PathVariable(name="startDate") String startDateInString,
                                                                                                @PathVariable(name="endDate") String endDateInString,
                                                                                                @PathVariable(name="friendId") UUID friendId){
        return lendBorrowExpenseService.calculateLendBorrowExpenseForSingleFriend(startDateInString, endDateInString, friendId);

    }

    @RequestMapping(value="/calculate/category/{startDate}/{endDate}/{categoryId}/{userId}",method = RequestMethod.GET)
    public List<FinalResultOfLendExpenseForCategoryDTO> calculateLendExpenseForCategory(@PathVariable(name="startDate") String startDateInString,
                                                                                             @PathVariable(name="endDate") String endDateInString,
                                                                                             @PathVariable(name="categoryId") UUID categoryId,
                                                                                             @PathVariable(name="userId") String userID){
        return lendBorrowExpenseService.calculateLendExpenseForCategory(startDateInString, endDateInString, categoryId,userID);
    }


}
