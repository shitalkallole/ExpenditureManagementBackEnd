package org.project.expendituremanagement.restcontroller;

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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;
    private String errorText="something wrong went";

    @RequestMapping(value ="/{userId}",method = RequestMethod.POST)
    public ResponseEntity<Object> createFriend(@RequestBody FriendDTO friendDTO,
                               @PathVariable(name = "userId") String userId)
    {
        Friend response=friendService.createFriend(friendDTO,userId);
        if(response!=null)
            return new ResponseEntity<>(response, HttpStatus.OK);
        return new ResponseEntity<>(errorText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/{userId}",method = RequestMethod.GET)
    public ResponseEntity<Object> getFriends(@PathVariable(name = "userId") String userId){
        List<Friend> response=friendService.getFriends(userId);
        if(response!=null)
            return new ResponseEntity<>(response,HttpStatus.OK);
        return new ResponseEntity<>(errorText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{friendId}",method = RequestMethod.PUT)
    public ResponseEntity<Object> updateFriend(@RequestBody FriendDTO friendDTO,
                               @PathVariable(name="friendId")UUID friendId){
        Friend response=friendService.updateFriend(friendDTO, friendId);
        if(response!=null)
            return new ResponseEntity<>(response,HttpStatus.OK);
        return new ResponseEntity<>(errorText,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/{friendId}",method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteFriend(@PathVariable(name="friendId")UUID friendId){
        if(friendService.deleteFriend(friendId)){
            StatusResponse statusResponse=new StatusResponse();
            statusResponse.setSuccessMessage("Deleted successfully");

            return new ResponseEntity<>(statusResponse,HttpStatus.OK);
        }
        return new ResponseEntity<>(errorText,HttpStatus.BAD_REQUEST);
    }
}
