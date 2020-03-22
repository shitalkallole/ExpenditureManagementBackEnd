package org.project.expendituremanagement.serviceinterface;

import org.project.expendituremanagement.dto.CredentialDTO;
import org.project.expendituremanagement.dto.UserInformationDTO;
import org.project.expendituremanagement.entity.UserInformation;
import org.springframework.stereotype.Service;


public interface UserService {
    UserInformation registerUser(UserInformationDTO userInformationDTO);
    UserInformation updateUser(UserInformationDTO userInformationDTO,String userId);
    boolean deleteUser(String userId);

    UserInformation validateUser(CredentialDTO credentialDTO);
    Boolean updatePassword(CredentialDTO credentialDTO);
}
