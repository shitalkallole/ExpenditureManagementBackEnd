package org.project.expendituremanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "lend_borrow_expense")
@Data
public class LendBorrowExpense {
    @Id
    @GeneratedValue(generator = "system-uuid")
    private UUID transactionId;
    private Date activityDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    private Double amount;
    private String comment;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id")
    private Friend friend;
    private Short lendOrBorrow;
    private Date entryDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserInformation userInformation;
}
