package hu.unideb.inf.corp.model;

import java.util.Random;

public enum AssetStatus {
    IN_STOCK,
    ASSIGNED,
    IN_SERVICE,
    SCRAPPED;

    private static final Random RANDOM = new Random();

    public static AssetStatus next() {
        return values()[RANDOM.nextInt(values().length)];
    }
}
