package hu.unideb.inf.corp.web;

import hu.unideb.inf.corp.model.AppUser;
import hu.unideb.inf.corp.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AppUserRepository repository;

    @GetMapping("/api/auth/me")
    public Map<String, String> me(Authentication authentication) {
        AppUser user = repository.findById(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        return Map.of(
                "username", user.getUsername(),
                "fullName", user.getFullName()
        );
    }
}