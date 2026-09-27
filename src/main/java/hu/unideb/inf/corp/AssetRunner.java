package hu.unideb.inf.corp;

import com.github.javafaker.Faker;
import hu.unideb.inf.corp.model.Asset;
import hu.unideb.inf.corp.model.AssetStatus;
import hu.unideb.inf.corp.model.AssetType;
import hu.unideb.inf.corp.model.Employee;
import hu.unideb.inf.corp.repository.AssetRepository;
import hu.unideb.inf.corp.repository.EmployeeRepository;
import hu.unideb.inf.corp.util.AssetUtils;
import hu.unideb.inf.corp.util.EmployeeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class AssetRunner implements CommandLineRunner {
    private static final Faker FAKER = new Faker();
    private static final Random RANDOM = new Random();

    private final AssetRepository assetRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Employee> savedEmployees = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            savedEmployees.add(employeeRepository.save(
                    Employee.builder()
                    .employeeId(EmployeeUtils.nextEmployeeId())
                    .name(FAKER.name().fullName())
                    .email(FAKER.internet().emailAddress())
                    .department(FAKER.commerce().department())
                    .createdAt(OffsetDateTime.now())
                    .updatedAt(OffsetDateTime.now())
                    .build()
            ));
        }

        AssetType[] types = AssetType.values();
        AssetStatus[] statuses = AssetStatus.values();

        for (int i = 0; i < 200; i++) {
            AssetStatus randomStatus = statuses[RANDOM.nextInt(statuses.length)];
            String assignedEmpId = null;

            if (randomStatus == AssetStatus.ASSIGNED || randomStatus == AssetStatus.IN_SERVICE) {
                assignedEmpId = savedEmployees.get(RANDOM.nextInt(savedEmployees.size())).getEmployeeId();
            }

            Asset asset = Asset.builder()
                    .serialNumber(AssetUtils.nextSerialNumber())
                    .name(FAKER.commerce().productName())
                    .type(types[RANDOM.nextInt(types.length)])
                    .status(randomStatus)
                    .assignedEmployeeId(assignedEmpId)
                    .createdAt(OffsetDateTime.now())
                    .updatedAt(OffsetDateTime.now())
                    .build();

            assetRepository.save(asset);
        }

        if(employeeRepository.count()==50 && assetRepository.count() == 200){
            System.out.println("Database initialized successfully!");
        }
        else{
            System.out.println("Database initialization failed!");
        }
    }
}