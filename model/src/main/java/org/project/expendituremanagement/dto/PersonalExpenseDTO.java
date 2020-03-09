package org.project.expendituremanagement.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class PersonalExpenseDTO {
    private Date activityDate;
    private UUID categoryId;
    private Double amount;
    private String comment;
}
