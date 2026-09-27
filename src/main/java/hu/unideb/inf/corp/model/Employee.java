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
public class Employee {
    @Id
    String employeeId;

    String name;
    String email;
    String department;

    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}
