package org.project.expendituremanagement.restcontroller;

import org.project.expendituremanagement.constants.API;
import org.project.expendituremanagement.constants.ErrorText;
import org.project.expendituremanagement.constants.SuccessText;
import org.project.expendituremanagement.dto.FriendDTO;
import org.project.expendituremanagement.dto.StatusResponse;
import org.project.expendituremanagement.entity.Friend;
import org.project.expendituremanagement.serviceinterface.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(API.FRIEND_PATH)
public class FriendController {

    @Autowired
    private FriendService friendService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> createFriend(@RequestBody FriendDTO friendDTO,
                                               @RequestHeader(name = "userId") String userId)
    {
        Friend response=friendService.createFriend(friendDTO,userId);
        if(response!=null)
            return new ResponseEntity<>(response, HttpStatus.OK);
        return new ResponseEntity<>(ErrorText.somethingWentWrongText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getFriends(@RequestHeader(name = "userId") String userId){
        List<Friend> response=friendService.getFriends(userId);
        if(response!=null)
            return new ResponseEntity<>(response,HttpStatus.OK);
        return new ResponseEntity<>(ErrorText.somethingWentWrongText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = API.FRIEND_ID_PARAM_PATH,method = RequestMethod.PUT)
    public ResponseEntity<Object> updateFriend(@RequestBody FriendDTO friendDTO,
                                               @PathVariable(name="friendId")UUID friendId,
                                               @RequestHeader(name = "userId") String userId){
        String ownerId=friendService.getUserIdByFriendId(friendId);
        if(userId.equals(ownerId)) {
            Friend response = friendService.updateFriend(friendDTO, friendId);
            if (response != null)
                return new ResponseEntity<>(response, HttpStatus.OK);
            return new ResponseEntity<>(ErrorText.somethingWentWrongText, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ErrorText.unauthorizedText,HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value=API.FRIEND_ID_PARAM_PATH,method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteFriend(@PathVariable(name="friendId")UUID friendId,
                                               @RequestHeader(name = "userId") String userId){
        String ownerId=friendService.getUserIdByFriendId(friendId);
        if(userId.equals(ownerId)) {
            if (friendService.deleteFriend(friendId)) {
                StatusResponse statusResponse = new StatusResponse();
                statusResponse.setSuccessMessage(SuccessText.successfullyDeleted);

                return new ResponseEntity<>(statusResponse, HttpStatus.OK);
            }
            return new ResponseEntity<>(ErrorText.somethingWentWrongText, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ErrorText.unauthorizedText,HttpStatus.UNAUTHORIZED);
    }
}
