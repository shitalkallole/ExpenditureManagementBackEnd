package org.project.expendituremanagement.serviceinterface;

import org.project.expendituremanagement.dto.CalculatePersonalExpenseDTO;
import org.project.expendituremanagement.dto.PersonalExpenseDTO;
import org.project.expendituremanagement.entity.PersonalExpense;

import java.util.List;
import java.util.UUID;

public interface PersonalExpenseService {
    PersonalExpense createEntryInPersonalExpense(PersonalExpenseDTO personalExpenseDTO,String userId);
    List<PersonalExpense> getAllPersonalExpensesBetweenDates(String startDateInString,String endDateInString,String userId);
    boolean deleteAllPersonalExpensesBetweenDates(String startDateInString,String endDateInString,String userId);
    PersonalExpense updateEntryInPersonalExpense(PersonalExpenseDTO personalExpenseDTO, UUID transactionId);
    boolean deleteEntryFromPersonalExpense(UUID transactionId);
    List<CalculatePersonalExpenseDTO> calculatePersonalExpense(String startDateInString,String endDateInString,String userId);
}
