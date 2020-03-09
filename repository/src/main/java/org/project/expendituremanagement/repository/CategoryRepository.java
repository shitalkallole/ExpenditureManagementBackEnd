package org.project.expendituremanagement.repository;

import org.project.expendituremanagement.entity.Category;
import org.project.expendituremanagement.entity.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findByUserInformation(UserInformation userInformation);
}
