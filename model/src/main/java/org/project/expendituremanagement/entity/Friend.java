package org.project.expendituremanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "friend_list")
@Data
public class Friend {
    @Id
    @GeneratedValue(generator = "system-uuid")
    private UUID friendId;
    private String friendName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserInformation userInformation;

    @JsonIgnore
    @OneToMany(mappedBy = "friend",cascade = {CascadeType.REMOVE},fetch = FetchType.LAZY)
    List<LendBorrowExpense> lendBorrowExpenses;
}
