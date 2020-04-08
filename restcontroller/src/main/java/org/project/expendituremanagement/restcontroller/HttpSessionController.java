package org.project.expendituremanagement.restcontroller;

import org.project.expendituremanagement.dto.HttpSession;
import org.project.expendituremanagement.dto.StatusResponse;
import org.project.expendituremanagement.dto.ValidateSessionResponse;
import org.project.expendituremanagement.serviceinterface.HttpSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")
public class HttpSessionController {
    @Autowired
    private HttpSessionService httpSessionService;
    private String errorText="something went wrong";

    @RequestMapping(value="/validate",method = RequestMethod.POST)
    public ResponseEntity<Object> validateSession(@RequestBody HttpSession httpSession){
        System.out.println("validate session called:");
        if(httpSession.getUserId()==null || httpSession.getSessionId()==null){
            ValidateSessionResponse response=new ValidateSessionResponse();
            response.setSessionActiveOrNot(false);
            response.setMessage("Session Expired");

            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        ValidateSessionResponse response = httpSessionService.validateSession(httpSession);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
/*
    @RequestMapping(value="/deleteSession",method = RequestMethod.POST)
    public ResponseEntity<Object> deleteSession(@RequestBody HttpSession httpSession){
        if(httpSessionService.deleteHttpSession(httpSession)){
            StatusResponse statusResponse=new StatusResponse();
            statusResponse.setSuccessMessage("Successfully signed out");
            return new ResponseEntity<>(statusResponse,HttpStatus.OK);
        }
        return new ResponseEntity<>(errorText,HttpStatus.BAD_REQUEST);
    }*/
    @RequestMapping(value="/deleteSession",method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteSession(@RequestHeader("userId") String userId){
        System.out.println("userId"+userId);
        if(httpSessionService.deleteHttpSession(userId)){
            StatusResponse statusResponse=new StatusResponse();
            statusResponse.setSuccessMessage("Successfully signed out");
            return new ResponseEntity<>(statusResponse,HttpStatus.OK);
        }
        return new ResponseEntity<>(errorText,HttpStatus.BAD_REQUEST);
    }
}
