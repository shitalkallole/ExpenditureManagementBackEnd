package org.project.expendituremanagement.repository;

import org.project.expendituremanagement.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepository extends JpaRepository<Credential,String> {
}
