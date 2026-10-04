package hu.unideb.inf.corp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Employee {
    @Id
    @EqualsAndHashCode.Include
    private String employeeId;

    private String name;
    private String email;
    private String department;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
