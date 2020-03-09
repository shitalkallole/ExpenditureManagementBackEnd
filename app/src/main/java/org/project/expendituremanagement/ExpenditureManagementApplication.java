package org.project.expendituremanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = "org.project.expendituremanagement.*")
@EntityScan(basePackages = {"org.project.expendituremanagement.entity"})
@EnableJpaRepositories(basePackages = {"org.project.expendituremanagement.repository"})
public class ExpenditureManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenditureManagementApplication.class, args);
	}

}
