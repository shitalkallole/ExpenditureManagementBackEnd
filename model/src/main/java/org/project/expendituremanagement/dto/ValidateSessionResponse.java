package org.project.expendituremanagement.dto;

import lombok.Data;

@Data
public class ValidateSessionResponse {
    private Boolean sessionActiveOrNot;
    private String message;
}
