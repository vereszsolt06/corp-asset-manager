package hu.unideb.inf.corp.model;

import lombok.*;

import java.time.OffsetDateTime;

@AllArgsConstructor
@Builder
@Value
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
