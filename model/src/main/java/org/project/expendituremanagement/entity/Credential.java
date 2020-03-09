package org.project.expendituremanagement.entity;
import lombok.Data;
import org.springframework.context.annotation.EnableMBeanExport;

import javax.persistence.*;

@Entity
@Table(name = "credential")
@Data
public class Credential {
    @Id
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId
    private UserInformation userInformation;
    private String userPassword;
}

