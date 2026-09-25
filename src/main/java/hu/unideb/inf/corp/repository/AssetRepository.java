package hu.unideb.inf.corp.repository;

import hu.unideb.inf.corp.model.Asset;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface AssetRepository {
    List<Asset> findAll();

    Optional<Asset> findById(@NonNull String serialNumber);

    Asset create(@NonNull Asset asset);

    Asset update(@NonNull Asset asset);

    void deleteById(@NonNull String serialNumber);
}
