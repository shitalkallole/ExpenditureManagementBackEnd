package org.project.expendituremanagement.restcontroller;

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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/personalexpense")
public class PersonalExpenseController {

    @Autowired
    private PersonalExpenseService personalExpenseService;
    private String errorText="something wrong went";

    @RequestMapping(value="/{userId}",method = RequestMethod.POST)
    public ResponseEntity<Object> createEntryInPersonalExpense(@RequestBody PersonalExpenseDTO personalExpenseDTO,
                                                        @PathVariable(name="userId") String userId){
        PersonalExpense response=personalExpenseService.createEntryInPersonalExpense(personalExpenseDTO, userId);
        if(response!=null) {
            StatusResponse statusResponse=new StatusResponse();
            statusResponse.setSuccessMessage("Successfully Created");
            return new ResponseEntity<>(statusResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(errorText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/show/{startDate}/{endDate}/{userId}",method = RequestMethod.GET)
    public ResponseEntity<Object> getAllPersonalExpensesBetweenDates(@PathVariable(name="startDate") String startDateInString,
                                                                    @PathVariable(name="endDate") String endDateInString,
                                                                    @PathVariable(name="userId") String userID){
        List<PersonalExpense> response=personalExpenseService.getAllPersonalExpensesBetweenDates(startDateInString,endDateInString,userID);
        if(response!=null)
            return new ResponseEntity<>(response,HttpStatus.OK);
        return new ResponseEntity<>(errorText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/delete/{startDate}/{endDate}/{userId}",method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteAllPersonalExpensesBetweenDates(@PathVariable(name="startDate") String startDateInString,
                                                      @PathVariable(name="endDate") String endDateInString,
                                                      @PathVariable(name="userId") String userID){
        if(personalExpenseService.deleteAllPersonalExpensesBetweenDates(startDateInString,endDateInString, userID)){
            StatusResponse statusResponse=new StatusResponse();
            statusResponse.setSuccessMessage("All records between dates deleted successfully");

            return new ResponseEntity<>(statusResponse,HttpStatus.OK);
        }
        return new ResponseEntity<>(errorText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/{transactionId}",method = RequestMethod.PUT)
    public ResponseEntity<Object> updateEntryInPersonalExpense(@RequestBody PersonalExpenseDTO personalExpenseDTO,
                                                        @PathVariable(name="transactionId")UUID transactionId)
    {
        PersonalExpense response=personalExpenseService.updateEntryInPersonalExpense(personalExpenseDTO, transactionId);
        if(response!=null)
            return new ResponseEntity<>(response,HttpStatus.OK);
        return new ResponseEntity<>(errorText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/{transactionId}",method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteEntryFromPersonalExpense(@PathVariable(name="transactionId")UUID transactionId){
        if(personalExpenseService.deleteEntryFromPersonalExpense(transactionId)) {
            StatusResponse statusResponse=new StatusResponse();
            statusResponse.setSuccessMessage("successfully deleted");
            return new ResponseEntity<>(statusResponse,HttpStatus.OK);
        }
        return new ResponseEntity<>(errorText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/calculate/{startDate}/{endDate}/{userId}",method = RequestMethod.GET)
    public ResponseEntity<Object> calculatePersonalExpense(@PathVariable(name="startDate") String startDateInString,
                                                                      @PathVariable(name="endDate") String endDateInString,
                                                                      @PathVariable(name="userId") String userID)
    {

        List<CalculatePersonalExpenseDTO> response= personalExpenseService.calculatePersonalExpense(startDateInString, endDateInString, userID);
        if(response!=null)
            return new ResponseEntity<>(response,HttpStatus.OK);
        return new ResponseEntity<>(errorText,HttpStatus.BAD_REQUEST);
    }
}
