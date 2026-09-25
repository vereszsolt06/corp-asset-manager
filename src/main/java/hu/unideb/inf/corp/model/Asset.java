package hu.unideb.inf.corp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.With;

import java.time.OffsetDateTime;

@AllArgsConstructor
@Builder
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Asset {
    @EqualsAndHashCode.Include
    String serialNumber;

    String name;
    AssetType type;
    AssetStatus status;

    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}
