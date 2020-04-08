package org.project.expendituremanagement.serviceimpl;

import org.project.expendituremanagement.dto.FriendDTO;
import org.project.expendituremanagement.entity.Friend;
import org.project.expendituremanagement.entity.UserInformation;
import org.project.expendituremanagement.repository.FriendRepository;
import org.project.expendituremanagement.serviceinterface.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendRepository friendRepository;

    @Override
    public Friend createFriend(FriendDTO friendDTO, String userId) {
        Friend friend =new Friend();
        friend.setFriendName(friendDTO.getFriendName());

        UserInformation userInformation=new UserInformation();
        userInformation.setUserId(userId);

        friend.setUserInformation(userInformation);
        return friendRepository.save(friend);
    }
    @Override
    public List<Friend> getFriends(String userId){
        UserInformation userInformation=new UserInformation();
        userInformation.setUserId(userId);
        return friendRepository.findByUserInformation(userInformation);
    }

    @Override
    public Friend updateFriend(FriendDTO friendDTO,UUID friendId){
        if(friendRepository.existsById(friendId)) {
            Friend friend=friendRepository.getOne(friendId);
            friend.setFriendName(friendDTO.getFriendName());

            return friendRepository.save(friend);
        }
        return null;
    }

    @Override
    public boolean deleteFriend(UUID friendId){
        if(friendRepository.existsById(friendId)) {
            friendRepository.deleteById(friendId);
            return true;
        }
        return false;
    }

    @Override
    public String getUserIdByFriendId(UUID friendId) {
        if(friendRepository.existsById(friendId))
            return friendRepository.getOne(friendId).getUserInformation().getUserId();
        return null;
    }
}
