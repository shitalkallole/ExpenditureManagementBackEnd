package org.project.expendituremanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "category_list")
@Data
public class Category {
    @Id
    @GeneratedValue(generator = "system-uuid")
    private UUID categoryId;
    private String categoryName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserInformation userInformation;

    @JsonIgnore
    @OneToMany(mappedBy = "category",cascade = {CascadeType.REMOVE},fetch = FetchType.LAZY)
    List<LendBorrowExpense> lendBorrowExpenses;

    @JsonIgnore
    @OneToMany(mappedBy = "category",cascade = {CascadeType.REMOVE},fetch = FetchType.LAZY)
    List<PersonalExpense> personalExpenses;
}
