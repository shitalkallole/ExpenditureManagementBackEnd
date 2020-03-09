package org.project.expendituremanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CalculateLendExpenseForCategoryDTO {
    private Date activityDate;
    private UUID friendId;
    private Double amount;
}
