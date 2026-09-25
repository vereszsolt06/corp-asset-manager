package hu.unideb.inf.corp.repository;

import hu.unideb.inf.corp.model.Asset;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.*;

@Repository
public class AssetRepositoryImpl implements AssetRepository{
    private final Map<String, Asset> assets;

    public AssetRepositoryImpl() {
        this.assets = new HashMap<>();
    }

    @Override
    public List<Asset> findAll() {
        return assets.values().stream()
                .sorted(Comparator.comparing(Asset::getSerialNumber))
                .toList();
    }

    @Override
    public Optional<Asset> findById(@NonNull String serialNumber) {
        return Optional.ofNullable(assets.get(serialNumber));
    }

    @Override
    public Asset create(@NonNull Asset asset) {
        final var updated = asset
                .withCreatedAt(OffsetDateTime.now())
                .withUpdatedAt(OffsetDateTime.now());
        assets.put(asset.getSerialNumber(),updated);
        return updated;
    }

    @Override
    public Asset update(@NonNull Asset asset) {
        if(!assets.containsKey(asset.getSerialNumber())){
            throw new NoSuchElementException("Asset not found: " + asset.getSerialNumber());
        }
        final var updated = asset.withUpdatedAt(OffsetDateTime.now());
        assets.put(asset.getSerialNumber(),updated);
        return updated;
    }

    @Override
    public void deleteById(@NonNull String serialNumber) {
        assets.remove(serialNumber);
    }
}
