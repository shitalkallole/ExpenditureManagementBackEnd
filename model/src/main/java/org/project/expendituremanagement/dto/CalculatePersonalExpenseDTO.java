package org.project.expendituremanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;


@Data
@AllArgsConstructor
public class CalculatePersonalExpenseDTO {
    private UUID categoryId;
    private Double amount;
}
