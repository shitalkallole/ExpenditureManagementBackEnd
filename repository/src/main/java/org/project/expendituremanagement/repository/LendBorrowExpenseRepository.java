package org.project.expendituremanagement.repository;

import org.project.expendituremanagement.dto.CalculateLendBorrowExpenseForFriendDTO;
import org.project.expendituremanagement.dto.CalculateLendExpenseForCategoryDTO;
import org.project.expendituremanagement.entity.Category;
import org.project.expendituremanagement.entity.Friend;
import org.project.expendituremanagement.entity.LendBorrowExpense;
import org.project.expendituremanagement.entity.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface LendBorrowExpenseRepository extends JpaRepository<LendBorrowExpense, UUID> {
    @Query("select lbeObj from LendBorrowExpense lbeObj where userInformation=:userId and activityDate between :startDate and :endDate order by activityDate")
    List<LendBorrowExpense> getAllLendBorrowExpensesBetweenDates(@Param("userId") UserInformation userInformation,
                                                                 @Param("startDate") Date startDate,
                                                                 @Param("endDate") Date endDate);

    @Modifying
    @Query("delete from LendBorrowExpense where userInformation=:userId and activityDate between :startDate and :endDate")
    void deleteAllLendBorrowExpensesBetweenDates(@Param("userId") UserInformation userInformation,
                                                 @Param("startDate") Date startDate,
                                                 @Param("endDate") Date endDate);

    @Query("select lbeObj from LendBorrowExpense lbeObj where userInformation=:userId and friend=:friendId and activityDate between :startDate and :endDate order by activityDate")
    List<LendBorrowExpense> getAllLendBorrowExpensesOfFriendBetweenDates(@Param("userId") UserInformation userInformation,
                                                                         @Param("startDate") Date startDate,
                                                                         @Param("endDate") Date endDate,
                                                                         @Param("friendId") Friend friend);

    @Modifying
    @Query("delete from LendBorrowExpense where userInformation=:userId and friend=:friendId and activityDate between :startDate and :endDate")
    void deleteAllLendBorrowExpensesOfFriendBetweenDates(@Param("userId") UserInformation userInformation,
                                                         @Param("startDate") Date startDate,
                                                         @Param("endDate") Date endDate,
                                                         @Param("friendId") Friend friend);



    @Query("select new org.project.expendituremanagement.dto.CalculateLendBorrowExpenseForFriendDTO(l.friend.friendId as friendId,sum(l.amount) as amount,l.lendOrBorrow as lendOrBorrow) from LendBorrowExpense l where userInformation=:userId and activityDate between :startDate and :endDate group by friend,lendOrBorrow")
    List<CalculateLendBorrowExpenseForFriendDTO> calculateLendBorrowExpenseForEachFriend(@Param("userId") UserInformation userInformation,
                                                                                         @Param("startDate") Date startDate,
                                                                                         @Param("endDate") Date endDate);

    @Query("select new org.project.expendituremanagement.dto.CalculateLendBorrowExpenseForFriendDTO(l.friend.friendId as friendId,sum(l.amount) as amount,l.lendOrBorrow as lendOrBorrow) from LendBorrowExpense l where friend=:friendId and activityDate between :startDate and :endDate group by friend,lendOrBorrow")
    List<CalculateLendBorrowExpenseForFriendDTO> calculateLendBorrowExpenseForSingleFriend(@Param("friendId") Friend friend,
                                                                                     @Param("startDate") Date startDate,
                                                                                     @Param("endDate") Date endDate);

    @Query("select new org.project.expendituremanagement.dto.CalculateLendExpenseForCategoryDTO(l.activityDate as activityDate,l.friend.friendId as friendId,l.amount as amount) from LendBorrowExpense l where category=:categoryId and lendOrBorrow=1 and activityDate between :startDate and :endDate order by activityDate")
    List<CalculateLendExpenseForCategoryDTO> calculateLendExpenseForCategory(@Param("categoryId")Category category,
                                                                             @Param("startDate") Date startDate,
                                                                             @Param("endDate") Date endDate);

}
