package org.project.expendituremanagement.dto;


import lombok.Data;
import java.io.Serializable;
import java.util.UUID;

@Data
public class HttpSession implements Serializable {
    private String userId;
    private UUID sessionId;
    private Boolean sessionActiveOrNot;
}
