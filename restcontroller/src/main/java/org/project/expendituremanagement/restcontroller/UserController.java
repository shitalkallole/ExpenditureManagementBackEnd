package org.project.expendituremanagement.restcontroller;

import org.project.expendituremanagement.dto.CredentialDTO;
import org.project.expendituremanagement.dto.UserInformationDTO;
import org.project.expendituremanagement.entity.UserInformation;
import org.project.expendituremanagement.serviceinterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseEntity registerUser(@RequestBody UserInformationDTO userInformationDTO){

        UserInformation userInformation= userService.registerUser(userInformationDTO);
        if(userInformation!=null)
            return new ResponseEntity(userInformation, HttpStatus.OK);
        return new ResponseEntity("User Already exist",HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/{userId}",method = RequestMethod.PUT)
    public UserInformation updateUser(@RequestBody UserInformationDTO userInformationDTO,@PathVariable(name="userId") String userId){
        return userService.updateUser(userInformationDTO, userId);
    }

    @RequestMapping(value="/{userId}",method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable(name="userId") String userId){
        userService.deleteUser(userId);
    }

    @RequestMapping(value="/validate",method = RequestMethod.POST)
    public ResponseEntity validateUser(@RequestBody CredentialDTO credentialDTO){
        UserInformation userInformation = userService.validateUser(credentialDTO);
        if(userInformation!=null)
            return new ResponseEntity(userInformation,HttpStatus.OK);
        return new ResponseEntity("Check user id and password and try again later",HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/updatecredential",method = RequestMethod.PUT)
    public ResponseEntity updatePassword(@RequestBody CredentialDTO credentialDTO){
        if(userService.updatePassword(credentialDTO))
            return new ResponseEntity("updated Password successfully",HttpStatus.OK);
        return new ResponseEntity("Check the current password",HttpStatus.BAD_REQUEST);
    }

}
