package hu.unideb.inf.corp.runner;

import com.github.javafaker.Faker;
import hu.unideb.inf.corp.model.Asset;
import hu.unideb.inf.corp.model.AssetStatus;
import hu.unideb.inf.corp.model.AssetType;
import hu.unideb.inf.corp.model.Employee;
import hu.unideb.inf.corp.repository.AssetRepository;
import hu.unideb.inf.corp.repository.EmployeeRepository;
import hu.unideb.inf.corp.util.AssetUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(2)
public class AssetRunner implements CommandLineRunner {
    private static final Faker FAKER = new Faker();
    private static final Random RANDOM = new Random();

    private final AssetRepository assetRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public void run(String... args) {
        final List<Employee> employees = employeeRepository.findAll();

        for (int i = 0; i < 200; i++) {
            final AssetStatus status = AssetStatus.next();
            final String assigneeId =
                    (status == AssetStatus.ASSIGNED || status == AssetStatus.IN_SERVICE)
                            ? employees.get(RANDOM.nextInt(employees.size())).getEmployeeId()
                            : null;

            assetRepository.save(Asset.builder()
                    .serialNumber(AssetUtils.nextSerialNumber())
                    .name(FAKER.commerce().productName())
                    .type(AssetType.next())
                    .status(status)
                    .assignedEmployeeId(assigneeId)
                    .createdAt(OffsetDateTime.now())
                    .updatedAt(OffsetDateTime.now())
                    .build());
        }
        log.info("Assets inserted: {}", assetRepository.count());
    }
}