package org.project.expendituremanagement.repository;

import org.project.expendituremanagement.entity.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInformationRepository extends JpaRepository<UserInformation,String> {
}
