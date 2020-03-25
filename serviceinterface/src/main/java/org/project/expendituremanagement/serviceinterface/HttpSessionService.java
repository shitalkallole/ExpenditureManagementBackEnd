package org.project.expendituremanagement.serviceinterface;

import org.project.expendituremanagement.dto.HttpSession;
import org.project.expendituremanagement.dto.ValidateSessionResponse;

import java.util.UUID;

public interface HttpSessionService {
    UUID createHttpSession(String userId);
    ValidateSessionResponse validateSession(HttpSession httpSession);
    boolean updateHttpSession(String userId);
    boolean deleteHttpSession(HttpSession httpSession);
}
