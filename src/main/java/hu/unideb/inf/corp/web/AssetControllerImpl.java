package hu.unideb.inf.corp.web;

import hu.unideb.inf.corp.model.Asset;
import hu.unideb.inf.corp.repository.AssetRepository;
import hu.unideb.inf.corp.util.AssetUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AssetControllerImpl implements AssetController{
    private static final Logger LOGGER = LoggerFactory.getLogger(AssetControllerImpl.class);

    private final AssetRepository repository; // final, so @RequiredArgsConstructor could inject

    @Override
    public Asset getOne(String serialNumber) {
        LOGGER.info("getOne({})",serialNumber);
        return repository.findById(serialNumber).orElseThrow();
    }

    @Override
    public List<Asset> getAll() {
        LOGGER.info("getAll()");
        return repository.findAll();
    }

    @Override
    public Asset createOne(Asset asset) {
        LOGGER.info("createdOne({})",asset);
        return repository.create(asset.withSerialNumber(AssetUtils.nextSerialNumber()));
    }

    @Override
    public Asset updateOne(Asset asset) {
        LOGGER.info("updateOne({})",asset);
        return Optional.of(asset)
                .map(Asset::getSerialNumber)
                .flatMap(repository::findById)
                .map(a -> a.withName(asset.getName()).withType(asset.getType()).withStatus(asset.getStatus()))
                .map(repository::update)
                .orElseThrow();
    }

    @Override
    public void deleteOne(String serialNumber) {
        LOGGER.info("deleteOne({})",serialNumber);
        repository.deleteById(serialNumber);
    }
}

