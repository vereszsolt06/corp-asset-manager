package hu.unideb.inf.corp.repository;

import hu.unideb.inf.corp.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, String> {
    List<Asset> findByAssignedEmployeeId(String employeeId);
}
