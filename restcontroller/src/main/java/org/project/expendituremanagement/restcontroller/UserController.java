package org.project.expendituremanagement.restcontroller;

import org.project.expendituremanagement.constants.API;
import org.project.expendituremanagement.constants.ErrorText;
import org.project.expendituremanagement.constants.SuccessText;
import org.project.expendituremanagement.dto.CredentialDTO;
import org.project.expendituremanagement.dto.StatusResponse;
import org.project.expendituremanagement.dto.UserInformationDTO;
import org.project.expendituremanagement.entity.UserInformation;
import org.project.expendituremanagement.serviceinterface.HttpSessionService;
import org.project.expendituremanagement.serviceinterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(API.USER_PATH)
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private HttpSessionService httpSessionService;

    @RequestMapping(value = API.REGISTER_PATH,method = RequestMethod.POST)
    public ResponseEntity<Object> registerUser(@RequestBody UserInformationDTO userInformationDTO){

        UserInformation userInformation= userService.registerUser(userInformationDTO);
        if(userInformation!=null) {
            StatusResponse statusResponse=new StatusResponse();
            statusResponse.setSuccessMessage(SuccessText.successfullyRegistered+" Your User Id is = "+userInformation.getUserId());

            return new ResponseEntity<>(statusResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(ErrorText.userAlreadyExistText, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> updateUser(@RequestBody UserInformationDTO userInformationDTO,
                                             @RequestHeader(name="userId") String userId){
        UserInformation userInformation=userService.updateUser(userInformationDTO, userId);
        if(userInformation!=null)
            return new ResponseEntity<>(userInformation,HttpStatus.OK);
        return new ResponseEntity<>(ErrorText.somethingWentWrongText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteUser(@RequestHeader(name="userId") String userId){
        if(userService.deleteUser(userId)){
            StatusResponse statusResponse=new StatusResponse();
            statusResponse.setSuccessMessage(SuccessText.successfullyAccountDeleted);

            return new ResponseEntity<>(statusResponse,HttpStatus.OK);
        }
        return new ResponseEntity<>(ErrorText.somethingWentWrongText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value=API.VALIDATE_PATH,method = RequestMethod.POST)
    public ResponseEntity<Object> validateUser(@RequestBody CredentialDTO credentialDTO){
        UserInformation userInformation = userService.validateUser(credentialDTO);
        if(userInformation!=null) {
            UUID sessionId=httpSessionService.createHttpSession(credentialDTO.getUserId());

            HttpHeaders responseHeaders=new HttpHeaders();
            responseHeaders.add("sessionId",sessionId.toString());
            responseHeaders.add("Access-Control-Expose-Headers","sessionId");

            return new ResponseEntity<>(userInformation,responseHeaders,HttpStatus.OK);
        }
        return new ResponseEntity<>(ErrorText.invalidSignInText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value=API.UPDATE_CREDENTIAL,method = RequestMethod.PUT)
    public ResponseEntity<Object> updatePassword(@RequestBody CredentialDTO credentialDTO,
                                                 @RequestHeader(name="userId") String userId){

        credentialDTO.setUserId(userId);
        if(userService.updatePassword(credentialDTO))
        {
            StatusResponse statusResponse=new StatusResponse();
            statusResponse.setSuccessMessage(SuccessText.successfullyPasswordUpdated);

            return new ResponseEntity<>(statusResponse,HttpStatus.OK);
        }
        return new ResponseEntity<>(ErrorText.checkCurrentPasswordText,HttpStatus.BAD_REQUEST);
    }

}