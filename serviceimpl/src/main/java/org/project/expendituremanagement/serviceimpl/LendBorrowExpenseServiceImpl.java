package org.project.expendituremanagement.serviceimpl;

import org.project.expendituremanagement.Utility.Utilities;
import org.project.expendituremanagement.dto.*;
import org.project.expendituremanagement.entity.Category;
import org.project.expendituremanagement.entity.Friend;
import org.project.expendituremanagement.entity.LendBorrowExpense;
import org.project.expendituremanagement.entity.UserInformation;
import org.project.expendituremanagement.repository.LendBorrowExpenseRepository;
import org.project.expendituremanagement.serviceinterface.FriendService;
import org.project.expendituremanagement.serviceinterface.LendBorrowExpenseService;
import org.project.expendituremanagement.transformer.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class LendBorrowExpenseServiceImpl implements LendBorrowExpenseService {
    @Autowired
    private LendBorrowExpenseRepository lendBorrowExpenseRepository;

    @Autowired
    private FriendService friendService;

    @Override
    public LendBorrowExpense createEntryInLendBorrowExpense(LendBorrowExpenseDTO lendBorrowExpenseDTO,String userId) {
        LendBorrowExpense lendBorrowExpense= Transformer.transformLendBorrowExpenseDTOToLendBorrowExpense(lendBorrowExpenseDTO,userId);
        if(lendBorrowExpense!=null)
            return lendBorrowExpenseRepository.save(lendBorrowExpense);
        return null;
    }

    @Override
    public List<LendBorrowExpense> getAllLendBorrowExpensesBetweenDates(String startDateInString, String endDateInString, String userId) {
        UserInformation userInformation=new UserInformation();
        userInformation.setUserId(userId);

        Date startDate= Utilities.convertDateInStringToDate(startDateInString);
        Date endDate=Utilities.convertDateInStringToDate(endDateInString);

        if(startDate!=null && endDate!=null)
            return lendBorrowExpenseRepository.getAllLendBorrowExpensesBetweenDates(userInformation,Utilities.convertDateToDateWithoutTime(startDate),Utilities.convertDateToDateWithoutTime(endDate));
        return null;
    }

    @Transactional
    @Override
    public boolean deleteAllLendBorrowExpensesBetweenDates(String startDateInString, String endDateInString, String userId) {
        UserInformation userInformation=new UserInformation();
        userInformation.setUserId(userId);

        Date startDate=Utilities.convertDateInStringToDate(startDateInString);
        Date endDate=Utilities.convertDateInStringToDate(endDateInString);

        if(startDate!=null && endDate!=null) {
            lendBorrowExpenseRepository.deleteAllLendBorrowExpensesBetweenDates(userInformation, Utilities.convertDateToDateWithoutTime(startDate), Utilities.convertDateToDateWithoutTime(endDate));
            return true;
        }
        return false;
    }

    @Override
    public LendBorrowExpense updateEntryInLendBorrowExpense(LendBorrowExpenseDTO lendBorrowExpenseDTO, UUID transactionId) {
        if(lendBorrowExpenseRepository.existsById(transactionId))
        {
            LendBorrowExpense lendBorrowExpense=lendBorrowExpenseRepository.getOne(transactionId);
            lendBorrowExpense.setActivityDate(Utilities.convertDateToDateWithoutTime(lendBorrowExpenseDTO.getActivityDate()));

            Category category=new Category();
            category.setCategoryId(lendBorrowExpenseDTO.getCategoryId());
            lendBorrowExpense.setCategory(category);

            lendBorrowExpense.setAmount(lendBorrowExpenseDTO.getAmount());
            lendBorrowExpense.setComment(lendBorrowExpenseDTO.getComment());

            Friend friend=new Friend();
            friend.setFriendId(lendBorrowExpenseDTO.getFriendId());
            lendBorrowExpense.setFriend(friend);

            lendBorrowExpense.setLendOrBorrow(lendBorrowExpenseDTO.getLendOrBorrow());

            return lendBorrowExpenseRepository.save(lendBorrowExpense);
        }
        return null;
    }

    @Override
    public boolean deleteEntryFromLendBorrowExpense(UUID transactionId) {
        if(lendBorrowExpenseRepository.existsById(transactionId)) {
            lendBorrowExpenseRepository.deleteById(transactionId);
            return true;
        }
        return false;
    }

    @Override
    public List<LendBorrowExpense> getAllLendBorrowExpensesOfFriendBetweenDates(String startDateInString, String endDateInString, UUID friendId, String userId) {
        UserInformation userInformation=new UserInformation();
        userInformation.setUserId(userId);

        Date startDate= Utilities.convertDateInStringToDate(startDateInString);
        Date endDate=Utilities.convertDateInStringToDate(endDateInString);

        if(startDate!=null && endDate!=null){

            Friend friend=new Friend();
            friend.setFriendId(friendId);

            return lendBorrowExpenseRepository.getAllLendBorrowExpensesOfFriendBetweenDates(userInformation,Utilities.convertDateToDateWithoutTime(startDate),Utilities.convertDateToDateWithoutTime(endDate),friend);
        }
        return null;
    }

    @Transactional
    @Override
    public boolean deleteAllLendBorrowExpensesOfFriendBetweenDates(String startDateInString, String endDateInString, UUID friendId, String userId) {
        UserInformation userInformation=new UserInformation();
        userInformation.setUserId(userId);

        Date startDate= Utilities.convertDateInStringToDate(startDateInString);
        Date endDate=Utilities.convertDateInStringToDate(endDateInString);

        if(startDate!=null && endDate!=null) {

            Friend friend = new Friend();
            friend.setFriendId(friendId);

            lendBorrowExpenseRepository.deleteAllLendBorrowExpensesOfFriendBetweenDates(userInformation,Utilities.convertDateToDateWithoutTime(startDate),Utilities.convertDateToDateWithoutTime(endDate),friend);
            return true;
        }
        return false;
    }


    @Override
    public List<FinalResultOfLendBorrowExpenseForFriendDTO> calculateLendBorrowExpenseForEachFriend(String startDateInString, String endDateInString, String userId) {
        UserInformation userInformation=new UserInformation();
        userInformation.setUserId(userId);

        Date startDate= Utilities.convertDateInStringToDate(startDateInString);
        Date endDate=Utilities.convertDateInStringToDate(endDateInString);

        if(startDate==null || endDate==null)
            return null;

        List<CalculateLendBorrowExpenseForFriendDTO> resultFromDb;
        resultFromDb=lendBorrowExpenseRepository.calculateLendBorrowExpenseForEachFriend(userInformation,Utilities.convertDateToDateWithoutTime(startDate),Utilities.convertDateToDateWithoutTime(endDate));

        List<Friend> friends=friendService.getFriends(userId);

        Map<UUID,Double> lendMap=new HashMap<>();
        Map<UUID,Double> borrowMap=new HashMap<>();

        List<FinalResultOfLendBorrowExpenseForFriendDTO> result=new ArrayList<>();

        resultFromDb.stream().forEach(record->{
            if(record.getLendOrBorrow()==1)
                lendMap.put(record.getFriendId(),record.getAmount());
            else
                borrowMap.put(record.getFriendId(),record.getAmount());
        });
        friends.stream().map(friend -> friend.getFriendId()).forEach(friendId->{
            Double lendAmount=0.0;
            Double borrowAmount=0.0;
            Double finalAmount;

            if(lendMap.get(friendId)!=null)
                lendAmount=lendMap.get(friendId);

            if(borrowMap.get(friendId)!=null)
                borrowAmount=borrowMap.get(friendId);

            finalAmount=lendAmount-borrowAmount;

            FinalResultOfLendBorrowExpenseForFriendDTO finalResultOfLendBorrowExpenseForFriendDTO =new FinalResultOfLendBorrowExpenseForFriendDTO();
            finalResultOfLendBorrowExpenseForFriendDTO.setFriendId(friendId);
            finalResultOfLendBorrowExpenseForFriendDTO.setLendAmount(lendAmount);
            finalResultOfLendBorrowExpenseForFriendDTO.setBorrowAmount(borrowAmount);
            finalResultOfLendBorrowExpenseForFriendDTO.setFinalAmount(finalAmount);

            result.add(finalResultOfLendBorrowExpenseForFriendDTO);
        });
        return result;
    }

    @Override
    public FinalResultOfLendBorrowExpenseForFriendDTO calculateLendBorrowExpenseForSingleFriend(String startDateInString, String endDateInString, UUID friendId) {
        Date startDate= Utilities.convertDateInStringToDate(startDateInString);
        Date endDate=Utilities.convertDateInStringToDate(endDateInString);

        if(startDate==null || endDate==null)
            return null;

        Friend friend=new Friend();
        friend.setFriendId(friendId);

        List<CalculateLendBorrowExpenseForFriendDTO> resultFromDb;
        resultFromDb=lendBorrowExpenseRepository.calculateLendBorrowExpenseForSingleFriend(friend,Utilities.convertDateToDateWithoutTime(startDate),Utilities.convertDateToDateWithoutTime(endDate));

        Double lendAmount=0.0;
        Double borrowAmount=0.0;
        Double finalAmount;

        FinalResultOfLendBorrowExpenseForFriendDTO finalResultOfLendBorrowExpenseForFriendDTO=new FinalResultOfLendBorrowExpenseForFriendDTO();
        finalResultOfLendBorrowExpenseForFriendDTO.setFriendId(friendId);
        finalResultOfLendBorrowExpenseForFriendDTO.setLendAmount(lendAmount);
        finalResultOfLendBorrowExpenseForFriendDTO.setBorrowAmount(borrowAmount);

        if(resultFromDb.size()!=0)
        {
            resultFromDb.stream().forEach(record->{
                if(record.getLendOrBorrow()==1)
                    finalResultOfLendBorrowExpenseForFriendDTO.setLendAmount(record.getAmount());
                else
                    finalResultOfLendBorrowExpenseForFriendDTO.setBorrowAmount(record.getAmount());
            });
        }

        finalAmount=finalResultOfLendBorrowExpenseForFriendDTO.getLendAmount()-finalResultOfLendBorrowExpenseForFriendDTO.getBorrowAmount();
        finalResultOfLendBorrowExpenseForFriendDTO.setFinalAmount(finalAmount);

        return finalResultOfLendBorrowExpenseForFriendDTO;
    }

    @Override
    public List<FinalResultOfLendExpenseForCategoryDTO> calculateLendExpenseForCategory(String startDateInString, String endDateInString, UUID categoryId,String userId) {
        Date startDate= Utilities.convertDateInStringToDate(startDateInString);
        Date endDate=Utilities.convertDateInStringToDate(endDateInString);

        if(startDate==null || endDate==null)
            return null;

        Category category=new Category();
        category.setCategoryId(categoryId);

        List<CalculateLendExpenseForCategoryDTO> resultFromDb;
        resultFromDb =lendBorrowExpenseRepository.calculateLendExpenseForCategory(category,Utilities.convertDateToDateWithoutTime(startDate),Utilities.convertDateToDateWithoutTime(endDate));

        List<Friend> friends=friendService.getFriends(userId);

        Map<Date, FinalResultOfLendExpenseForCategoryDTO> result=new HashMap<>();
        List<Date> keys=new ArrayList<>();//this holds the keys in inserted order manner(preserves the order coz .it is used coz map doesn't preserves the insertion order.we want to show on screen in ascending order of date that is why it is used)

        resultFromDb.stream().forEach(record->{
            Date tempDate=Utilities.convertDateToDateWithoutTime(record.getActivityDate());
            UUID friendId=record.getFriendId();
            Double amount=record.getAmount();


            if(result.get(tempDate)==null){
                keys.add(tempDate);

                FinalResultOfLendExpenseForCategoryDTO finalResultOfLendExpenseForCategoryDTO=new FinalResultOfLendExpenseForCategoryDTO();
                finalResultOfLendExpenseForCategoryDTO.setDate(tempDate);

                friends.stream().map(friend -> friend.getFriendId()).forEach(id->{
                    finalResultOfLendExpenseForCategoryDTO.getFriendMap().put(id,0.0);
                });
                finalResultOfLendExpenseForCategoryDTO.setTotalAmount(0.0);

                finalResultOfLendExpenseForCategoryDTO.getFriendMap().put(friendId,amount);
                finalResultOfLendExpenseForCategoryDTO.setTotalAmount(amount);

                result.put(tempDate,finalResultOfLendExpenseForCategoryDTO);
            }
            else{
                Double prevAmount;

                FinalResultOfLendExpenseForCategoryDTO finalResultOfLendExpenseForCategoryDTO=result.get(tempDate);

                prevAmount = finalResultOfLendExpenseForCategoryDTO.getFriendMap().get(friendId);
                prevAmount+=amount;
                finalResultOfLendExpenseForCategoryDTO.getFriendMap().put(friendId,prevAmount);

                prevAmount = finalResultOfLendExpenseForCategoryDTO.getTotalAmount();
                prevAmount+=amount;
                finalResultOfLendExpenseForCategoryDTO.setTotalAmount(prevAmount);
            }
        });

        List<FinalResultOfLendExpenseForCategoryDTO> finalResult=new ArrayList<>();

        keys.stream().forEach(d->{
            finalResult.add(result.get(d));
        });
        return finalResult;
    }

    @Override
    public String getUserIdBy(UUID transactionId) {
        if(lendBorrowExpenseRepository.existsById(transactionId))
            return lendBorrowExpenseRepository.getOne(transactionId).getUserInformation().getUserId();
        return null;
    }
}
