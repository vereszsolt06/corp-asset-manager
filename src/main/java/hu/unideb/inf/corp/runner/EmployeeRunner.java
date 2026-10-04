package hu.unideb.inf.corp.runner;

import com.github.javafaker.Faker;
import hu.unideb.inf.corp.model.Employee;
import hu.unideb.inf.corp.repository.EmployeeRepository;
import hu.unideb.inf.corp.util.EmployeeUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(1)
public class EmployeeRunner implements CommandLineRunner {
    private static final Faker FAKER = new Faker();
    private final EmployeeRepository repository;

    @Override
    @Transactional
    public void run(String... args) {
        for (int i = 0; i < 50; i++) {
            repository.save(Employee.builder()
                    .employeeId(EmployeeUtils.nextEmployeeId())
                    .name(FAKER.name().fullName())
                    .email(FAKER.internet().emailAddress())
                    .department(FAKER.commerce().department())
                    .createdAt(OffsetDateTime.now())
                    .updatedAt(OffsetDateTime.now())
                    .build());
        }
        log.info("Employees inserted: {}", repository.count());
    }
}