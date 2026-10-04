package hu.unideb.inf.corp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "assets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Asset {
    @Id
    @EqualsAndHashCode.Include
    private String serialNumber;

    private String name;
    @Enumerated(EnumType.STRING)
    private AssetType type;
    @Enumerated(EnumType.STRING)
    private AssetStatus status;

    @Column(name = "assigned_employee_id")
    private String assignedEmployeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_employee_id",insertable = false,updatable = false)
    @JsonIgnore
    @ToString.Exclude
    private Employee assignedEmployee;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
