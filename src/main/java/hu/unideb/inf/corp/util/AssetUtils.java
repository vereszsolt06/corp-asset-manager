package hu.unideb.inf.corp.util;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AssetUtils {
    private static final Random RANDOM = new Random();
    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String ALL = LETTERS + DIGITS;

    public static String nextSerialNumber() {
        String randomPart = IntStream.range(0, 6)
                .mapToObj(pos -> String.valueOf(ALL.charAt(RANDOM.nextInt(ALL.length()))))
                .collect(Collectors.joining());
        return "CA-" + randomPart;
    }
}
