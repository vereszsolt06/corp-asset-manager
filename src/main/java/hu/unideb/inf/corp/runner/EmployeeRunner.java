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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(1)
public class EmployeeRunner implements CommandLineRunner {
    private static final Faker FAKER = new Faker();
    private final List<String> departments = List.of("Consumer Electronics", "Smart Home", "Wearable Tech", "Activewear", "Home Appliances", "Pet Supplies");
    private final Random RANDOM = new Random();
    private final EmployeeRepository repository;

    @Override
    @Transactional
    public void run(String... args) {
        for (int i = 0; i < 50; i++) {
            String firstName = FAKER.name().firstName();
            String lastName = FAKER.name().lastName();
            repository.save(Employee.builder()
                    .employeeId(EmployeeUtils.nextEmployeeId())
                    .name(firstName + " " + lastName)
                    .email(FAKER.internet().emailAddress(firstName.toLowerCase() + "." + lastName.toLowerCase()))
                    .department(departments.get(RANDOM.nextInt(departments.size())))
                    .createdAt(OffsetDateTime.now())
                    .updatedAt(OffsetDateTime.now())
                    .build());
        }
        log.info("Employees inserted: {}", repository.count());
    }
}