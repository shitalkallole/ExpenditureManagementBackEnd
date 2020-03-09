package org.project.expendituremanagement.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class LendBorrowExpenseDTO {
    private Date activityDate;
    private UUID categoryId;
    private Double amount;
    private String comment;
    private UUID friendId;
    private Short lendOrBorrow;
}
