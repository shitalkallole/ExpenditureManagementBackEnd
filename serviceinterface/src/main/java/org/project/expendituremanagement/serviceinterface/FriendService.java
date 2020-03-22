package org.project.expendituremanagement.serviceinterface;

import org.project.expendituremanagement.dto.FriendDTO;
import org.project.expendituremanagement.entity.Friend;

import java.util.List;
import java.util.UUID;

public interface FriendService {
    Friend createFriend(FriendDTO friendDTO, String userId);
    List<Friend> getFriends(String userId);
    Friend updateFriend(FriendDTO friendDTO,UUID friendId);
    boolean deleteFriend(UUID friendId);
}
