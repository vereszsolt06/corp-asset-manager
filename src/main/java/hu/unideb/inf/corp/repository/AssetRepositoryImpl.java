package hu.unideb.inf.corp.repository;

import hu.unideb.inf.corp.model.Asset;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class AssetRepositoryImpl implements AssetRepository{
    private final Map<String, Asset> assets;

    public AssetRepositoryImpl() {
        this.assets = new HashMap<>();
    }

    @Override
    public List<Asset> findAll() {
        return List.of();
    }

    @Override
    public Optional<Asset> findById(@NonNull String serialNumber) {
        return Optional.empty();
    }

    @Override
    public Asset create(@NonNull Asset asset) {
        return null;
    }

    @Override
    public Asset update(@NonNull Asset asset) {
        return null;
    }

    @Override
    public void deleteById(@NonNull String serialNumber) {

    }
}
