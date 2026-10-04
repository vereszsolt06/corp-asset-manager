package hu.unideb.inf.corp.web;

import hu.unideb.inf.corp.model.AssetStatus;
import hu.unideb.inf.corp.model.Employee;
import hu.unideb.inf.corp.repository.AssetRepository;
import hu.unideb.inf.corp.repository.EmployeeRepository;
import hu.unideb.inf.corp.util.EmployeeUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import jakarta.transaction.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class EmployeeControllerImpl implements EmployeeController{
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeControllerImpl.class);
    private final EmployeeRepository repository;  // final, so @RequiredArgsConstructor could inject
    private final AssetRepository assetRepository;

    @Override
    public Employee getOne(String employeeId) {
        LOGGER.info("getOne({})",employeeId);
        return repository.findById(employeeId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "No Employee found"));
    }

    @Override
    public List<Employee> getAll() {
        LOGGER.info("getAll()");
        return repository.findAll();
    }

    @Override
    public Employee createOne(Employee employee) {
        LOGGER.info("createOne({})",employee);
        return repository.save(employee
                .withEmployeeId(EmployeeUtils.nextEmployeeId())
                .withCreatedAt(OffsetDateTime.now())
                .withUpdatedAt(OffsetDateTime.now()));
    }

    @Override
    public Employee updateOne(Employee employee) {
        LOGGER.info("updateOne({})",employee);
        return Optional.of(employee)
                .map(Employee::getEmployeeId)
                .flatMap(repository::findById)
                .map(e -> e.withName(employee.getName())
                        .withEmail(employee.getEmail())
                        .withDepartment(employee.getDepartment())
                        .withUpdatedAt(OffsetDateTime.now()))
                .map(repository::save)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Employee found"));
    }

    @Override
    @Transactional
    public void deleteOne(String employeeId) {
        LOGGER.info("deleteOne({})",employeeId);
        assetRepository.findByAssignedEmployeeId(employeeId).forEach(asset -> {
            asset.setAssignedEmployeeId(null);
            asset.setStatus(AssetStatus.IN_STOCK);
            asset.setUpdatedAt(OffsetDateTime.now());
        });
        repository.deleteById(employeeId);
    }
}
