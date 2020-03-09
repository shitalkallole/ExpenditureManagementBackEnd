package org.project.expendituremanagement.dto;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
public class FinalResultOfLendExpenseForCategoryDTO {
    private Date date;
    private Map<UUID,Double> friendMap=new HashMap<>();
    private Double totalAmount;
}
