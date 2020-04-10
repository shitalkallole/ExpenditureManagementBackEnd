package org.project.expendituremanagement.restcontroller;

import org.project.expendituremanagement.constants.API;
import org.project.expendituremanagement.constants.ErrorText;
import org.project.expendituremanagement.constants.SuccessText;
import org.project.expendituremanagement.dto.HttpSession;
import org.project.expendituremanagement.dto.StatusResponse;
import org.project.expendituremanagement.dto.ValidateSessionResponse;
import org.project.expendituremanagement.serviceinterface.HttpSessionService;
import org.project.expendituremanagement.serviceinterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(API.SESSION_PATH)
public class HttpSessionController {
    @Autowired
    private UserService userService;
    @Autowired
    private HttpSessionService httpSessionService;

    @RequestMapping(value=API.VALIDATE_PATH,method = RequestMethod.GET)
    public ResponseEntity<Object> validateSession(@RequestHeader("userId") String userId){
        //if api call reaches here means it is already validated through filter
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @RequestMapping(value=API.DELETE_SESSION_PATH,method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteSession(@RequestHeader("userId") String userId){
        if(httpSessionService.deleteHttpSession(userId)){
            StatusResponse statusResponse=new StatusResponse();
            statusResponse.setSuccessMessage(SuccessText.successfullySignedOut);
            return new ResponseEntity<>(statusResponse,HttpStatus.OK);
        }
        return new ResponseEntity<>(ErrorText.somethingWentWrongText,HttpStatus.BAD_REQUEST);
    }
}
