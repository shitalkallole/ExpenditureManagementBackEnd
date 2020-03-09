package org.project.expendituremanagement.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class FinalResultOfLendBorrowExpenseForFriendDTO {
    private UUID friendId;
    private Double lendAmount;
    private Double borrowAmount;
    private Double finalAmount;
}
