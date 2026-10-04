package hu.unideb.inf.corp.runner;

import hu.unideb.inf.corp.model.AppUser;
import hu.unideb.inf.corp.repository.AppUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(0)
public class AppUserRunner implements CommandLineRunner {
    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        repository.save(AppUser.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .fullName("Admin")
                .build());
        repository.save(AppUser.builder()
                .username("user")
                .password(passwordEncoder.encode("user"))
                .fullName("TestUser")
                .build());
        log.info("Users inserted: {}", repository.count());
    }
}