package hu.unideb.inf.corp;

import hu.unideb.inf.corp.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class AssetRunner implements CommandLineRunner {
    private final AssetRepository repository;
    private final Random random = new Random();

    @Override
    public void run(String... args) throws Exception {

    }
}
