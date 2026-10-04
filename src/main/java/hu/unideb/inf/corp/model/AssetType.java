package hu.unideb.inf.corp.model;

import java.util.Random;

public enum AssetType {
    LAPTOP,
    PHONE,
    CAR,
    MONITOR;

    private static final Random RANDOM = new Random();

    public static AssetType next(){
        return values()[RANDOM.nextInt(values().length)];
    }
}
