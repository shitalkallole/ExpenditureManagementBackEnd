package org.project.expendituremanagement.restcontroller;

import org.project.expendituremanagement.dto.CalculatePersonalExpenseDTO;
import org.project.expendituremanagement.dto.PersonalExpenseDTO;
import org.project.expendituremanagement.entity.PersonalExpense;
import org.project.expendituremanagement.serviceinterface.PersonalExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/personalexpense")
public class PersonalExpenseController {

    @Autowired
    private PersonalExpenseService personalExpenseService;
    @RequestMapping(value="/{userId}",method = RequestMethod.POST)
    public PersonalExpense createEntryInPersonalExpense(@RequestBody PersonalExpenseDTO personalExpenseDTO,
                                                        @PathVariable(name="userId") String userId){
        return personalExpenseService.createEntryInPersonalExpense(personalExpenseDTO, userId);
    }

    @RequestMapping(value="/show/{startDate}/{endDate}/{userId}",method = RequestMethod.GET)
    public List<PersonalExpense> getAllPersonalExpensesBetweenDates(@PathVariable(name="startDate") String startDateInString,
                                                                    @PathVariable(name="endDate") String endDateInString,
                                                                    @PathVariable(name="userId") String userID){
        return personalExpenseService.getAllPersonalExpensesBetweenDates(startDateInString,endDateInString,userID);
    }

    @RequestMapping(value="/delete/{startDate}/{endDate}/{userId}",method = RequestMethod.DELETE)
    public void deleteAllPersonalExpensesBetweenDates(@PathVariable(name="startDate") String startDateInString,
                                                      @PathVariable(name="endDate") String endDateInString,
                                                      @PathVariable(name="userId") String userID){
        personalExpenseService.deleteAllPersonalExpensesBetweenDates(startDateInString,endDateInString, userID);
    }

    @RequestMapping(value="/{transactionId}",method = RequestMethod.PUT)
    public PersonalExpense updateEntryInPersonalExpense(@RequestBody PersonalExpenseDTO personalExpenseDTO,
                                                        @PathVariable(name="transactionId")UUID transactionId)
    {
        return personalExpenseService.updateEntryInPersonalExpense(personalExpenseDTO, transactionId);
    }

    @RequestMapping(value="/{transactionId}",method = RequestMethod.DELETE)
    public void deleteEntryFromPersonalExpense(@PathVariable(name="transactionId")UUID transactionId){
        personalExpenseService.deleteEntryFromPersonalExpense(transactionId);
    }

    @RequestMapping(value="/calculate/{startDate}/{endDate}/{userId}",method = RequestMethod.GET)
    public List<CalculatePersonalExpenseDTO> calculatePersonalExpense(@PathVariable(name="startDate") String startDateInString,
                                                                      @PathVariable(name="endDate") String endDateInString,
                                                                      @PathVariable(name="userId") String userID)
    {

        return personalExpenseService.calculatePersonalExpense(startDateInString, endDateInString, userID);

    }
}
