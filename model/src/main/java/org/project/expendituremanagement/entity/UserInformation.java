package org.project.expendituremanagement.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_information")
@Data
public class UserInformation {
    @Id
    private String userId;
    private String userName;
    private String emailId;
    private String gender;

    @JsonIgnore
    @OneToOne(mappedBy = "userInformation",cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    Credential credential;

    @JsonIgnore
    @OneToMany(mappedBy = "userInformation",cascade = {CascadeType.REMOVE},fetch = FetchType.LAZY)
    List<Friend> friends;

    @JsonIgnore
    @OneToMany(mappedBy = "userInformation",cascade = {CascadeType.REMOVE},fetch = FetchType.LAZY)
    List<Category> categories;

}