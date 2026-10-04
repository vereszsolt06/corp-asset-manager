package hu.unideb.inf.corp.repository;

import hu.unideb.inf.corp.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
}