package org.project.expendituremanagement.transformer;

import org.project.expendituremanagement.Utility.Utilities;
import org.project.expendituremanagement.dto.LendBorrowExpenseDTO;
import org.project.expendituremanagement.dto.PersonalExpenseDTO;
import org.project.expendituremanagement.dto.UserInformationDTO;
import org.project.expendituremanagement.entity.*;

import java.util.Date;

public class Transformer {
    public static UserInformation transformUserInformationDTOTOUserInformation(UserInformationDTO userInformationDTO){
        UserInformation userInformation=new UserInformation();
        userInformation.setUserId(userInformationDTO.getUserId());
        userInformation.setUserName(userInformationDTO.getUserName());
        userInformation.setEmailId(userInformationDTO.getEmailId());
        userInformation.setGender(userInformationDTO.getGender());

        return userInformation;
    }

    public static LendBorrowExpense transformLendBorrowExpenseDTOToLendBorrowExpense(LendBorrowExpenseDTO lendBorrowExpenseDTO,String userId){
        LendBorrowExpense lendBorrowExpense=new LendBorrowExpense();

        Date dateWithoutTime= Utilities.convertDateToDateWithoutTime(lendBorrowExpenseDTO.getActivityDate());
        if(dateWithoutTime!=null)
            lendBorrowExpense.setActivityDate(dateWithoutTime);
        else
            return null;

        Category category=new Category();
        category.setCategoryId(lendBorrowExpenseDTO.getCategoryId());
        lendBorrowExpense.setCategory(category);

        lendBorrowExpense.setAmount(lendBorrowExpenseDTO.getAmount());
        lendBorrowExpense.setComment(lendBorrowExpenseDTO.getComment());

        Friend friend=new Friend();
        friend.setFriendId(lendBorrowExpenseDTO.getFriendId());
        lendBorrowExpense.setFriend(friend);

        lendBorrowExpense.setLendOrBorrow(lendBorrowExpenseDTO.getLendOrBorrow());

        dateWithoutTime=Utilities.convertDateToDateWithoutTime(new Date());
        if(dateWithoutTime!=null)
            lendBorrowExpense.setEntryDate(dateWithoutTime);
        else
            return null;

        UserInformation userInformation=new UserInformation();
        userInformation.setUserId(userId);
        lendBorrowExpense.setUserInformation(userInformation);

        return lendBorrowExpense;
    }
    public static PersonalExpense transformPersonalExpenseDTOToPersonalExpense(PersonalExpenseDTO personalExpenseDTO,String userId){
        PersonalExpense personalExpense=new PersonalExpense();

        Date dateWithoutTime=Utilities.convertDateToDateWithoutTime(personalExpenseDTO.getActivityDate());
        if(dateWithoutTime!=null)
            personalExpense.setActivityDate(dateWithoutTime);
        else
            return null;

        Category category=new Category();
        category.setCategoryId(personalExpenseDTO.getCategoryId());
        personalExpense.setCategory(category);

        personalExpense.setAmount(personalExpenseDTO.getAmount());
        personalExpense.setComment(personalExpenseDTO.getComment());

        dateWithoutTime=Utilities.convertDateToDateWithoutTime(new Date());
        if(dateWithoutTime!=null)
            personalExpense.setEntryDate(dateWithoutTime);
        else
            return null;

        UserInformation userInformation=new UserInformation();
        userInformation.setUserId(userId);
        personalExpense.setUserInformation(userInformation);

        return personalExpense;
    }
}
