package org.project.expendituremanagement.repository;

import org.project.expendituremanagement.entity.Friend;
import org.project.expendituremanagement.entity.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FriendRepository extends JpaRepository<Friend, UUID> {
    List<Friend> findByUserInformation(UserInformation userInformation);
}
