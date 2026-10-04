package hu.unideb.inf.corp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "app_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {
    @Id
    private String username;

    private String password; //BCrypt hash, nem az eredeti formában
    private String fullName;
}