package org.project.expendituremanagement.dto;

import lombok.Data;

@Data
public class CredentialDTO {
    private String userId;
    private String currentPassword;
    private String newPassword;
}
