package org.project.expendituremanagement.restcontroller;

import org.project.expendituremanagement.constants.API;
import org.project.expendituremanagement.constants.ErrorText;
import org.project.expendituremanagement.constants.SuccessText;
import org.project.expendituremanagement.dto.CalculatePersonalExpenseDTO;
import org.project.expendituremanagement.dto.PersonalExpenseDTO;
import org.project.expendituremanagement.dto.StatusResponse;
import org.project.expendituremanagement.entity.PersonalExpense;
import org.project.expendituremanagement.serviceinterface.PersonalExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(API.PERSONAL_EXPENSE_PATH)
public class PersonalExpenseController {

    @Autowired
    private PersonalExpenseService personalExpenseService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> createEntryInPersonalExpense(@RequestBody PersonalExpenseDTO personalExpenseDTO,@RequestHeader(name="userId") String userId){
        PersonalExpense response=personalExpenseService.createEntryInPersonalExpense(personalExpenseDTO, userId);
        if(response!=null) {
            StatusResponse statusResponse=new StatusResponse();
            statusResponse.setSuccessMessage(SuccessText.successfullyCreated);
            return new ResponseEntity<>(statusResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(ErrorText.somethingWentWrongText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value=API.SHOW_PATH+API.START_DATE_PARAM_PATH+API.END_DATE_PARAM_PATH,method = RequestMethod.GET)
    public ResponseEntity<Object> getAllPersonalExpensesBetweenDates(@PathVariable(name="startDate") String startDateInString,
                                                                     @PathVariable(name="endDate") String endDateInString,
                                                                     @RequestHeader(name="userId") String userId){
        List<PersonalExpense> response=personalExpenseService.getAllPersonalExpensesBetweenDates(startDateInString,endDateInString,userId);
        if(response!=null)
            return new ResponseEntity<>(response,HttpStatus.OK);
        return new ResponseEntity<>(ErrorText.somethingWentWrongText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value=API.DELETE_PATH+API.START_DATE_PARAM_PATH+API.END_DATE_PARAM_PATH,method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteAllPersonalExpensesBetweenDates(@PathVariable(name="startDate") String startDateInString,
                                                                        @PathVariable(name="endDate") String endDateInString,
                                                                        @RequestHeader(name="userId") String userId){
        if(personalExpenseService.deleteAllPersonalExpensesBetweenDates(startDateInString,endDateInString, userId)){
            StatusResponse statusResponse=new StatusResponse();
            statusResponse.setSuccessMessage(SuccessText.allRecordsBetweenDatesSuccessfullyDeleted);

            return new ResponseEntity<>(statusResponse,HttpStatus.OK);
        }
        return new ResponseEntity<>(ErrorText.somethingWentWrongText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value=API.TRANSACTION_ID_PARAM_PATH,method = RequestMethod.PUT)
    public ResponseEntity<Object> updateEntryInPersonalExpense(@RequestBody PersonalExpenseDTO personalExpenseDTO,
                                                               @PathVariable(name="transactionId")UUID transactionId,
                                                               @RequestHeader(name="userId") String userId) {
        String ownerId = personalExpenseService.getUserByTransactionId(transactionId);
        if(userId.equals(ownerId)) {
            PersonalExpense response = personalExpenseService.updateEntryInPersonalExpense(personalExpenseDTO, transactionId);
            if (response != null)
                return new ResponseEntity<>(response, HttpStatus.OK);
            return new ResponseEntity<>(ErrorText.somethingWentWrongText, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ErrorText.unauthorizedText,HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value=API.TRANSACTION_ID_PARAM_PATH,method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteEntryFromPersonalExpense(@PathVariable(name="transactionId")UUID transactionId,
                                                                 @RequestHeader(name="userId") String userId){
        String ownerId = personalExpenseService.getUserByTransactionId(transactionId);
        if(userId.equals(ownerId)) {
            if (personalExpenseService.deleteEntryFromPersonalExpense(transactionId)) {
                StatusResponse statusResponse = new StatusResponse();
                statusResponse.setSuccessMessage(SuccessText.successfullyDeleted);
                return new ResponseEntity<>(statusResponse, HttpStatus.OK);
            }
            return new ResponseEntity<>(ErrorText.somethingWentWrongText, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ErrorText.unauthorizedText,HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value=API.CALCULATE_PATH+API.START_DATE_PARAM_PATH+API.END_DATE_PARAM_PATH,method = RequestMethod.GET)
    public ResponseEntity<Object> calculatePersonalExpense(@PathVariable(name="startDate") String startDateInString,
                                                           @PathVariable(name="endDate") String endDateInString,
                                                           @RequestHeader(name="userId") String userId)
    {

        List<CalculatePersonalExpenseDTO> response= personalExpenseService.calculatePersonalExpense(startDateInString, endDateInString, userId);
        if(response!=null)
            return new ResponseEntity<>(response,HttpStatus.OK);
        return new ResponseEntity<>(ErrorText.somethingWentWrongText,HttpStatus.BAD_REQUEST);
    }
}
