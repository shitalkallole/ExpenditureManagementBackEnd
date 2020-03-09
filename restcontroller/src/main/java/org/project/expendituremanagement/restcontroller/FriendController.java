package org.project.expendituremanagement.restcontroller;

import org.project.expendituremanagement.dto.FriendDTO;
import org.project.expendituremanagement.entity.Friend;
import org.project.expendituremanagement.serviceinterface.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @RequestMapping(value ="/{userId}",method = RequestMethod.POST)
    public Friend createFriend(@RequestBody FriendDTO friendDTO,
                               @PathVariable(name = "userId") String userId)
    {
        return friendService.createFriend(friendDTO,userId);
    }

    @RequestMapping(value="/{userId}",method = RequestMethod.GET)
    public List<Friend> getFriends(@PathVariable(name = "userId") String userId){
        return friendService.getFriends(userId);
    }

    @RequestMapping(value = "/{friendId}",method = RequestMethod.PUT)
    public Friend updateFriend(@RequestBody FriendDTO friendDTO,
                               @PathVariable(name="friendId")UUID friendId){
        return friendService.updateFriend(friendDTO, friendId);
    }

    @RequestMapping(value="/{friendId}",method = RequestMethod.DELETE)
    public void deleteFriend(@PathVariable(name="friendId")UUID friendId){
        friendService.deleteFriend(friendId);
    }
}
