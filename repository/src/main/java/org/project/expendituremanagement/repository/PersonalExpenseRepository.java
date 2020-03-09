package org.project.expendituremanagement.repository;

import org.project.expendituremanagement.dto.CalculatePersonalExpenseDTO;
import org.project.expendituremanagement.entity.PersonalExpense;
import org.project.expendituremanagement.entity.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface PersonalExpenseRepository extends JpaRepository<PersonalExpense, UUID> {

    @Query("select peObj from PersonalExpense peObj where userInformation=:userId and activityDate between :startDate and :endDate order by activityDate")
    List<PersonalExpense> getAllPersonalExpensesBetweenDates(@Param("userId") UserInformation userInformation,
                                                             @Param("startDate") Date startDate,
                                                             @Param("endDate") Date endDate);

    @Modifying
    @Query("delete from PersonalExpense where userInformation=:userId and activityDate between :startDate and :endDate")
    void deleteAllPersonalExpensesBetweenDates(@Param("userId") UserInformation userInformation,
                                               @Param("startDate") Date startDate,
                                               @Param("endDate") Date endDate);

    @Query("select new org.project.expendituremanagement.dto.CalculatePersonalExpenseDTO(p.category.categoryId as categoryId,sum(p.amount) as amount) from PersonalExpense p where userInformation=:userId and activityDate between :startDate and :endDate group by category")
     List<CalculatePersonalExpenseDTO> calculatePersonalExpense(@Param("userId") UserInformation userInformation,
                                                               @Param("startDate") Date startDate,
                                                               @Param("endDate") Date endDate);
}
