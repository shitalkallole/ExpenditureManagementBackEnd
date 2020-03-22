package org.project.expendituremanagement.serviceinterface;

import org.project.expendituremanagement.dto.CalculateLendExpenseForCategoryDTO;
import org.project.expendituremanagement.dto.FinalResultOfLendBorrowExpenseForFriendDTO;
import org.project.expendituremanagement.dto.FinalResultOfLendExpenseForCategoryDTO;
import org.project.expendituremanagement.dto.LendBorrowExpenseDTO;
import org.project.expendituremanagement.entity.LendBorrowExpense;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface LendBorrowExpenseService {
    LendBorrowExpense createEntryInLendBorrowExpense(LendBorrowExpenseDTO lendBorrowExpenseDTO,String userId);
    List<LendBorrowExpense> getAllLendBorrowExpensesBetweenDates(String startDateInString,String endDateInString,String userId);
    boolean deleteAllLendBorrowExpensesBetweenDates(String startDateInString,String endDateInString,String userId);
    LendBorrowExpense updateEntryInLendBorrowExpense(LendBorrowExpenseDTO lendBorrowExpenseDTO, UUID transactionId);
    boolean deleteEntryFromLendBorrowExpense(UUID transactionId);

    List<LendBorrowExpense> getAllLendBorrowExpensesOfFriendBetweenDates(String startDateInString,String endDateInString,UUID friendId,String userId);
    boolean deleteAllLendBorrowExpensesOfFriendBetweenDates(String startDateInString,String endDateInString,UUID friendId,String userId);

    List<FinalResultOfLendBorrowExpenseForFriendDTO> calculateLendBorrowExpenseForEachFriend(String startDateInString, String endDateInString, String userId);
    FinalResultOfLendBorrowExpenseForFriendDTO calculateLendBorrowExpenseForSingleFriend(String startDateInString, String endDateInString, UUID friendId);

    List<FinalResultOfLendExpenseForCategoryDTO> calculateLendExpenseForCategory(String startDateInString, String endDateInString, UUID categoryId, String userId);
}
