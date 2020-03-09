package org.project.expendituremanagement.serviceimpl;

import org.project.expendituremanagement.dto.CredentialDTO;
import org.project.expendituremanagement.dto.UserInformationDTO;
import org.project.expendituremanagement.entity.Credential;
import org.project.expendituremanagement.entity.UserInformation;
import org.project.expendituremanagement.repository.CredentialRepository;
import org.project.expendituremanagement.repository.UserInformationRepository;
import org.project.expendituremanagement.serviceinterface.UserService;
import org.project.expendituremanagement.transformer.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserInformationRepository userInformationRepository;

    @Autowired
    CredentialRepository credentialRepository;

    @Override
    public UserInformation registerUser(UserInformationDTO userInformationDTO) {

        if(!userInformationRepository.existsById(userInformationDTO.getUserId())) {
            UserInformation userInformation = Transformer.transformUserInformationDTOTOUserInformation(userInformationDTO);

            Credential credential = new Credential();
            //credential.setId(userInformation.getUserId());
            credential.setUserInformation(userInformation);
            credential.setUserPassword(userInformationDTO.getUserPassword());

            userInformation.setCredential(credential);

            return userInformationRepository.save(userInformation);
        }
        return null;
    }

    @Override
    public UserInformation updateUser(UserInformationDTO userInformationDTO, String userId) {
        userInformationDTO.setUserId(userId);

        UserInformation userInformation=Transformer.transformUserInformationDTOTOUserInformation(userInformationDTO);

        if(userInformationRepository.existsById(userId))
            return userInformationRepository.save(userInformation);

        return null;
    }

    @Override
    public void deleteUser(String userId) {
        userInformationRepository.deleteById(userId);
    }

    private boolean validateUserCredential(CredentialDTO credentialDTO) {
        if (credentialRepository.existsById(credentialDTO.getUserId())) {
            Credential credential = credentialRepository.getOne(credentialDTO.getUserId());
            if (!credential.getUserPassword().equals(credentialDTO.getCurrentPassword()))
                return false;
            return true;
        }
        return false;
    }
    @Override
    public UserInformation validateUser(CredentialDTO credentialDTO) {
        if(validateUserCredential(credentialDTO))
            return userInformationRepository.getOne(credentialDTO.getUserId());
        return null;
    }

    @Override
    public Boolean updatePassword(CredentialDTO credentialDTO) {
        if(validateUserCredential(credentialDTO))
        {
            Credential credential=new Credential();
            credential.setId(credentialDTO.getUserId());
            credential.setUserPassword(credentialDTO.getNewPassword());

            credentialRepository.save(credential);

            return true;
        }
        return false;
    }
}
