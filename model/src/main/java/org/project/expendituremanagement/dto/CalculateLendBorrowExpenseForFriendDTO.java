package org.project.expendituremanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CalculateLendBorrowExpenseForFriendDTO {
    private UUID friendId;
    private Double amount;
    private Short lendOrBorrow;
}
