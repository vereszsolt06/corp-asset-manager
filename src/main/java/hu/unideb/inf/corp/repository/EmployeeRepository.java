package hu.unideb.inf.corp.repository;

import hu.unideb.inf.corp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
