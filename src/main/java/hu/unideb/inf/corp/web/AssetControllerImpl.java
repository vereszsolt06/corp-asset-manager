package hu.unideb.inf.corp.web;

import hu.unideb.inf.corp.model.Asset;
import hu.unideb.inf.corp.repository.AssetRepository;
import hu.unideb.inf.corp.repository.EmployeeRepository;
import hu.unideb.inf.corp.util.AssetUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AssetControllerImpl implements AssetController{
    private static final Logger LOGGER = LoggerFactory.getLogger(AssetControllerImpl.class);
    private final AssetRepository repository; // final, so @RequiredArgsConstructor could inject
    private final EmployeeRepository employeeRepository;

    @Override
    public Asset getOne(String serialNumber) {
        LOGGER.info("getOne({})",serialNumber);
        return repository.findById(serialNumber).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "No Asset found"));
    }

    @Override
    public List<Asset> getAll() {
        LOGGER.info("getAll()");
        return repository.findAll();
    }

    @Override
    public Asset createOne(Asset asset) {
        LOGGER.info("createdOne({})",asset);
        validateEmployeeId(asset.getAssignedEmployeeId());
        return repository.save(asset
                .withSerialNumber(AssetUtils.nextSerialNumber())
                .withCreatedAt(OffsetDateTime.now())
                .withUpdatedAt(OffsetDateTime.now()));
    }

    @Override
    public Asset updateOne(Asset asset) {
        LOGGER.info("updateOne({})",asset);
        validateEmployeeId(asset.getAssignedEmployeeId());
        return repository.findById(asset.getSerialNumber())
                .map(a->a.withName(asset.getName())
                                .withType(asset.getType())
                                .withStatus(asset.getStatus())
                                .withAssignedEmployeeId(asset.getAssignedEmployeeId())
                                .withUpdatedAt(OffsetDateTime.now()))
                .map(repository::save)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Asset found"));
    }

    @Override
    public void deleteOne(String serialNumber) {
        LOGGER.info("deleteOne({})",serialNumber);
        repository.deleteById(serialNumber);
    }

    private void validateEmployeeId(String employeeId) {
        if (employeeId != null && !employeeRepository.existsById(employeeId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Employee found: " + employeeId);
        }
    }
}

