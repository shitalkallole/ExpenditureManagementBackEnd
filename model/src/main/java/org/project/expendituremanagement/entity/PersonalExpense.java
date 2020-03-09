package org.project.expendituremanagement.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "personal_expense")
@Data
public class PersonalExpense {
    @Id
    @GeneratedValue(generator = "system-uuid")
    private UUID transactionId;
    private Date activityDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    private Double amount;
    private String comment;
    private Date entryDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserInformation userInformation;
}
