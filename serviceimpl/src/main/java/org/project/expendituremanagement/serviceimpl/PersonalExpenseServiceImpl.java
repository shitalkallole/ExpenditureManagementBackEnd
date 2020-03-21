package org.project.expendituremanagement.serviceimpl;

import org.project.expendituremanagement.Utility.Utilities;
import org.project.expendituremanagement.dto.CalculatePersonalExpenseDTO;
import org.project.expendituremanagement.dto.PersonalExpenseDTO;
import org.project.expendituremanagement.entity.Category;
import org.project.expendituremanagement.entity.PersonalExpense;
import org.project.expendituremanagement.entity.UserInformation;
import org.project.expendituremanagement.repository.PersonalExpenseRepository;
import org.project.expendituremanagement.serviceinterface.PersonalExpenseService;
import org.project.expendituremanagement.transformer.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PersonalExpenseServiceImpl implements PersonalExpenseService {

    @Autowired
    private PersonalExpenseRepository personalExpenseRepository;

    @Override
    public PersonalExpense createEntryInPersonalExpense(PersonalExpenseDTO personalExpenseDTO, String userId) {
        PersonalExpense personalExpense= Transformer.transformPersonalExpenseDTOToPersonalExpense(personalExpenseDTO, userId);
        if(personalExpense!=null)
            return personalExpenseRepository.save(personalExpense);
        return null;
    }

    @Override
    public List<PersonalExpense> getAllPersonalExpensesBetweenDates(String startDateInString,String endDateInString, String userId) {
        UserInformation userInformation=new UserInformation();
        userInformation.setUserId(userId);

        Date startDate=Utilities.convertDateInStringToDate(startDateInString);
        Date endDate=Utilities.convertDateInStringToDate(endDateInString);

        if(startDate!=null && endDate!=null)
            return personalExpenseRepository.getAllPersonalExpensesBetweenDates(userInformation, Utilities.convertDateToDateWithoutTime(startDate),Utilities.convertDateToDateWithoutTime(endDate));
        return null;
    }

    @Transactional
    @Override
    public boolean deleteAllPersonalExpensesBetweenDates(String startDateInString,String endDateInString,String userId) {
        UserInformation userInformation = new UserInformation();
        userInformation.setUserId(userId);

        Date startDate = Utilities.convertDateInStringToDate(startDateInString);
        Date endDate = Utilities.convertDateInStringToDate(endDateInString);

        if (startDate != null && endDate != null) {
            personalExpenseRepository.deleteAllPersonalExpensesBetweenDates(userInformation, Utilities.convertDateToDateWithoutTime(startDate), Utilities.convertDateToDateWithoutTime(endDate));
            return true;
        }
        return false;
    }
    @Override
    public PersonalExpense updateEntryInPersonalExpense(PersonalExpenseDTO personalExpenseDTO, UUID transactionId) {
        if(personalExpenseRepository.existsById(transactionId))
        {
            PersonalExpense personalExpense=personalExpenseRepository.getOne(transactionId);
            personalExpense.setActivityDate(Utilities.convertDateToDateWithoutTime(personalExpenseDTO.getActivityDate()));

            Category category=new Category();
            category.setCategoryId(personalExpenseDTO.getCategoryId());
            personalExpense.setCategory(category);

            personalExpense.setAmount(personalExpenseDTO.getAmount());
            personalExpense.setComment(personalExpenseDTO.getComment());

            return personalExpenseRepository.save(personalExpense);
        }
        return null;
    }

    @Override
    public boolean deleteEntryFromPersonalExpense(UUID transactionId) {
        if(personalExpenseRepository.existsById(transactionId)) {
            personalExpenseRepository.deleteById(transactionId);
            return true;
        }
        return false;
    }

    @Override
    public List<CalculatePersonalExpenseDTO> calculatePersonalExpense(String startDateInString, String endDateInString, String userId) {

        UserInformation userInformation=new UserInformation();
        userInformation.setUserId(userId);

        Date startDate=Utilities.convertDateInStringToDate(startDateInString);
        Date endDate=Utilities.convertDateInStringToDate(endDateInString);

        if(startDate!=null && endDate!=null)
            return personalExpenseRepository.calculatePersonalExpense(userInformation,Utilities.convertDateToDateWithoutTime(startDate),Utilities.convertDateToDateWithoutTime(endDate));
        return null;
    }

}
